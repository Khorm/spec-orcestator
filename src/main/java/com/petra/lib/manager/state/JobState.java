package com.petra.lib.manager.state;

public enum JobState {
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
     * Response
     */
    EXECUTION_RESPONSE,

    /**
     * Execution error
     */
    ERROR
}
