package com.petra.lib.signal.model;

import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SignalModel {

    Collection<ProcessVariable> signalVariables;
    String version;
    Long signalId;
    UUID scenarioId;

}
