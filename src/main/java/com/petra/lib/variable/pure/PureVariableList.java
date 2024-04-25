package com.petra.lib.variable.pure;

import com.petra.lib.variable.value.ProcessValue;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Список переменных, которые обрабатывает блок
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PureVariableList {
    Map<Long, PureVariable> idVariableMap;
    Map<String, PureVariable> nameVariableMap;

    Mapper mapper;

    public PureVariableList(Collection<PureVariable> pureVariables) {
        idVariableMap = pureVariables.stream()
                .collect(Collectors.toMap(PureVariable::getId, Function.identity()));
        nameVariableMap = pureVariables.stream()
                .collect(Collectors.toMap(PureVariable::getName, Function.identity()));
        mapper = new Mapper(pureVariables);
    }

    public PureVariable getVariableById(Long id) {
        return idVariableMap.get(id);
    }

    public PureVariable getVariableByName(String name) {
        return nameVariableMap.get(name);
    }

    public Collection<ProcessValue> parseVariables(Collection<ProcessValue> incomeValues){
        Collection<ProcessValue> processValueCollection = new ArrayList<>();
        for (ProcessValue incomeValue: incomeValues){
            PureVariable localVariable = mapper.parseSourceIdToLocalId(incomeValue.getVariableId());
            ProcessValue processValue = new ProcessValue(localVariable.getId(), localVariable.getName(),
                    incomeValue.getJsonValue());
            processValueCollection.add(processValue);
        }
        return processValueCollection;
    }
}
