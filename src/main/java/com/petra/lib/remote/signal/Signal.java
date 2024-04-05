package com.petra.lib.remote.signal;

import com.petra.lib.block.BlockId;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * объект передающийся между блоками в качестве сигнала.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Deprecated
class Signal {

    /**
     * айди сценария
     */
    UUID scenarioId;

    /**
     * переменные JSON передаваемые в сигнале
     */
    VariablesContainer signalVariables;

    /**
     * айди сигнала
     */
    BlockId signalId;
    BlockId consumerBlockId;

    String producerService;
    BlockId producerBlockId;

    SignalType signalType;

    /**
     *
     * @param scenarioId - айди сценария
     * @param signalVariables - переменные сигнала
     * @param signalId - айди сигнала
     * @param consumerBlockId - имя блока ку отправлять
     * @param signalType - тип сигнала
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
