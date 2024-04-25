package com.petra.lib.workflow.signal;

import com.petra.lib.variable.pure.PureVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SignalBuildModel {
    Long signalId;
    /**
     * Переменные сигнала
     */
    Collection<PureVariable> signalVariables;
}
