package com.petra.lib.remote.response;

import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockResponseResult;
import org.springframework.web.client.RestTemplate;

class BlockResponseImpl implements BlockResponse{
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlockResponseResult response(BlockResponseDto blockResponseDto, String responseServiceName) {
        return restTemplate.postForObject("http://" + responseServiceName + "/approve",
                blockResponseDto, BlockResponseResult.class);
    }
}
