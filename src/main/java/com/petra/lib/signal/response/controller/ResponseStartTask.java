package com.petra.lib.signal.response.controller;

import com.petra.lib.manager.block.Block;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseStartTask implements Runnable{

    Block block;
    RequestDto requestDto;
    SignalId responseSignalId;

    @Override
    public void run() {
        block.execute(requestDto, responseSignalId);
    }
}
