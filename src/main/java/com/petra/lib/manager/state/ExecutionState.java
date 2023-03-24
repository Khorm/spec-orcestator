package com.petra.lib.manager.state;

public enum ExecutionState {
    INITIALIZING(true),
    REQUEST_SOURCE_DATA(false),
    EXECUTING(false),
    EXECUTION_REGISTRATION(false),
    EXECUTION_RESPONSE(true),

    ERROR(false)

    ;

    private final boolean isExecuteInNewThread;

    ExecutionState(boolean isExecuteInNewThread){
        this.isExecuteInNewThread = isExecuteInNewThread;
    }



    public boolean isExecuteInNewThread(){
        return isExecuteInNewThread;
    }
}
