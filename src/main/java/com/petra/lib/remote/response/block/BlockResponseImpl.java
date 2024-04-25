package com.petra.lib.remote.response.block;

import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockResponseResult;
import org.springframework.web.client.RestTemplate;

class BlockResponseImpl implements BlockResponse{
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlockResponseResult response(BlockResponseDto blockResponseDto, String responseServiceName) {
        return restTemplate.postForObject("http://" + responseServiceName + "/approve",
                blockResponseDto, BlockResponseResult.class);
    }
}
