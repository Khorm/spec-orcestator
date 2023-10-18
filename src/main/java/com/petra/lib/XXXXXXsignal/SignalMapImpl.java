package com.petra.lib.XXXXXXsignal;

import com.petra.lib.XXXXXXsignal.request.RequestSignal;
import com.petra.lib.XXXXXXsignal.response.ResponseSignal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class SignalMapImpl implements SignalMap{

    Map<SignalId, RequestSignal> requestSignalMap = new HashMap<>();
    Map<SignalId, ResponseSignal> responseSignalMap = new HashMap<>();


    @Override
    public RequestSignal getRequestSignalById(SignalId signalId) {
        return requestSignalMap.get(signalId);
    }

    @Override
    public ResponseSignal getResponseSignalById(SignalId signalId) {
        return responseSignalMap.get(signalId);
    }


    void setResponseSignal(ResponseSignal responseSignal){
        responseSignalMap.put(responseSignal.getId(), responseSignal);
    }

    void setRequestSignal(RequestSignal requestSignal){
        requestSignalMap.put(requestSignal.getId(), requestSignal);
    }
}
