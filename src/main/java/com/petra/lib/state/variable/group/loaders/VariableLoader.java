package com.petra.lib.state.variable.group.loaders;

import com.petra.lib.context.ExecutionContext;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface VariableLoader {
    void load(ExecutionContext actionContext);
    Collection<Long> getFilingProcessVariableIds();
    void setFillHandler(BiConsumer<Collection<Long>, ExecutionContext> fillHandler);
    void setErrorHandler(Consumer<ExecutionContext> errorHandler);
}
