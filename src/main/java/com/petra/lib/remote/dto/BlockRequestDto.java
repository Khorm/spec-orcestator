package com.petra.lib.remote.dto;

import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class BlockRequestDto {
    /**
     * ���� ��������
     */
    UUID scenarioId;
    /**
     * ���� ����� ������� ���������� ������
     */
    Long producerId;
    /**
     * ���� ����� ������� �������� ������
     */
    Long consumerId;

    /**
     * ���� ������� ������� ����������
     */
    Long signalId;

    /**
     * �������������� ���������� ���������
     */
    VariablesContainer blockVariables;

    /**
     * ��� �������
     */
    String requestWorkflowServiceName;
}
