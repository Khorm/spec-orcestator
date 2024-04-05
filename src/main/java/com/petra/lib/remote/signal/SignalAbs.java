package com.petra.lib.remote.signal;

import com.petra.lib.block.BlockId;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class SignalAbs {

    /**
     * ID сценария
     */
    UUID scenarioId;

    /**
     * Переменные сигнала
     */
    VariablesContainer signalVariables;
    BlockId responseBlockId;
    BlockId requestBlockId;
}
