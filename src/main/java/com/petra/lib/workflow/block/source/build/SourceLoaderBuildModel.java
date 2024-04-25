package com.petra.lib.workflow.block.source.build;

import com.petra.lib.variable.pure.PureVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SourceLoaderBuildModel {
    Long requestingSourceId;
    /**
     * Все переменные соурса
     */
    Collection<PureVariable> sourceVariables;
    String requestingSourceServiceName;
}
