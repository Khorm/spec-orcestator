package com.petra.lib.manager.state;

public enum JobState {
    /**
     * Initialize job
     */
    INITIALIZING,

    /**
     * Request source data
     */
    REQUEST_SOURCE_DATA,

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
