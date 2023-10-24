package com.petra.lib.environment.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProcessValue {
    private final Long variableId;
    private final String name;
    private final boolean loaded;
    private final String jsonValue;
    private static final ObjectMapper oj = new ObjectMapper();

    public ProcessValue(Long variableId, String name, boolean loaded, String jsonValue) {
        this.variableId = variableId;
        this.name = name;
        this.loaded = loaded;
        this.jsonValue = jsonValue;
    }

    public <T> ParsedValue getParsedVariable(Class<T> clazz) throws JsonProcessingException {
        T parsed = oj.readValue(jsonValue, clazz);
        return new ParsedValue(variableId, name, loaded, jsonValue, parsed);
    }
}
