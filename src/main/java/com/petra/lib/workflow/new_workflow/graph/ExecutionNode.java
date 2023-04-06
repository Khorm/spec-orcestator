package com.petra.lib.workflow.new_workflow.graph;

import com.petra.lib.signal.SenderSignal;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExecutionNode {

    @Getter
    Long id;
    SenderSignal signal;

    public void execute(Collection<ProcessVariable> processVariables, UUID scenarioId){
        signal.send(processVariables, scenarioId);
    }
}
