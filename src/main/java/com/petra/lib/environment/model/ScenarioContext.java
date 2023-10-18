package com.petra.lib.environment.model;

import com.petra.lib.XXXXXcontext.DirtyVariablesList;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.state.State;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScenarioContext {
    UUID businessId;
    BlockId blockId;
    String serviceName;
    Long transactionId;
    Signal signal;
    DirtyVariablesList dirtyVariablesList;

    public void syncCurrentInputVariableList(DirtyVariablesList inputDirtyVariablesList){
        dirtyVariablesList.syncVariables(inputDirtyVariablesList);
    }

    public void setCurrentState(State state){
        dirtyVariablesList.setCurrentState(state);
    }

    public State getCurrentState(){
        return dirtyVariablesList.getCurrentState();
    }

}
