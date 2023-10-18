package com.petra.lib.environment.output.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.PetraException;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;

/**
 * Энкодер исходящего сообщения в строку
 */
public class SignalEncoder implements Encoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        try {
            String body = objectMapper.writeValueAsString(object);
            template.body(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new PetraException("SignalEncoder error", e);
        }
    }
}
