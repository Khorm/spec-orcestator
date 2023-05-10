package com.petra.lib.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.block.Block;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalResponseListener;
import com.petra.lib.signal.model.RequestDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InputQueueImpl implements InputQueue, SignalResponseListener {

    BlockingQueue<RequestDto> inputQueue;
    Executor jobExecutor;
    Map<Long, Block> blocksBySignalIds;
    ObjectMapper objectMapper = new ObjectMapper();
    QueueRepository queueRepository;
    Collection<ResponseSignal> signals;

    InputQueueImpl(Collection<ResponseSignal> signals, int threadsSize, Map<Long, Block> blocksBySignalIds, QueueRepository queueRepository){
        inputQueue = new ArrayBlockingQueue<>(threadsSize);
        jobExecutor = Executors.newFixedThreadPool(threadsSize);
        this.signals = signals;
        this.blocksBySignalIds = blocksBySignalIds;
        this.queueRepository = queueRepository;
        this.signals.forEach(signal -> signal.setListener(this));

        for(int i = 0; i < threadsSize; i++){
            jobExecutor.execute(() -> {
                while (true){
                    try {
                        RequestDto requestDto = inputQueue.take();
                        Block block = InputQueueImpl.this.blocksBySignalIds.get(requestDto.getSignalId());
                        block.execute(requestDto);
                    } catch (InterruptedException e) {
                        log.error(e);
                    }
                }
            });
        }
    }

    @Override
    public void setAnswer(Collection<ProcessVariableDto> contextVariables, UUID scenarioId, Long signalId){
        getSignal(signalId).setAnswer(contextVariables, scenarioId);
    }

    @Override
    public void setError(UUID scenarioId, Long signalId){
        getSignal(signalId).setError(scenarioId);
    }

    @Override
    public void executeSignal(String message) throws JsonProcessingException, InterruptedException {
        try {
            RequestDto signalTransferModel = objectMapper.readValue(message, RequestDto.class);
            inputQueue.put(signalTransferModel);
            queueRepository.addQueueMessage(message, signalTransferModel.getScenarioId(),
                    blocksBySignalIds.get(signalTransferModel.getSignalId()).getId());

        } catch (JsonProcessingException e) {
            log.error(e);
            throw e;
        } catch (InterruptedException e) {
            log.error(e);
            throw e;
        }
    }

    private ResponseSignal getSignal(Long signalId){
        for (ResponseSignal responseSignal : signals){
            if (responseSignal.getId().equals(signalId)){
                return responseSignal;
            }
        }
        throw new NullPointerException("Signal not found");
    }
}
