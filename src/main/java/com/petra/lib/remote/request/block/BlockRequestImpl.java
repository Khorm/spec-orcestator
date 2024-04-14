package com.petra.lib.remote.request.block;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import org.springframework.web.client.RestTemplate;

class BlockRequestImpl implements BlockRequest {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public BlockRequestResult requestBlockExec(BlockRequestDto blockRequestDto, String consumerServiceName) {
        return restTemplate.postForObject("http://" + consumerServiceName + "/execute",
                blockRequestDto, BlockRequestResult.class);
    }
}
