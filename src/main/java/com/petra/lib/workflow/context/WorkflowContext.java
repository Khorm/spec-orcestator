package com.petra.lib.workflow.context;

import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.enums.WorkflowState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
public class WorkflowContext {

    final UUID scenarioId;
    final Long workflowId;
    final Long clientId;
    final Long signalId;
    final VariablesContainer signalVariables;
    WorkflowState workflowState;
    final String requestServiceName;


    public synchronized void setWorkflowState(WorkflowState workflowState){
        this.workflowState = workflowState;
    }


}
