package com.petra.lib.block;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Deprecated
public class ProcessValue {

    private final Long variableId;

    @Getter
    private final String value;

    public ProcessValue(Long variableId, String json) {
        this.variableId = variableId;
        this.value = json;
    }
}
