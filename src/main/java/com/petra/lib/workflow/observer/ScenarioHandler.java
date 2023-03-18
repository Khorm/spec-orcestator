package com.petra.lib.workflow.observer;

import java.util.Optional;

/**
 *
 * @param <T>
 * @param <E>
 */
public interface ScenarioHandler<T,E> {

    void handled(T executedObject, E executedResult, Optional<T> nextObject);
    void iterationError(T errorObject, String message);
    void finish(T lastObject, E lastResult);
}
