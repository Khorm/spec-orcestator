package com.petra.lib.remote.controller;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("${service-name}")
@RequiredArgsConstructor
public class PetraController {

    private final InputExecutor inputController;

    @PostMapping(path = "execute")
    public BlockRequestResult execute(BlockRequestDto blockRequestDto) {
        return inputController.execute(blockRequestDto);
    }

    @PostMapping(path = "approve")
    public BlockResponseResult approve(BlockResponseDto signal) {
        return inputController.handleAnswer(signal);
    }


    @PostMapping(path = "source")
    public SourceResponseDto getSource(SourceRequestDto sourceRequestDto) {
        return inputController.handleSource(sourceRequestDto);
    }


}
