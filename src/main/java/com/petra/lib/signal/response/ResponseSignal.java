package com.petra.lib.signal.response;

import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.Signal;

import java.util.Collection;
import java.util.UUID;

/**
 * Response signal interface
 */
public interface ResponseSignal extends Signal {

    /**
     * Send answer parameters
     *
     * Mapped signal variables
     * @param signalAnswerVariables
     *
     * Block id, who request this response
     * @param requestBlockId
     *
     * Current scenario id
     * @param scenarioId
     *
     * Block id, who handles request signal and answers on it
     * @param responseBlockId
     */
    void setAnswer(Collection<ProcessValue> signalAnswerVariables, BlockId requestBlockId, UUID scenarioId, BlockId responseBlockId);

    /**
     * Send error if exception appeared
     *
     *
     * Block id, who request this response
     * @param requestBlockId
     *
     * Current scenario id
     * @param scenarioId
     *
     * Block id, who handles request signal and answers on it
     * @param responseBlockId
     */
    void setError(BlockId requestBlockId, UUID scenarioId, BlockId responseBlockId);

}
