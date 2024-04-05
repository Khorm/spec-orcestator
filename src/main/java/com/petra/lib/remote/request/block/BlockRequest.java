package com.petra.lib.remote.request.block;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;

public interface BlockRequest {
    BlockRequestResult requestBlockExec(BlockRequestDto blockRequestDto);
}
