package com.petra.lib.block;

import com.petra.lib.remote.request.RequestDto;
import com.petra.lib.remote.response.ResponseDto;


@Deprecated
public interface Block {
    BlockId getId();

    String getName();

    ResponseDto execute(RequestDto requestDto);

}
