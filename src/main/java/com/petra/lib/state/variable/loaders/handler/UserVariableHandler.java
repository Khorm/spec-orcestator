package com.petra.lib.state.variable.loaders.handler;

/**
 * Польозвательский хендлер в котором
 * пользователь определяет загрузку переменной
 */
public interface UserVariableHandler {
    Object map(VariableUserContext variableUserContext);
}
