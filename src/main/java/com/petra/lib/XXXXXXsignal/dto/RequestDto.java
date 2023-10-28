package com.petra.lib.XXXXXXsignal.dto;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Deprecated
public class RequestDto {
    UUID scenarioId;
    VersionBlockId requestBlockId;
    SignalId signalId;
    Collection<ProcessValue> signalVariables;
}
