package com.petra.lib.block.switcher;

public class StateSwitchSequencePureFactory {

    public static StateSwitchStrategy getActionSwitcher(){
        return new ActionStateSwitchStrategy();
    }
}
