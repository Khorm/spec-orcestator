package com.petra.lib.variable.pure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Переменная которую обрабатывает блок
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PureVariable {
    Long id;
    String name;
    Collection<Long> sourceVariableIds;
}
