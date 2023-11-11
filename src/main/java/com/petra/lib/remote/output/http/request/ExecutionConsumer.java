package com.petra.lib.remote.output.http.request;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import org.springframework.web.bind.annotation.RequestBody;

interface ExecutionConsumer {
    AnswerDto execute(@RequestBody Signal signal);
}
