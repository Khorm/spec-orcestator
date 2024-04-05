package com.petra.lib.remote.model;

import com.petra.lib.block.BlockId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockServiceModel {
    BlockId requestBlockId;
    String requestServiceName;
    String responseServiceName;
}
