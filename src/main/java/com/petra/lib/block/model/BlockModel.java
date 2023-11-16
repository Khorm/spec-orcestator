package com.petra.lib.block.model;

import com.petra.lib.block.Version;
import com.petra.lib.block.model.source.BlockGroup;
import com.petra.lib.variable.mapper.MapperVariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockModel {
    Long id;
    String name;
    Version version;
    BlockServiceModel service;
    BlockType blockType;
    Collection<BlockSignalModel> signals;
    Collection<MapperVariableModel> variables;
    Collection<BlockGroup> blockGroups;
}
