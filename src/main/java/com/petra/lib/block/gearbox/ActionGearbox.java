package com.petra.lib.block.gearbox;

import com.petra.lib.state.ActionState;

import java.util.Arrays;

class ActionGearbox extends AbsGearbox {
    ActionGearbox() {
        super(Arrays.asList(ActionState.INITIALIZING, ActionState.FILL_CONTEXT_VARIABLES, ActionState.EXECUTING, ActionState.END));
    }
}
