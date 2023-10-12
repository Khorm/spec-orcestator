package com.petra.lib.state.variable.group.repo;

import com.petra.lib.context.ExecutionContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

/**
 * Хранящийся во время запросов контекст
 */

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class ContextModel {

    @Getter
    ExecutionContext context;
    Set<Long> filledVariables = new HashSet<>();

    Set<Long> getExecutedSourceSignalIds(){
        return new HashSet<>(filledVariables);
    }

    void setFilledGroupId(Long id){
        filledVariables.add(id);
    }

}
