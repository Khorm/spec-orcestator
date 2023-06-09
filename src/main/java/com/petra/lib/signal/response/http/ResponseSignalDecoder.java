package com.petra.lib.signal.response.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.dto.ResponseDto;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class ResponseSignalDecoder implements Decoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object decode(Response response, Type type) throws FeignException {
        if (response.body() == null) {
            return null;
        }
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ResponseDto message = objectMapper.readValue(bodyIs, ResponseDto.class);
            return message;
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
    }
}
