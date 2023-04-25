package com.petra.lib.manager.state;

public enum JobState {
    /**
     * Initialize job
     */
    INITIALIZING(true),
    REQUEST_SOURCE_DATA(false),
    EXECUTING(false),
    EXECUTION_REGISTRATION(false),
    EXECUTION_RESPONSE(true),


    ERROR(false)

    ;

    private final boolean isExecuteInNewThread;

    JobState(boolean isExecuteInNewThread){
        this.isExecuteInNewThread = isExecuteInNewThread;

    }

    public boolean isExecuteInNewThread(){
        return isExecuteInNewThread;
    }
}
