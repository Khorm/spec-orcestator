package com.petra.lib.state.response;

import com.petra.lib.state.StateHandler;

public class ResponseFactory {
    public static StateHandler createResponseState(){
        return new ResponseState()
    }
}
