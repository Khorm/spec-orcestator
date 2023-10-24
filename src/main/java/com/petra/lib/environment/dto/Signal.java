package com.petra.lib.environment.dto;

import com.petra.lib.XXXXXXsignal.response.ResponseType;
import com.petra.lib.environment.context.variables.VariablesContext;
import com.petra.lib.block.BlockId;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.environment.output.enums.AnswerType;
import com.petra.lib.environment.output.enums.RequestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Signal {

    /**
     * ���� ��������
     */
    UUID scenarioId;

    /**
     * ���������� JSON ������������ � �������
     */
    VariablesContext variablesContext;

    /**
     * ���� �������
     */
    SignalId signalId;

    /**
     * ��� �������
     */
    String signalName;

    /**
     * ��� ������� ��������� �������
     */
    String producerServiceName;

    /**
     * ���� ���������� ��� ���������� ������
     */
    BlockId producerActionId;

    /**
     * ���� ����������� ��� ��������� ������
     */
    BlockId consumerBlockId;

    /**
     * ��� ������� ������������ �������
     */
    String consumerServiceName;

    /**
     * ��� �������
     */
    RequestType requestType;
    AnswerType answerType;
}
