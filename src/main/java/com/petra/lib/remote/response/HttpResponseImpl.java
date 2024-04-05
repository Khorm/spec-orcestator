package com.petra.lib.remote.response;

import com.petra.lib.block.BlockId;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

class HttpResponseImpl implements Response {

    RestTemplate restTemplate = new RestTemplate();
    BlockId responseBlockId;


    @Override
    public void response(UUID scenarioId, BlockId currentActionId, List<ResponseSignal> responseSignals,
                         ResponseSignalType responseSignalType, BlockId requestBlock, String requestServiceName) {
        ResponseDto responseDto = new ResponseDto(
                scenarioId,
                currentActionId,
                responseSignals,
                responseBlockId,
                responseSignalType,
                requestBlock
        );
        restTemplate.postForObject("http://" + requestServiceName + "/answer",
                responseDto, ResponseDto.class);
    }
}
