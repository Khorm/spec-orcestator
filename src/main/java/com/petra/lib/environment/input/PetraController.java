package com.petra.lib.environment.input;

import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service-name}")
@RequiredArgsConstructor
public class PetraController {

    private final WorkEnvironment workEnvironment;

    @PostMapping
    public AnswerDto consume(Signal signal){
       return workEnvironment.consume(signal);
    }
}
