package com.petra.lib.remote.output.manager;

import com.petra.lib.block.BlockVersionId;

import java.util.Collection;

public class OutputFactory {

    public static SignalRequestStrategy getSourceStrategy(String sendServiceName, Collection<Long> loaderValIds,
                                                          BlockVersionId sourceId, String currentServiceName){
        return new RequestSourceStrategy(sendServiceName, loaderValIds, sourceId, currentServiceName);
    }
}
