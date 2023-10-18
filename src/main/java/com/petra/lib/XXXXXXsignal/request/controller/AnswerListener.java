package com.petra.lib.XXXXXXsignal.request.controller;

import com.petra.lib.XXXXXXsignal.dto.ResponseDto;

/**
 * This listener subscribes for its own request and waits for the answer.
 */
public interface AnswerListener {
    void answer(ResponseDto responseDto);

}
