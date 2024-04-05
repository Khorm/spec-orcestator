package com.petra.lib.remote.request;

import com.petra.lib.block.BlockId;
import com.petra.lib.remote.response.ResponseDto;
import com.petra.lib.variable.value.VariablesContainer;

import java.util.UUID;

public interface Request {
    ResponseDto send(UUID scenarioId, Long responseBlockId,
                     VariablesContainer signalContainer, Long currentActionId);
}
