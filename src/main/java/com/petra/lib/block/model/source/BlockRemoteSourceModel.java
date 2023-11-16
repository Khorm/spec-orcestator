package com.petra.lib.block.model.source;

import com.petra.lib.block.BlockVersionId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockRemoteSourceModel {

    List<Long> localVariableIds;
    String sourceServiceName;
    BlockVersionId sourceId;
    Integer group;
}
