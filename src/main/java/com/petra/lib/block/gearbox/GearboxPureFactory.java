package com.petra.lib.block.gearbox;

public class GearboxPureFactory {

    public static Gearbox getActionGearBox(){
        return new ActionGearbox();
    }
}
