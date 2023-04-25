package com.petra.lib.signal.source;

import com.petra.lib.manager.block.JobContext;
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
    JobContext context;
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
