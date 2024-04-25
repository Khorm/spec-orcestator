package com.petra.lib.workflow.signal;

import java.util.Collection;

public final class SignalManagerFactory {

    public static SignalManager createSignalManager(Collection<SignalBuildModel> signalBuildModels){
        return new SignalManager(signalBuildModels);
    }
}
