package com.petra.lib.worker.source;

import com.petra.lib.manager.block.SourceSignalModel;
import com.petra.lib.signal.RequestSignal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SourceSignalList {

    Map<Long, RequestSignal> sourceSignals;
    Map<Long, Set<Long>> childrenByParent;
    Map<Long, Set<Long>> parentsByChild;
    Set<RequestSignal> starterSignals;

    @Getter
    int sourceSignalsCount;

    SourceSignalList(Collection<SourceSignalModel> sourceSignalList, List<RequestSignal> signals) {
        sourceSignalsCount = signals.size();


        sourceSignals = signals.stream()
                .collect(Collectors.toMap(signal -> signal.getId(), Function.identity()));

        childrenByParent = new HashMap<>();
        parentsByChild = new HashMap<>();
        starterSignals = new HashSet<>();

        if (sourceSignalList == null) return;
        for (SourceSignalModel signalModel : sourceSignalList) {
            childrenByParent.put(signalModel.getId(), new HashSet<>(signalModel.getChildIds()));
            if (signalModel.getParentIds().isEmpty()) {
                starterSignals.add(signals.stream().filter(signal -> signal.getId().equals(signalModel.getId())).findFirst().get());
            } else {
                parentsByChild.put(signalModel.getId(), new HashSet<>(signalModel.getParentIds()));

            }
        }

    }

    void start() {
        sourceSignals.values().forEach(RequestSignal::startSignal);
    }

    Set<RequestSignal> getNextAvailableSignals(Set<Long> executedSignals) {
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
