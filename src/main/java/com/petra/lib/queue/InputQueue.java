package com.petra.lib.queue;

import com.petra.lib.manager.block.Block;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.ResponseSignal;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface InputQueue {
    void setAnswer(Collection<ProcessVariableDto> contextVariables, UUID scenarioId, Long signalId);
    void setError(UUID scenarioId, Long signalId);

    static InputQueue getQueue(Collection<ResponseSignal> signals, int threadsSize,
                               Map<Long, Block> blocksBySignalIds, QueueRepository queueRepository){
        return new InputQueueImpl(signals, threadsSize, blocksBySignalIds, queueRepository);
    }
}
