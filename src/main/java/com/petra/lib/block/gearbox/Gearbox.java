package com.petra.lib.block.gearbox;

import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.state.State;

public interface Gearbox {
    State getNextHandler(ScenarioContext scenarioContext);
}
