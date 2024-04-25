package com.petra.lib.remote.request.source;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import org.springframework.web.client.RestTemplate;

class SourceRequestImpl implements SourceRequest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlockRequestResult send(SourceRequestDto sourceRequestDto, String sourceServiceName) {
        return restTemplate.postForObject("http://" + sourceServiceName + "/source_request",
                sourceRequestDto, BlockRequestResult.class);
    }
}
