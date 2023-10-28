package com.petra.lib.XXXXXXsignal.request.controller;

import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.XXXXXXsignal.queue.TaskQueueManager;
import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;
import com.petra.lib.XXXXXXsignal.model.SignalModel;
import com.petra.lib.XXXXXXsignal.model.Version;
import com.petra.lib.XXXXXXsignal.request.KafkaRequest;
import com.petra.lib.XXXXXXsignal.request.RequestSignal;
import com.petra.lib.XXXXXXsignal.request.SignalRequestListener;
import com.petra.lib.XXXXXXsignal.request.SyncRequest;
import com.petra.lib.XXXXXXsignal.response.ResponseType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manager handle all requests from blocks
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestSignalManager implements SignalRequestManager, SignalRequestListener {
    /**
     * Contains subscribed to signal listeners.
     * First key is signal id.
     * Second key is block id
     */
    Map<Long, RequestSignal> requestSignalMap = new HashMap<>();
    TaskQueueManager taskQueueManager;
    AnswerListenerMap answerListenerMap;

    public RequestSignalManager(Collection<SignalModel> outputSignals, String bootstrapServers,
                                Map<String, Map<String, Object>> consumerUserParams, Map<String, Map<String, Object>> producerUserParams,
                                String serviceName, TaskQueueManager taskQueueManager, AnswerListenerMap answerListenerMap) {
        this.taskQueueManager = taskQueueManager;
        this.answerListenerMap = answerListenerMap;

        outputSignals.forEach(signalModel -> {
            switch (signalModel.getSignalType()) {
                case KAFKA:
                    RequestSignal requestSignal = new KafkaRequest(
                            signalModel,
                            bootstrapServers,
                            consumerUserParams.get(signalModel.getSignalName()),
                            serviceName,
                            producerUserParams.get(signalModel.getSignalName()),
                            this
                    );
                    requestSignalMap.put(requestSignal.getId(), requestSignal);
                    break;
                case HTTP:
                    RequestSignal httpRequestSignal = new SyncRequest(
                            signalModel,
                            this
                    );
                    requestSignalMap.put(httpRequestSignal.getId(), httpRequestSignal);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        });
    }

    @Override
    public void request(Long signalId, Collection<ProcessValue> signalVariables, UUID scenarioId, Long blockId, Version blockVersion) {
        requestSignalMap.get(signalId).request(signalVariables, scenarioId, blockId, blockVersion);
    }

    @Override
    public void executed(ResponseDto responseDto, Long signalId) {
        AnswerListener answerListener = answerListenerMap.getAnswerListener(responseDto.getScenarioId(), signalId);
        RequestTaskQueue requestHandler = new RequestTaskQueue(responseDto, answerListener);

        taskQueueManager.executeTask(requestHandler);
    }

    @Override
    public void error(Exception e, RequestDto requestDto, Long signalId) {
        log.error(e);
        ResponseDto responseDto = new ResponseDto(requestDto.getScenarioId(), requestDto.getSignalVersion(), requestDto.getRequestBlockId(),
                null, ResponseType.ERROR, null);
        AnswerListener answerListener = answerListenerMap.getAnswerListener(responseDto.getScenarioId(), signalId);
        RequestTaskQueue requestHandler = new RequestTaskQueue(responseDto, answerListener);
        taskQueueManager.executeTask(requestHandler);
    }
}
