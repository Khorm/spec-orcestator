package com.petra.lib.remote.signal;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * ������ ������������ ����� ������� � �������� �������.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Signal {

    /**
     * ���� ��������
     */
    UUID scenarioId;

    /**
     * ���������� JSON ������������ � �������
     */
    VariablesContainer signalVariables;

    /**
     * ���� �������
     */
    VersionId signalId;

    /**
     * ��� �������
     */
    String signalName;

    String consumerService;
    VersionId consumerBlockId;

    String producerService;
    VersionId producerBlockId;

    SignalType signalType;

    /**
     *
     * @param scenarioId - ���� ��������
     * @param signalVariables - ���������� �������
     * @param signalId - ���� �������
     * @param signalName - ��� �������
     * @param consumerService - ��� ������� ���� ����������
     * @param consumerBlockId - ��� ����� �� ����������
     * @param signalType - ��� �������
     */
    public Signal(UUID scenarioId,
                  VariablesContainer signalVariables, String signalName, VersionId signalId,
                  String consumerService, VersionId consumerBlockId,
                  String producerService, VersionId producerBlockId,
                  SignalType signalType) {
        this.scenarioId = scenarioId;
        this.signalVariables = signalVariables;
        this.signalId = signalId;
        this.signalName = signalName;
        this.consumerService = consumerService;
        this.consumerBlockId = consumerBlockId;
        this.producerService = producerService;
        this.producerBlockId = producerBlockId;
        this.signalType = signalType;
    }
}
