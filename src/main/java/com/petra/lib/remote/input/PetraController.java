package com.petra.lib.remote.input;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.dto.SignalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service-name}")
@RequiredArgsConstructor
public class PetraController {

    private final InputExecuteController inputController;

    @PostMapping(path="execute")
    public AnswerDto execute(SignalDTO signal){
        try {
            return inputController.execute(signal);
        }catch (Exception e){
            return new AnswerDto(SignalResult.ERROR);
        }

    }

}
