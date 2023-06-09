package com.petra.lib.manager.block;

import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;

public interface Block {
    BlockId getId();
    void execute(RequestDto requestDto, SignalId signalId);
}
