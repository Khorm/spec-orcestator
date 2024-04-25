package com.petra.lib.remote.response.source;

public final class SourceResponseFactory {
    public static SourceResponse createSourceResponse(){
        return new SourceResponseImpl();
    }
}
