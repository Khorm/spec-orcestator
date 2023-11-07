package com.petra.lib.remote.signal;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.VariablesContainer;
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
public class Signal {

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
    VersionId signalId;

    /**
     * имя сигнала
     */
    String signalName;

    String consumerService;
    VersionId consumerBlockId;

    String producerService;
    VersionId producerBlockId;

    SignalType signalType;

    /**
     *
     * @param scenarioId - айди сценария
     * @param signalVariables - переменные сигнала
     * @param signalId - айди сигнала
     * @param signalName - имя сигнала
     * @param consumerService - имя сервиса куда отправлять
     * @param consumerBlockId - имя блока ку отправлять
     * @param signalType - тип сигнала
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
