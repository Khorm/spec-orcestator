package com.petra.lib.XXXXXXsignal.response;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.XXXXXXsignal.Signal;

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
    void setAnswer(Collection<ProcessValue> signalAnswerVariables, VersionId requestBlockId, UUID scenarioId, VersionId responseBlockId);

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
    void setError(VersionId requestBlockId, UUID scenarioId, VersionId responseBlockId);

}
