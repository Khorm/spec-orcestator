package com.petra.lib.context.variables;

import com.petra.lib.state.variable.neww.ProcessValue;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

/**
 * „асть общего контекста, хран€щ€€ и синхронизирующ€€ изменени€ с базой
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariablesContext {
    //текущие заполненеые переменные
    final Map<Long, ProcessValue> processValueMap = new HashMap<>();

    final VariablesSynchRepo variablesSynchRepo;

    public VariablesContext(VariablesSynchRepo variablesSynchRepo) {
        this.variablesSynchRepo = variablesSynchRepo;
    }

    /**
     * ƒобавл€ет новое значение или замен€ет старое.
     * @param processValue
     */
    public synchronized void setVariable(ProcessValue processValue) {
        processValueMap.put(processValue.getVariableId(), processValue);
        variablesSynchRepo.commit(processValueMap);
    }


    /**
     * —инхронизирует контекст блока с вход€щим контектсом.
     * ћожет заполн€ть только отуствующие значени€.
     * IllegalArgumentException - выбрасываетс€ если такое значение уюе было найдено
     * @param inputContext
     */
    public synchronized void syncVariables(VariablesContext inputContext) {
        inputContext.processValueMap.forEach((key, value) -> {
            if (this.processValueMap.containsKey(key)) {
                throw new IllegalArgumentException("ќбнаружено пересенечение конектстов");
            }
            this.processValueMap.put(key, value);
        });
        variablesSynchRepo.commit(processValueMap);
    }

    public synchronized ProcessValue getProcessValueByName(String name) {
        for (Map.Entry<Long, ProcessValue> entry : processValueMap.entrySet()){
            if (entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        throw new NullPointerException("No value found for name " + name);
    }

    public synchronized ProcessValue getValueById(Long id){
        return processValueMap.get(id);
    }
}
