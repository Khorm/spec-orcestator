package com.petra.lib.remote.request.source;

public final class SourceRequestFactory {
    public static SourceRequest createSourceRequest(){
        return new SourceRequestImpl();
    }
}
