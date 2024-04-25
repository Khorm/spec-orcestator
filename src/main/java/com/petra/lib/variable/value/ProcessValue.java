package com.petra.lib.variable.value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
public class ProcessValue {
    private final Long variableId;
    private final String name;
    private final String jsonValue;
    private static final ObjectMapper oj = new ObjectMapper();

    public ProcessValue(Long variableId, String name, String jsonValue) {
        this.variableId = variableId;
        this.name = name;
        this.jsonValue = jsonValue;
    }

    public ProcessValue(Long variableId, String name, Object value) throws JsonProcessingException {
        this.variableId = variableId;
        this.name = name;
        this.jsonValue = oj.writeValueAsString(value);
    }

    public <T> T getParsedVariable(Class<T> clazz) throws JsonProcessingException {
        return oj.readValue(jsonValue, clazz);
    }

    public <T> List<T> getParsedList(Class<T> clazz) throws JsonProcessingException {
        return oj.readValue(jsonValue, oj.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
