package com.petra.lib.state.variable.loaders.source;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.variables.VariablesContainer;
import com.petra.lib.remote.output.OutputConsumeSocket;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.state.variable.loaders.VariableLoader;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SourceHandler implements VariableLoader {
    VersionId signalId;
    VersionId sourceId;
    String sourceName;
    String signalName;

    VariableMapper fromContextToSignalMapper;
    VariableMapper fromSignalToContextMapper;
    OutputConsumeSocket outputSocket;

    /**
     * Переменные, значения которых загружает лоадер
     */
    Collection<Long> loaderValIds;

    /**
     * Обработчик выполняет либо функцию запроса либо ответа
     * @param activityContext
     */
    @Override
    public void handle(ActivityContext activityContext) {
        if (isReady(activityContext)) return;

        switch (activityContext.getSignalType()) {
            case REQUEST_ACTIVITY_EXECUTION:
                requestData(activityContext);
                break;
            case RESPONSE_SOURCE:
                setAnswer(activityContext);
                break;
        }
    }

    /**
     * Проверяет все ли переменные группы заполнены
     * @param activityContext
     * @return
     */
    @Override
    public boolean isReady(ActivityContext activityContext) {
        return loaderValIds.stream()
                .allMatch(variableId -> activityContext.getValueById(variableId).isPresent());

    }

    private void requestData(ActivityContext activityContext){
        VariablesContainer signalVariables = fromContextToSignalMapper.map(activityContext.getSignalVariables());
        outputSocket.consume(signalVariables, activityContext, SignalType.REQUEST_SOURCE);
    }

    private void setAnswer(ActivityContext activityContext){
        VariablesContainer dataFromSignal = fromSignalToContextMapper.map(activityContext.getSignalVariables());
        activityContext.addVariables(dataFromSignal);
    }


}
