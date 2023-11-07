package com.petra.lib.block.switcher;

public class StateSwitchSequencePureFactory {

    public static StateSwitchSequence getActionGearBox(){
        return new ActionStateSwitchSequence();
    }
}
