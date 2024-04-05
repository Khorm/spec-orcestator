package com.petra.lib.remote.request;

import com.petra.lib.block.BlockId;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RequestDto {
    /**
     * ���� ������ ������
     */
    UUID scenarioId;

    /**
     * ���� �������
     */
    Long id;

    /**
     * ��������� �������
     */
    VariablesContainer signalVariables;

    /**
     * ���� �������������� �����
     */
    Long requestBlockId;
    /**
     * ��� ������� ������������� ��������
     */
    String requestWorkflowServiceName;

    /**
     * ���� � ����������
     */
    Long executingBlockId;
//    RequestSignalType requestSignalType;
//    Long responseBlockId;


}
