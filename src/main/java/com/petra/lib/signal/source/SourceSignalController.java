package com.petra.lib.signal.source;

import com.petra.lib.signal.model.ExecutionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SourceSignalController {

    Set<SourceSignal> signals;
    Map<Long, SourceSignal> signalsByConsumerId;
    Map<UUID, Set<SourceSignal>> executedSignals;

    public Collection<SourceSignal> getSignalsWithoutParents() {
        Collection<SourceSignal> sendersWithoutParents = new ArrayList<>();
        signals.forEach((sender) -> {
            if (sender.getParentSignals().isEmpty()) {
                sendersWithoutParents.add(sender);
            }
        });
        return sendersWithoutParents;
    }

    public synchronized void start(Consumer<ExecutionResponse> messageHandler) {
        signals.forEach(sourceSignal -> sourceSignal.start(messageHandler));
    }

    public synchronized boolean checkSourcesReady(UUID scenarioId) {
        Set<SourceSignal> executedSignalsSet = executedSignals.get(scenarioId);
        if (executedSignalsSet.size() == signals.size()) {
            return true;
        }
        return false;

    }

    public synchronized List<SourceSignal> getNextAvailableSignals(UUID scenarioId, Long consumerId) {
        SourceSignal executedSignal = signalsByConsumerId.get(consumerId);
        Set<SourceSignal> executedSignalsSet = executedSignals.get(scenarioId);
        executedSignalsSet.add(executedSignal);

        Set<SourceSignal> nextChildSignals = executedSignal.getChildSignals();
        return nextChildSignals.stream()
                .filter(childSignal -> {
                    Set<SourceSignal> parentSignals = childSignal.getParentSignals();
                    if (executedSignalsSet.containsAll(parentSignals)) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
    }

    public synchronized void clear(UUID scenarioId) {
        executedSignals.remove(scenarioId);
    }

}
