package com.petra.lib.manager.block;

import com.petra.lib.signal.model.RequestDto;

public interface Block {
    Long getId();
    void execute(RequestDto requestDto);
}
