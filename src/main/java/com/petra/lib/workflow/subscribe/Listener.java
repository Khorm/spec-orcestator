package com.petra.lib.workflow.subscribe;

public interface Listener {
    void handle(WorkflowSignal workflowSignal, WorkflowSignalModel workflowSignalModel);
}
