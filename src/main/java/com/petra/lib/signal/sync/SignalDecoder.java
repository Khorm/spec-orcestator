package com.petra.lib.signal.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.model.SignalTransferModel;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

class SignalDecoder implements Decoder {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Object decode(Response response, Type type) throws FeignException {
        if (response.body() == null) {
            return null;
        }
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            SignalTransferModel message = mapper.readValue(bodyIs, SignalTransferModel.class);
            return message;
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
    }
}
