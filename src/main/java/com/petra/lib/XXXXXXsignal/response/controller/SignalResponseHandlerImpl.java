package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.XXXXXmanager.BlockMap;
import com.petra.lib.block.VersionBlockId;
import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.XXXXXXsignal.queue.TaskQueueManager;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.SignalMap;
import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.response.ResponseSignal;
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
    public void answerToRequest(Collection<ProcessValue> signalAnswerVariables, SignalId signalId, VersionBlockId requestBlockId, UUID scenarioId, VersionBlockId responseId) {
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
    public void errorToRequest(SignalId signalId, VersionBlockId requestBlockId, UUID scenarioId, VersionBlockId responseId) {
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
