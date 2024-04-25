package com.petra.lib.remote.request.source;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;

public interface SourceRequest {
    BlockRequestResult send(SourceRequestDto sourceRequestDto, String sourceServiceName);
}
