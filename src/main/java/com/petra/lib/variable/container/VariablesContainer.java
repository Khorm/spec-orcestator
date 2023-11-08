package com.petra.lib.variable.container;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VariablesContainer {
    /**
     * Добавляет новое значение или заменяет старое.
     * @param processValue
     */
    void addVariable(ProcessValue processValue, UUID actionId);

    /**
     * Синхронизирует контекст блока с входящим контектсом.
     * Заменяет встретившиеся значения
     * @param inputContext
     */
    void addVariables(VariablesContainer inputContext, UUID actionId);

    ProcessValue getValueByName(String name);

    Optional<ProcessValue> getValueById(Long id);

    Collection<ProcessValue> getValues();


}
