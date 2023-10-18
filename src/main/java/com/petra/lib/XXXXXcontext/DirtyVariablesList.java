package com.petra.lib.XXXXXcontext;

import com.petra.lib.block.ProcessValue;
import com.petra.lib.state.State;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirtyVariablesList {
    //текущие заполненеые переменные
    Map<Long, ProcessValue> processValueMap = new HashMap<>();

    @Setter
    @Getter
    volatile State currentState;

    public synchronized String getJSONVariablesList(){

    }

    public synchronized void setVariableList(ProcessValue processValue){
        processValueMap.put(processValue.getVariableId(), processValue);
    }

    public synchronized void syncVariables(DirtyVariablesList dirtyVariablesList){
        dirtyVariablesList.processValueMap.forEach((key, value)->{
            if (this.processValueMap.containsKey(key)){
                throw new IllegalArgumentException("Обнаружено пересенечение конектстов");
            }
            this.processValueMap.put(key,value);
        });
    }


}
