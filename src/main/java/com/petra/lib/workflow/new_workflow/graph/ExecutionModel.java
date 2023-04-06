package com.petra.lib.workflow.new_workflow.graph;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ExecutionModel {
    Long id;
    Collection<Long> children;
    Boolean noParents;
}
