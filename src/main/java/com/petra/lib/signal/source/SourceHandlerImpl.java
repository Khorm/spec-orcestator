package com.petra.lib.signal.source;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.signal.model.ExecutionRequest;
import com.petra.lib.signal.model.ExecutionResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * ОБработчик сигналов к соурсам
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceHandlerImpl implements SourceHandler {
    SourceSignalController sourceSignalController;
    Map<UUID, ExecutionContext> sourceContextsMap = new ConcurrentHashMap<>();
    Consumer<ExecutionContext> loadDataCallback;
    Consumer<ExecutionContext> errorCallback;
    Map<Long, VariableMapper> mappersForSourcesSignals;

    public SourceHandlerImpl(SourceSignalController sourceSignalController,
                             Consumer<ExecutionContext> loadDataCallback, Consumer<ExecutionContext> errorCallback,
                             Map<Long, VariableMapper> mappersForSourcesSignals) {
        this.sourceSignalController = sourceSignalController;
        this.loadDataCallback = loadDataCallback;
        this.errorCallback = errorCallback;
        this.mappersForSourcesSignals = mappersForSourcesSignals;
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        sourceContextsMap.put(executionContext.getScenarioId(), executionContext);
        sourceSignalController.getSignalsWithoutParents().forEach(signal -> {
            signal.send(executionContext.getScenarioId(), executionContext.getVariablesList(), this::sendErrorHandler);
        });
    }

    @Override
    public void start() {
        sourceSignalController.start(this::afterDataReceive);
    }

    /**
     * Колбэк после получения ответа от сервиса
     * @param executionResponse
     */
    private void afterDataReceive(ExecutionResponse executionResponse) {
        ExecutionContext executionContext = sourceContextsMap.get(executionResponse.getScenarioId());
        if (executionContext == null) return;

        VariableMapper variableMapper = mappersForSourcesSignals.get(executionResponse.getSignalId());
        Collection<ProcessVariable> processVariables = variableMapper.map(executionResponse.getConsumerProcessVariables());
        executionContext.setVariables(processVariables);

        List<SourceSignal> nextSignals = sourceSignalController.getNextAvailableSignals(executionResponse.getScenarioId(),
                executionResponse.getSignalId());

        //если все сендеры выполнены
        if (sourceSignalController.checkSourcesReady(executionResponse.getScenarioId())) {
            sourceContextsMap.remove(executionResponse.getScenarioId());
            sourceSignalController.clear(executionResponse.getScenarioId());
            loadDataCallback.accept(executionContext);
        } else {

            //выполнить следующие сендеры
            if (!nextSignals.isEmpty()) {
                nextSignals.forEach(signal -> {
                    signal.send(executionContext.getScenarioId(), executionContext.getVariablesList(), this::sendErrorHandler);
                });
            }
        }
    }

    private void sendErrorHandler(Exception e, ExecutionRequest request) {
        ExecutionContext executionContext = sourceContextsMap.get(request.getScenarioId());
        errorCallback.accept(executionContext);
    }

}
