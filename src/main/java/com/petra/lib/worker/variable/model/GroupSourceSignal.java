package com.petra.lib.worker.variable.model;

import com.petra.lib.signal.SignalId;
import com.petra.lib.variable.mapper.MapperVariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class GroupSourceSignal {
    /**
     * Source signal which produce variables to this group
     */
    SignalId sourceSignalId;

    /**
     * Variables mapper to source signal from context
     */
    Collection<MapperVariableModel> toSourceSignalVariablesMap;

}
