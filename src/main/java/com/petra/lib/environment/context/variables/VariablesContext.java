package com.petra.lib.environment.context.variables;

import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.state.ActionState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariablesContext {
    //������� ����������� ����������
    final Map<Long, ProcessValue> processValueMap = new HashMap<>();

    @Setter
    @Getter
    volatile ActionState currentActionState;

    final VariablesSynchRepo variablesSynchRepo;

    public VariablesContext(VariablesSynchRepo variablesSynchRepo) {
        this.variablesSynchRepo = variablesSynchRepo;
    }

//    public synchronized String getJSONVariablesList() {
//
//    }

    /**
     * ��������� ����� �������� ��� �������� ������.
     * @param processValue
     */
    public synchronized void setVariable(ProcessValue processValue) {
        processValueMap.put(processValue.getVariableId(), processValue);
//        processValueMapByName.put(processValue.getName(), processValue);
        variablesSynchRepo.commit(processValueMap);
    }


    /**
     * �������������� �������� ����� � �������� ����������.
     * ����� ��������� ������ ����������� ��������.
     * IllegalArgumentException - ������������� ���� ����� �������� ��� ���� �������
     * @param inputContext
     */
    public synchronized void syncVariables(VariablesContext inputContext) {
        inputContext.processValueMap.forEach((key, value) -> {
            if (this.processValueMap.containsKey(key)) {
                throw new IllegalArgumentException("���������� ������������� ����������");
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
