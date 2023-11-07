package com.petra.lib.remote.output.http;

import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

interface AnswerConsumer {
    AnswerDto answer(URI baseUrl, @RequestBody Signal signal);
}
