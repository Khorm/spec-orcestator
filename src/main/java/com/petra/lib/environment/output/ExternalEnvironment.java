package com.petra.lib.environment.output;

import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.output.enums.RequestType;

public interface ExternalEnvironment {
    AnswerDto consume(DirtyContext dirtyContext, RequestType requestType);
}
