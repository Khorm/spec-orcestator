package com.petra.lib.XXXXXXsignal.dto;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.response.ResponseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDto {
    UUID scenarioId;
    SignalId signalId;
    VersionId requestBlockId;
    VersionId responseBlockId;
    ResponseType responseType;
    Collection<ProcessValue> signalVariables;
}
