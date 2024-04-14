package com.petra.lib.remote.request.source;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;

public interface SourceRequest {
    SourceResponseDto send(SourceRequestDto sourceRequestDto, String sourceServiceName);
}
