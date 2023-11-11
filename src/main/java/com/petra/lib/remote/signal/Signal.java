package com.petra.lib.remote.signal;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.value.VariablesContainer;
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
    BlockVersionId signalId;
    BlockVersionId consumerBlockId;

    String producerService;
    BlockVersionId producerBlockId;

    SignalType signalType;

    /**
     *
     * @param scenarioId - ���� ��������
     * @param signalVariables - ���������� �������
     * @param signalId - ���� �������
     * @param consumerBlockId - ��� ����� �� ����������
     * @param signalType - ��� �������
     */
    public Signal(UUID scenarioId,
                  VariablesContainer signalVariables, BlockVersionId signalId,
                  BlockVersionId consumerBlockId,
                  String producerService, BlockVersionId producerBlockId,
                  SignalType signalType) {
        this.scenarioId = scenarioId;
        this.signalVariables = signalVariables;
        this.signalId = signalId;
        this.consumerBlockId = consumerBlockId;
        this.producerService = producerService;
        this.producerBlockId = producerBlockId;
        this.signalType = signalType;
    }
}
