package com.petra.lib.variable.mapper;

import com.petra.lib.signal.SignalId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class MapperVariableModel {
    SignalId signalId;
    Long sourceVariableId;
    Long targetVariableId;
}
