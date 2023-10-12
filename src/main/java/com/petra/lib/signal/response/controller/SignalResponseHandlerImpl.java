package com.petra.lib.signal.response.controller;

import com.petra.lib.manager.BlockMap;
import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.queue.TaskQueueManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.SignalMap;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.response.ResponseSignal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SignalResponseHandlerImpl implements SignalResponseHandler, ResponseReadyListener {

    TaskQueueManager taskQueueManager;
    BlockMap blockMap;
    SignalMap signalMap;

    @Override
    public void handleSignal(RequestDto requestDto, ResponseSignal signal) {
        Collection<ResponseStartTask> blocks = blockMap.getBySignalId(signal.getId()).stream()
                .map(block -> new ResponseStartTask(block, requestDto, signal.getId()))
                .collect(Collectors.toList());
        blocks.forEach(taskQueueManager::executeTask);
    }


    @Override
    public void answerToRequest(Collection<ProcessValue> signalAnswerVariables, SignalId signalId, BlockId requestBlockId, UUID scenarioId, BlockId responseId) {
        ResponseEndTask responseEndTask = new ResponseEndTask(
                signalAnswerVariables,
                requestBlockId,
                scenarioId,
                responseId,
                signalMap.getResponseSignalById(signalId),
                true
        );
        taskQueueManager.executeTask(responseEndTask);
    }

    @Override
    public void errorToRequest(SignalId signalId, BlockId requestBlockId, UUID scenarioId, BlockId responseId) {
        ResponseEndTask responseEndTask = new ResponseEndTask(
                null,
                requestBlockId,
                scenarioId,
                responseId,
                signalMap.getResponseSignalById(signalId),
                false
        );
        taskQueueManager.executeTask(responseEndTask);
    }

}
