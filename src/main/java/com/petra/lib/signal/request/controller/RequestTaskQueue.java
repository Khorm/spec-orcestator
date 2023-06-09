package com.petra.lib.signal.request.controller;

import com.petra.lib.signal.dto.ResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RequestTaskQueue implements Runnable {

    ResponseDto responseDto;
    AnswerListener answerListener;
//    Long signalId;

    @Override
    public void run() {
        answerListener.answer(responseDto);
    }
}
