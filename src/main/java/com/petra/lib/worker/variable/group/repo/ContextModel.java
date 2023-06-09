package com.petra.lib.worker.variable.group.repo;

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
    Set<Long> filledGroupIds = new HashSet<>();

    Set<Long> getExecutedSourceSignalIds(){
        return new HashSet<>(filledGroupIds);
    }

    void setFilledGroupId(Long id){
        filledGroupIds.add(id);
    }

}
