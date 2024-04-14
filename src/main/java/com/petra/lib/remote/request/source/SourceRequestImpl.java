package com.petra.lib.remote.request.source;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import org.springframework.web.client.RestTemplate;

class SourceRequestImpl implements SourceRequest {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public SourceResponseDto send(SourceRequestDto sourceRequestDto, String sourceServiceName) {
        return restTemplate.postForObject("http://" + sourceServiceName + "/source",
                sourceRequestDto, SourceResponseDto.class);
    }
}
