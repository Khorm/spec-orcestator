package com.petra.lib.state;

import com.petra.lib.context.ExecutionContext;

/**
 * Standart interface for all states of block.
 */
@Deprecated
public interface StateHandler {

    /**
     * Require state execution
     *
     * @param context - current execution context
     * @throws Exception - any execution exception
     */
    void execute(ExecutionContext context) throws Exception;

    State getManagerState();

    void start();

}
