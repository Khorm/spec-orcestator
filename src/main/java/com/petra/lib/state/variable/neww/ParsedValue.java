package com.petra.lib.state.variable.neww;

public class ParsedValue extends ProcessValue {
    private final Object value;

    public ParsedValue(Long variableId, String name, boolean loaded, String jsonValue, Object value) {
        super(variableId, name, loaded, jsonValue);
        this.value = value;
    }
}
