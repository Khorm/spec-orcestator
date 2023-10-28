package com.petra.lib.environment.output;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.output.enums.AnswerType;
import com.petra.lib.environment.output.enums.RequestType;

public interface OutputSocket {
    AnswerDto consume(ActivityContext context, RequestType requestType);
    AnswerDto answer(ActivityContext context, AnswerType requestType);
}
