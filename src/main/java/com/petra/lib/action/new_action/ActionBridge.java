package com.petra.lib.action.new_action;

import com.petra.lib.block.ExecutingBlock;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.signal.source.SourceHandler;
import com.petra.lib.signal.source.SourceHandlerImpl;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.function.Consumer;

/**
 * Класс активности, хранящий в себе колбеки для взаимодействия с соурсами
 */
public class ActionBridge implements ExecutingBlock {

    private final ActionBlock actionBlock;
    private final SourceHandler sourceHandler;

    ActionBridge(ActionRepository actionRepository, PlatformTransactionManager txManager,
                 Consumer<ExecutionContext> afterExecuteConsumer, Consumer<ExecutionContext> errorExecutionConsumer,
                 ActionHandler actionHandler){
        this.sourceHandler = new SourceHandlerImpl(null, this::afterSourcesSet, this::sourcesSetError);
        this.actionBlock;
        sourceHandler.start();
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        actionBlock.execute(executionContext);
    }

    private void afterSourcesSet(ExecutionContext executionContext) {
        actionBlock.afterSourcesSet(executionContext);
    }

    private void sourcesSetError(ExecutionContext executionContext) {
        actionBlock.sourcesSetError(executionContext);
    }


}
