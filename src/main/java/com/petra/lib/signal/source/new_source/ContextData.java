package com.petra.lib.signal.source.new_source;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class ContextData {

    @Getter
    ExecutionContext context;

    @Getter
    ExecutionHandler executionHandler;
    Set<Long> executedSourceSignalIds = new HashSet<>();

    Set<Long> getExecutedSourceSignalIds(){
        return new HashSet<>(executedSourceSignalIds);
    }

    void setExecutedSourceSignal(Long id){
        executedSourceSignalIds.add(id);
    }

    int executedSourceSignalsSize(){
        return executedSourceSignalIds.size();
    }

}
