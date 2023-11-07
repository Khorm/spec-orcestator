package com.petra.lib.block.switcher;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.state.ActionState;

import java.util.Iterator;
import java.util.List;

abstract class AbsStateSwitchSequence implements StateSwitchSequence {
    final List<ActionState> usedActionStates;

    /**
     * @param usedActionStates - список используемых а активности обработчиков
     */
    AbsStateSwitchSequence(List<ActionState> usedActionStates) {
        this.usedActionStates = usedActionStates;
    }

    @Override
    public ActionState getNextHandler(ActivityContext activityContext) {
        if (activityContext.getState() == null){
            return ActionState.INITIALIZING;
        }

        Iterator<ActionState> iter = usedActionStates.listIterator();
        while (iter.hasNext()) {
            if (iter.next() == activityContext.getState()) {
                return iter.next();
            }
        }
        throw new IllegalArgumentException("Wrong state: " + activityContext.getState());
    }
}
