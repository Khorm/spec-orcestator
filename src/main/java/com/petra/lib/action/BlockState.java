package com.petra.lib.action;

public enum BlockState {

    EXECUTING,

    /**
     * Response after execution
     */
    EXECUTED,

    COMPLETE,

    /**
     * Execution error
     */
    ERROR

}
