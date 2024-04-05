package com.petra.lib.action.model.source;

import com.petra.lib.block.BlockId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockRemoteSourceModel {

    List<Long> localVariableIds;
    String sourceServiceName;
    BlockId sourceId;
    Integer group;
}
