package com.petra.lib.workflow;

import com.petra.lib.workflow.signal.SignalBuildModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class WorkflowBuildModel {
    Long workflowId;
    Long callingSignalId;
    Collection<Long> allContainerBlockIds;

    Collection<SignalBuildModel> signalBuildModels;
}
