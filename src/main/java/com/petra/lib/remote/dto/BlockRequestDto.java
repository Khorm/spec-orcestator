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
     * Айди сценария
     */
    UUID scenarioId;
    /**
     * айди блока который отправляет сигнал
     */
    Long producerId;
    /**
     * айди блока который получает сигнал
     */
    Long consumerId;

    /**
     * Айди сигнала который вызывается
     */
    Long signalId;

    /**
     * Подготовленные переменные консумера
     */
    VariablesContainer blockVariables;

    /**
     * Имя сервиса
     */
    String requestWorkflowServiceName;
}
