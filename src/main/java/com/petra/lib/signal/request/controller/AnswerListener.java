package com.petra.lib.signal.request.controller;

import com.petra.lib.signal.dto.ResponseDto;

/**
 * This listener subscribes for its own request and waits for the answer.
 */
public interface AnswerListener {
    void answer(ResponseDto responseDto);
}
