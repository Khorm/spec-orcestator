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
 * �� ������������ ����� �������������� ������ ���� ���������� ������
 */
class HttpOutputSocket implements OutputSocket {

    /**
     * ������ �� �������� ��������� � ���������� �������
     */
    VariableMapper toSignalVariableMapper;

    /**
     * ���� ������������� �������
     */
    SignalId signalId;

    /**
     * ��� ������������� �������
     */
    String signalName;

    /**
     * ���� ����� ����������
     */
    BlockId consumerActionId;

    /**
     * ��� ������� ����������
     */
    String consumerServiceName;


    WorkEnvironment remoteEntryPoint = Feign.builder()
            .encoder(new SignalEncoder())
            .decoder(new AnswerDecoder())
            .target(WorkEnvironment.class, "http://" + consumerServiceName);


    @Override
    public AnswerDto consume(ActivityContext context, RequestType requestType) {
        //������� �������� �� ��������� ���������� � �������� �������
        VariablesContext signalVariablesList = toSignalVariableMapper.map(context.getVariablesContext());

        //������� ������ �������
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
        //������� �������� �� ��������� ���������� � �������� �������
        VariablesContext signalVariablesList = toSignalVariableMapper.map(context.getVariablesContext());
        //������� ������ �������
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
