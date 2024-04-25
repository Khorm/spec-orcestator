package com.petra.lib.variable.value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public class ValuesContainerFactory {

    public static ValuesContainer getSimpleVariableContainer(){
        return new ValuesContainerImpl();
    }

    private final static ObjectMapper oj = new ObjectMapper();

    public static ValuesContainer createValuesContainer(Collection<ProcessValue> processValueCollection){
        return new ValuesContainerImpl(processValueCollection);
    }

    public static String toJson(ValuesContainer valuesContainer) {
        try {
            return oj.writeValueAsString(valuesContainer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ValuesContainer fromJson(String json)  {
        try {
            return oj.readValue(json, ValuesContainerImpl.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
