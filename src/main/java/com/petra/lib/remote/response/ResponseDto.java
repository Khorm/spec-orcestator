package com.petra.lib.remote.response;

import com.petra.lib.block.BlockId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    UUID scenarioId;
    BlockId currentActionId;
    Collection<ResponseSignal> responseSignals;
    BlockId responseBlockId;
    ResponseSignalType responseSignalType;
    BlockId requestBlockId;

}
