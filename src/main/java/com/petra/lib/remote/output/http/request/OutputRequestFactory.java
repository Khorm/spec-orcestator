package com.petra.lib.remote.output.http.request;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.remote.output.http.SignalManager;

import java.util.Collection;

public class OutputRequestFactory {

    public static SignalManager getSourceStrategy(String sendServiceName, Collection<Long> loaderValIds,
                                                  BlockVersionId sourceId, String currentServiceName){
        return new SourceManager(sendServiceName, loaderValIds, sourceId, currentServiceName);
    }
}
