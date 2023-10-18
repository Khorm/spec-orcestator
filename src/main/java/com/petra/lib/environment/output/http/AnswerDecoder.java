package com.petra.lib.environment.output.http;

import com.petra.lib.environment.output.enums.SignalResult;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Пределывает входящее сообщение в сигнал.
 */
public class AnswerDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws FeignException {
        if (response.body() == null) {
            return null;
        }
        try (InputStream body = response.body()
                .asInputStream()) {
            return SignalResult.valueOf(body.toString());
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
    }
}
