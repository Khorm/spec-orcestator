package com.petra.lib.context.variables;

import java.util.Optional;

public interface VariablesContainer {
    /**
     * Добавляет новое значение или заменяет старое.
     * @param processValue
     */
    void addVariable(ProcessValue processValue);

    /**
     * Синхронизирует контекст блока с входящим контектсом.
     * Заменяет встретившиеся значения
     * @param inputContext
     */
    void addVariables(VariablesContainer inputContext);

    ProcessValue getValueByName(String name);

    Optional<ProcessValue> getValueById(Long id);
}
