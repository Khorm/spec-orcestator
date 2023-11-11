package com.petra.lib.remote.input;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service-name}")
@RequiredArgsConstructor
public class PetraController {

    private final InputExecuteController inputController;
    private final InputAnswerController inputAnswerController;

    @PostMapping(path="execute")
    public AnswerDto execute(Signal signal){
        try {
            SignalResult result = inputController.execute(signal);
            return new AnswerDto(result);
        }catch (Exception e){
            return new AnswerDto(SignalResult.ERROR);
        }

    }

    @PostMapping(path="answer")
    public AnswerDto answer(Signal signal){
        try {
            SignalResult result = inputAnswerController.answer(signal);
            return new AnswerDto(result);
        }catch (Exception e){
            return new AnswerDto(SignalResult.ERROR);
        }
    }

}
