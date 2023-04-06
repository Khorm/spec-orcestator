package com.petra.lib.variable.process;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class ProcessVariableList {
    private final Collection<ProcessVariable> processVariables = new ArrayList<>();
}
