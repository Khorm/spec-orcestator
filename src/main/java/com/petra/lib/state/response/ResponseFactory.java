package com.petra.lib.state.response;

import com.petra.lib.remote.output.http.SignalManager;
import com.petra.lib.state.StateHandler;

public class ResponseFactory {
    public static StateHandler createResponseState(SignalManager signalManager){
        return new ResponseState(signalManager);
    }
}
