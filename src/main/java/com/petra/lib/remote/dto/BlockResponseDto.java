package com.petra.lib.remote.dto;


import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockRequestStatus;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class BlockResponseDto {
    UUID scenarioId;
    Long executedBlockId;
    VariablesContainer blockVariables;
    BlockRequestStatus blockRequestStatus;
    Long callingSignalId;
    Long producerBlockId;
}
