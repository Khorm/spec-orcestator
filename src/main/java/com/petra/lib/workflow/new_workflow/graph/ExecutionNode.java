package com.petra.lib.workflow.new_workflow.graph;

import com.petra.lib.signal.request.RequestSignal;
import com.petra.lib.block.ProcessValue;
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
    RequestSignal signal;

    public void execute(Collection<ProcessValue> processValues, UUID scenarioId){
        signal.send(processValues, scenarioId);
    }
}
