package com.petra.lib.remote.response;

import com.petra.lib.block.BlockId;

import java.util.List;
import java.util.UUID;

public interface Response {
    void response(UUID scenarioId, BlockId currentActionId, List<ResponseSignal> responseSignals,
                  ResponseSignalType responseSignalType, BlockId requestBlock, String requestServiceName);
}
