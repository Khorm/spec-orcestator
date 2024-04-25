package com.petra.lib.workflow.block.source.build;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class LoadGroupBuildModel {
    int groupNumber;
    Collection<SourceLoaderBuildModel> sourceLoaderBuildModelCollection;
}
