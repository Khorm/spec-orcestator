package com.petra.lib.remote.request.block;

public final class BlockRequestFactory {
    public static BlockRequest createBlockRequest(){
        return new BlockRequestImpl();
    }
}
