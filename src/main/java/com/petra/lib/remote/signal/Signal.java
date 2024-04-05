package com.petra.lib.remote.signal;

import com.petra.lib.block.BlockId;
import com.petra.lib.variable.value.VariablesContainer;
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
@Deprecated
class Signal {

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
    BlockId signalId;
    BlockId consumerBlockId;

    String producerService;
    BlockId producerBlockId;

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
                  VariablesContainer signalVariables, BlockId signalId,
                  BlockId consumerBlockId,
                  String producerService, BlockId producerBlockId,
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
