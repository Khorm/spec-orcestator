package com.petra.lib.source.local;

public interface SourceContext {

    <T> T getVariable(String variableName);
    void setVariable(String name, Object value);
}
