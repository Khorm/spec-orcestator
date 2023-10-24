package com.petra.lib.state;

public enum ActionState {
    /**
     * Initialize job
     */
    INITIALIZING,

    /**
     * Fill values of variable from sources, user handlers and mapping
     */
    FILL_CONTEXT_VARIABLES,

    /**
     * Execute block
     */
    EXECUTING,

    /**
     * Save execution result
     */
//    EXECUTION_REGISTRATION,

    /**
     * Response after execution
     */
    END,

    /**
     * Execution error
     */
    ERROR
}
