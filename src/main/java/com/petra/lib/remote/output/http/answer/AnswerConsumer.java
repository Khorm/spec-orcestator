package com.petra.lib.remote.output.http.answer;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.output.manager.SignalDTO;
import com.petra.lib.remote.signal.Signal;
import org.springframework.web.bind.annotation.RequestBody;

public interface AnswerConsumer {
    AnswerDto answer(@RequestBody SignalDTO signal);
}
