package com.petra.lib.environment.output;

import com.petra.lib.block.BlockId;
import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.XXXXXcontext.DirtyVariablesList;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.input.WorkEnvironment;
import com.petra.lib.environment.output.enums.RequestType;
import com.petra.lib.environment.output.http.AnswerDecoder;
import com.petra.lib.environment.output.http.SignalEncoder;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.variable.mapper.VariableMapper;
import feign.Feign;

/**
 * Отправищиr сообщения по определенному урлу
 */
class HttpOutputConnector implements ExternalEnvironment{
    BlockId actionId;
    VariableMapper toSignalVariableMapper;
    SignalId signalId;
    String signalName;
    String producerServiceName;
    BlockId consumerActionId;
    String consumerServiceName;


    WorkEnvironment consumerEntryPoint = Feign.builder()
            .encoder(new SignalEncoder())
            .decoder(new AnswerDecoder())
            .target(WorkEnvironment.class, "http://" + consumerServiceName);

    public AnswerDto consume(DirtyContext dirtyContext, RequestType requestType) {
        //маппинг сигналов из контекста исполнения в конектст сигнала
        DirtyVariablesList signalVariablesList = toSignalVariableMapper.map(dirtyContext.getDirtyVariablesList());

        //отправляемый сигнал
        Signal producerSignalDto = new Signal(
                dirtyContext.getScenarioId(),
                signalVariablesList.getJSONVariablesList(),
                signalId,
                signalName,
                producerServiceName,
                actionId,
                consumerActionId,
                consumerServiceName,
                requestType
        );

        //выгрузить текущий конектст в базу
//        actionRepo.flushBusinessActionContext(dirtyContext.getScenarioId(), dirtyContext.getBlockId(), dirtyContext);
        return consumerEntryPoint.consume(producerSignalDto);
    }
}
