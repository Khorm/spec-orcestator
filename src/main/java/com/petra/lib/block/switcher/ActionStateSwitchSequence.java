package com.petra.lib.block.switcher;

import com.petra.lib.context.state.ActionState;

import java.util.Arrays;

class ActionStateSwitchSequence extends AbsStateSwitchSequence {
    ActionStateSwitchSequence() {
        super(Arrays.asList(ActionState.INITIALIZING, ActionState.FILL_CONTEXT_VARIABLES, ActionState.EXECUTING, ActionState.COMPLETION));
    }
}
