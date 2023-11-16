package com.petra.lib.state.variable.loader.handler;

/**
 * Польозвательский хендлер в котором
 * пользователь определяет загрузку переменной
 */
public interface UserVariableHandler {
    Object map(VariableUserContext variableUserContext);
}
