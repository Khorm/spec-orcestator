package com.petra.lib.XXXXXXsignal.request.controller;

import com.petra.lib.block.BlockId;
import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;

import java.util.Collection;
import java.util.UUID;

public interface SignalRequestManager {

    /**
     *
     * @param signalId - send signal id
     * @param signalVariables - filled signal variables
     * @param scenarioId - current scenario id
     * @param blockId - current block id
     * @param answerListener - callback
     */
    void request(SignalId signalId, Collection<ProcessValue> signalVariables,
                 UUID scenarioId, BlockId blockId, AnswerListener answerListener);
}
