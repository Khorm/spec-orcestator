package com.petra.lib.worker.variable.group.loaders;

import com.petra.lib.PetraException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class SourceVariableLoader implements VariableLoader {

    Long currentBlockVariableId;
    Long sourceVariableId;

    @Override
    public void load(Collection<ProcessVariableDto> sourceVariables, JobContext jobContext) {
        for (ProcessVariableDto sourceVariable : sourceVariables) {
            if (sourceVariable.getId().equals(sourceVariableId)) {
                jobContext.setVariable(new ProcessVariableDto(currentBlockVariableId, sourceVariable.getValue()));
                return;
            }
        }
        throw new PetraException("Variable not found in source", null);
    }
}
