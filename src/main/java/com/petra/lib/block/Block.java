package com.petra.lib.block;

import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.variable.base.PureVariableList;


public interface Block {
    BlockId getId();
    String getName();

    void execute(ScenarioContext scenarioContext);

    boolean isSequentially();
}
