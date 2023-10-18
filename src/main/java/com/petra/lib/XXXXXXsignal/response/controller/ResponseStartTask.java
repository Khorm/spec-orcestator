package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.block.Block;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.dto.RequestDto;
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
