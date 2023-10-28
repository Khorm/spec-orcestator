package com.petra.lib.environment.dto;

import com.petra.lib.context.variables.VariablesContext;
import com.petra.lib.block.VersionBlockId;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.environment.output.enums.AnswerType;
import com.petra.lib.environment.output.enums.RequestType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * объект передающийся между блоками в качестве сигнала.
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Signal {

    /**
     * айди сценария
     */
    UUID scenarioId;

    /**
     * переменные JSON передаваемые в сигнале
     */
    VariablesContext variablesContext;

    /**
     * айди сигнала
     */
    SignalId signalId;

    /**
     * имя сигнала
     */
    String signalName;

    /**
     * имя сервиса продюсера сигнала
     */
    String producerServiceName;

    /**
     * айди активности что отправляет сигнал
     */
    VersionBlockId producerActionId;

    /**
     * Айди активностей что принимает сигнал
     */
    VersionBlockId consumerBlockId;

    /**
     * имя сервиса принимающего сигнала
     */
    String consumerServiceName;

    /**
     * тип сигнала
     */
    RequestType requestType;
    AnswerType answerType;
}
