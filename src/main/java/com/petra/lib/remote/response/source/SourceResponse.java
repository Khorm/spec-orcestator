package com.petra.lib.remote.response.source;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockResponseResult;

public interface SourceResponse {
    BlockResponseResult response(SourceResponseDto blockResponseDto, String responseServiceName);
}
