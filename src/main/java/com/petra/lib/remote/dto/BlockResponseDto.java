package com.petra.lib.remote.dto;


import com.petra.lib.remote.enums.BlockExecutionStatus;
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
    String blockVariables;
    BlockExecutionStatus blockExecutionStatus;
    Long nextSignalId;
    Long producerBlockId;
}
