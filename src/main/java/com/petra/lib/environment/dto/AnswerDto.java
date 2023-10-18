package com.petra.lib.environment.dto;

import com.petra.lib.environment.output.enums.SignalResult;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AnswerDto {
    private final SignalResult signalResult;
}
