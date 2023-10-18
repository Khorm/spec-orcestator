package com.petra.lib.block.gearbox;

import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;

import java.util.Arrays;
import java.util.List;

class ActionGearbox extends AbsGearbox {
    ActionGearbox() {
        super(Arrays.asList(State.INITIALIZING, State.FILL_CONTEXT_VARIABLES, State.EXECUTING, State.END));
    }
}
