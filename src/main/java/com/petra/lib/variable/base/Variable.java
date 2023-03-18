package com.petra.lib.variable.base;

import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Variable {
    Long id;
    String name;
}
