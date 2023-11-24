package com.petra.lib.variable.mapper;

import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class MapperVariableModel {

  /**
   * Переменная в которую записываем значение
   */
  Long variableId;

  /**
   * Имя переменной в которую записываем значение
   */
  String name;

  /**
   * Список переменных из которой берём значения
   */
  Set<Long> sourceVariables;
}
