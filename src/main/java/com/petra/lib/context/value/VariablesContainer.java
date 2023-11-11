package com.petra.lib.context.value;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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

    Collection<ProcessValue> getValues();


}
