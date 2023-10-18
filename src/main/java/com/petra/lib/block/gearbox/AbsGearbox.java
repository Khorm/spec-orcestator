package com.petra.lib.block.gearbox;

import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.state.State;

import java.util.Iterator;
import java.util.List;

abstract class AbsGearbox implements Gearbox {
    final List<State> usedStates;

    /**
     * @param usedStates - список используемых а активности обработчиков
     */
    AbsGearbox(List<State> usedStates) {
        this.usedStates = usedStates;
    }

    @Override
    public State getNextHandler(ScenarioContext scenarioContext) {
        Iterator<State> iter = usedStates.listIterator();
        while (iter.hasNext()) {
            if (iter.next() == scenarioContext.getCurrentState()) {
                return iter.next();
            }
        }
        throw new NullPointerException("State not found");
    }
}
