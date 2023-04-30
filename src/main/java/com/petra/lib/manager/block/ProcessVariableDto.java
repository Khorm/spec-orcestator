package com.petra.lib.manager.block;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProcessVariableDto {

    private Long id;

    @Getter
    private String value;

    public ProcessVariableDto(Long id, String json) {
        this.id = id;
        this.value = json;
    }

//    public Object getValue(Class<?> clazz) throws JsonProcessingException {
//        if (value == null) return null;
//        ObjectMapper objectMapper = new ObjectMapper();
//        Object parserValue = objectMapper.readValue(value, clazz);
//        return parserValue;
//    }
}
