package com.petra.lib.signal.request.controller;

import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.model.Version;

import java.util.Collection;
import java.util.UUID;

public interface SignalRequestManager {
    void request(SignalId signalId, Collection<ProcessVariableDto> signalVariables, UUID scenarioId, BlockId blockId);
}
