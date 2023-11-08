package com.petra.lib.variable.mapper;

import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.block.BlockVersionId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class MapperVariableModel {
    BlockVersionId blockVersionId;
    Long sourceVariableId;
    Long targetVariableId;
}
