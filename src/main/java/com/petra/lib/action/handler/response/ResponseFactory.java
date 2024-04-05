package com.petra.lib.action.handler.response;

import com.petra.lib.remote.request.Request;
import com.petra.lib.state.StateHandler;

public class ResponseFactory {
    public static StateHandler createResponseState(Request request){
        return new ResponseState(request);
    }
}
