package com.petra.lib.state.variable.loaders.handler;

/**
 * внутри может заполнятся ннесколько переменных, хоть все сразу.
 */
public interface UserVariableHandler {
    Object map(VariableUserContext variableUserContext);
}
