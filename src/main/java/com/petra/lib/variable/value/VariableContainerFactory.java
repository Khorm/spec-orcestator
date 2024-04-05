package com.petra.lib.variable.value;

public class VariableContainerFactory {

    public static VariablesContainer getSimpleVariableContainer(){
        return new VariablesContainerImpl();
    }
}
