package com.petra.lib.block;

import com.petra.lib.manager.ExecutionContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExecutingBlockBuilder {

    Consumer<ExecutionContext> afterExecutionConsumer;

    public void setAfterExecutionFunction(Consumer<ExecutionContext> afterExecutionConsumer){
        this.afterExecutionConsumer = afterExecutionConsumer;
    }

    public ExecutingBlock build(){

    }

}
