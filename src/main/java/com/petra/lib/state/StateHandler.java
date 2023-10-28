package com.petra.lib.state;

import com.petra.lib.context.ActivityContext;

/**
 * Standart interface for all states of block.
 */
public interface StateHandler {

    /**
     * Require state execution
     *
     * @param context - current execution context
     * @throws Exception - any execution exception
     */
    void execute(ActivityContext context) throws Exception;
//    void answer(ScenarioContext scenarioContext, Signal signal);

    void start();

    ActionState getState();



}
