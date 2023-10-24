package com.petra.lib.XXXXXXsignal.dto;

import com.petra.lib.block.BlockId;
import com.petra.lib.environment.context.ProcessValue;
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
    BlockId requestBlockId;
    BlockId responseBlockId;
    ResponseType responseType;
    Collection<ProcessValue> signalVariables;
}
