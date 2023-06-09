package com.petra.lib.worker.variable.group.loaders;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.worker.variable.group.handler.VariableHandler;

import java.util.Collection;

public interface VariableLoader {
    void load(Collection<ProcessVariableDto> sourceVariables, JobContext jobContext);

    static VariableLoader getHandlerVariable(Long currentBlockVariableId, VariableHandler variableHandler){
        return new HandlerVariableLoader(currentBlockVariableId, variableHandler);
    }

    static VariableLoader getInitVariableLoader(Long currentBlockVariableId, Long iniSignalVariableId){
        return new InitVariableLoader(currentBlockVariableId, iniSignalVariableId);
    }

    static VariableLoader getSourceVariableLoader(Long currentBlockVariableId, Long sourceVariableId){
        return new SourceVariableLoader(currentBlockVariableId, sourceVariableId);
    }
}
