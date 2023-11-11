package com.petra.lib.remote.dto;

import com.petra.lib.remote.input.SignalResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AnswerDto {
    private final SignalResult signalResult;
}
