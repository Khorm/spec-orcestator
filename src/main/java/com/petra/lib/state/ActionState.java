package com.petra.lib.state;

public enum ActionState {

    //////////////////////////request types
    /**
     * Initialize job
     */
    INITIALIZING(false),

    /**
     * Fill values of variable from sources, user handlers and mapping
     */
    FILL_CONTEXT_VARIABLES(true),

    /**
     * Execute block
     */
    EXECUTING(false),

    /**
     * Response after execution
     */
    COMPLETION(false),

    /**
     * Execution error
     */
    ERROR(false);

    private boolean canBeAnswered;
    ActionState(boolean canBeAnswered){
        this.canBeAnswered = canBeAnswered;
    }

    public boolean canBeAnswered(){
        return canBeAnswered;
    }



}
