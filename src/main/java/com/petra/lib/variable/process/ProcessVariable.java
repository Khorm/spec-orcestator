package com.petra.lib.variable.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
public class ProcessVariable {

    private Long id;
    private Object value;
    private String valueJson;

    public ProcessVariable(Long id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Object getValue(){
        return value;
    }

    public Object getValue(Class<?> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        value = objectMapper.readValue(valueJson, clazz);
        return value;
    }
}
