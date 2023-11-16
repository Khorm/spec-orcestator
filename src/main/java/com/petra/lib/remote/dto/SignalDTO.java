package com.petra.lib.remote.dto;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.remote.signal.SignalType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignalDTO {
    UUID scenarioId;
    BlockVersionId consumerBlockId;
    VariablesContainer signalVariablesContainer;
    String producerServiceName;
    BlockVersionId producerBlockId;
    SignalType signalType;
}
