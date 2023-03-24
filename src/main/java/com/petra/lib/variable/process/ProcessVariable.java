package com.petra.lib.variable.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
public class ProcessVariable {

    private Long id;
    private transient Object value;
    private transient boolean wasParsed;
    private String valueJson;

    public ProcessVariable(Long id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Object getValue(){
        if (wasParsed) {
            return value;
        }
        throw new IllegalStateException();
    }

    public Object getValue(Class<?> clazz) throws JsonProcessingException {
        if (wasParsed){
            return value;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        value = objectMapper.readValue(valueJson, clazz);
        wasParsed = true;
        return value;
    }
}
