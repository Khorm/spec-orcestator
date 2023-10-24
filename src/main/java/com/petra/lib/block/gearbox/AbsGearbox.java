package com.petra.lib.block.gearbox;

import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.state.ActionState;

import java.util.Iterator;
import java.util.List;

abstract class AbsGearbox implements Gearbox {
    final List<ActionState> usedActionStates;

    /**
     * @param usedActionStates - список используемых а активности обработчиков
     */
    AbsGearbox(List<ActionState> usedActionStates) {
        this.usedActionStates = usedActionStates;
    }

    @Override
    public ActionState getNextHandler(ActivityContext activityContext) {
        Iterator<ActionState> iter = usedActionStates.listIterator();
        while (iter.hasNext()) {
            if (iter.next() == activityContext.getCurrentState()) {
                return iter.next();
            }
        }
        throw new NullPointerException("State not found");
    }
}
