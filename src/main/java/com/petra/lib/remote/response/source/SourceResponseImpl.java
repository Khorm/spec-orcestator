package com.petra.lib.remote.response.source;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockResponseResult;
import org.springframework.web.client.RestTemplate;

class SourceResponseImpl implements SourceResponse{

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlockResponseResult response(SourceResponseDto blockResponseDto, String responseServiceName) {
        return restTemplate.postForObject("http://" + responseServiceName + "/service_response",
                blockResponseDto, BlockResponseResult.class);
    }
}
