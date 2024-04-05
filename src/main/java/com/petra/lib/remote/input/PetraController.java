package com.petra.lib.remote.input;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${service-name}")
@RequiredArgsConstructor
public class PetraController {

    private final InputExecute inputController;

//    @PostMapping(path = "execute")
//    public ResponseDto execute(RequestDto signal) {
//        try {
//            return inputController.execute(signal);
//        } catch (Exception e) {
//            return RequestResult.ERROR;
//        }
//    }
//
//    @PostMapping(path = "answer")
//    public ResponseDto answer(ResponseDto signal) {
////        try {
////            return inputController.execute(signal);
////        }catch (Exception e){
////            return new AnswerDto(SignalResult.ERROR);
////        }
//    }


    @PostMapping(path = "source")
    public SourceResponseDto getSource(SourceRequestDto sourceRequestDto){
//        inputController.

    }


}
