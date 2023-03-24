package com.petra.lib.signal.source;

import com.petra.lib.signal.Signal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SourceSignalList {

    Map<Long, Signal> sourceSignals;
    Map<Long, Set<Long>> childrenByParent;
    Map<Long, Set<Long>> parentsByChild;
    Set<Signal> starterSignals;

    @Getter
    int sourceSignalsCount;

    Set<Signal> getNextAvailableSignals(Set<Long> executedSignals) {
        if (executedSignals == null || executedSignals.isEmpty()) {
            return starterSignals;
        }

        Set<Long> allNewChildren = executedSignals.stream()
                .filter(child -> !executedSignals.contains(child))
                .flatMap(executedSignal -> {
                    Set<Long> children = childrenByParent.get(executedSignal);
                    if (children != null && !children.isEmpty()) {
                        return children.stream();
                    }
                    return Stream.empty();
                })
                .collect(Collectors.toSet());

        Set<Long> childrenWithExecutedParents = allNewChildren.stream()
                .filter(child -> {
                    Set<Long> childParents = parentsByChild.get(child);
                    if (executedSignals.containsAll(childParents)) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toSet());

        return childrenWithExecutedParents.stream()
                .map(sourceSignals::get)
                .collect(Collectors.toSet());
    }
}
