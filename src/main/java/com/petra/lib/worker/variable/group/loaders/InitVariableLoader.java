package com.petra.lib.worker.variable.group.loaders;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class InitVariableLoader implements VariableLoader {

    Long currentBlockVariableId;
    Long initSignalVariableId;

    @Override
    public void load(Collection<ProcessVariableDto> sourceVariables, JobContext jobContext) {
        ProcessVariableDto variable = jobContext.getInitSignalVariable(initSignalVariableId);
        jobContext.setVariable(new ProcessVariableDto(currentBlockVariableId, variable.getValue()));
    }
}
