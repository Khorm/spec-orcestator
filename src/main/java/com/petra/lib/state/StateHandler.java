package com.petra.lib.state;

import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.model.ScenarioContext;

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
    void execute(ScenarioContext context) throws Exception;
//    void answer(ScenarioContext scenarioContext, Signal signal);

    void start();

    State getState();



}
