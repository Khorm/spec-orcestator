package com.petra.lib.remote.request;

import com.petra.lib.block.BlockId;

import java.util.Collection;

public class OutputRequestFactory {

    public static Request getSourceStrategy(String sendServiceName, Collection<Long> loaderValIds,
                                            BlockId sourceId, String currentServiceName){
        return new HttpRequestImpl(sendServiceName, loaderValIds, sourceId, currentServiceName);
    }
}
