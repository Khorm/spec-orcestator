package com.petra.lib.manager.factory;

import com.petra.lib.signal.SignalModel;
import com.petra.lib.variable.factory.VariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ActionModel {
    Long id;
    String name;
    Collection<VariableModel> variables;
    Collection<SourceSignalModel> sources;
    SignalModel request;
}
