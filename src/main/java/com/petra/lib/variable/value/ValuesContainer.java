package com.petra.lib.variable.value;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Контейнер для заполненных переменных
 */
public interface ValuesContainer {
    /**
     * Добавляет новое значение или заменяет старое.
     * @param processValue
     */
    void addValue(ProcessValue processValue);

    /**
     * Синхронизирует контекст блока с входящим контектсом.
     * Заменяет встретившиеся значения
     * @param inputContext
     */
    void addValues(ValuesContainer inputContext);
    void addValues(Collection<ProcessValue> inputContext);

    ProcessValue getValueByName(String name);

    ProcessValue getValueById(Long id);

    Collection<ProcessValue> getValues();


}
