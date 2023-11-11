package com.petra.lib.block.constructor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockServiceModel {
    Long id;
    String name;
}
