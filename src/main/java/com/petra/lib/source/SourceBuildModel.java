package com.petra.lib.source;

import com.petra.lib.variable.pure.PureVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SourceBuildModel {
    Long SourceId;
    String name;
    Collection<PureVariable> sourceVariables;
}
