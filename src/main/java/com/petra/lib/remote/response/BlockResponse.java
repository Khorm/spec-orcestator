package com.petra.lib.remote.response;

import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockResponseResult;

public interface BlockResponse {

    BlockResponseResult response(BlockResponseDto blockResponseDto, String responseServiceName);
}
