package com.petra.lib.remote.output.manager;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.remote.signal.SignalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class SignalDTO {
    BlockVersionId consumerId;
    VariablesContainer signalVariablesContainer;
    String producerServiceName;
    BlockVersionId producerId;
    SignalType signalType;
}
