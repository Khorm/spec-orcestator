package com.petra.lib.environment.output;

import com.petra.lib.block.BlockId;
import com.petra.lib.environment.context.variables.VariablesContext;
import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.input.WorkEnvironment;
import com.petra.lib.environment.output.enums.AnswerType;
import com.petra.lib.environment.output.enums.RequestType;
import com.petra.lib.environment.output.http.AnswerDecoder;
import com.petra.lib.environment.output.http.SignalEncoder;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.variable.mapper.VariableMapper;
import feign.Feign;

/**
 * не синглтоновый класс обрабатывающий каждый свой конкретный запрос
 */
class HttpOutputSocket implements OutputSocket {

    /**
     * маппер из текущего конектста в переменные сигнала
     */
    VariableMapper toSignalVariableMapper;

    /**
     * айди отправляемого сигнала
     */
    SignalId signalId;

    /**
     * Имя отправляемого сигнала
     */
    String signalName;

    /**
     * Айди блока получателя
     */
    BlockId consumerActionId;

    /**
     * имя сервиса получателя
     */
    String consumerServiceName;


    WorkEnvironment remoteEntryPoint = Feign.builder()
            .encoder(new SignalEncoder())
            .decoder(new AnswerDecoder())
            .target(WorkEnvironment.class, "http://" + consumerServiceName);


    @Override
    public AnswerDto consume(ActivityContext context, RequestType requestType) {
        //маппинг сигналов из контекста исполнения в конектст сигнала
        VariablesContext signalVariablesList = toSignalVariableMapper.map(context.getVariablesContext());

        //создать модель запроса
        Signal producerSignalDto = new Signal(
                context.getBusinessId(),
                signalVariablesList,
                signalId,
                signalName,
                context.getCurrentServiceName(),
                context.getCurrentBlockId(),
                consumerActionId,
                consumerServiceName,
                requestType,
                null
        );
        return remoteEntryPoint.consume(producerSignalDto);
    }

    @Override
    public AnswerDto answer(ActivityContext context, AnswerType answerType) {
        //маппинг сигналов из контекста исполнения в конектст сигнала
        VariablesContext signalVariablesList = toSignalVariableMapper.map(context.getVariablesContext());
        //создать модель запроса
        Signal producerSignalDto = new Signal(
                context.getBusinessId(),
                signalVariablesList,
                signalId,
                signalName,
                context.getCurrentServiceName(),
                context.getCurrentBlockId(),
                consumerActionId,
                consumerServiceName,
                null,
                answerType
        );
        return remoteEntryPoint.consume(producerSignalDto);
    }
}
