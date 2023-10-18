package com.petra.lib.state.variable.group.loaders;

import com.petra.lib.XXXXXcontext.DirtyContext;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface VariableLoader {
    void load(DirtyContext actionContext);
    Collection<Long> getFilingProcessVariableIds();
    void setFillHandler(BiConsumer<Collection<Long>, DirtyContext> fillHandler);
    void setErrorHandler(Consumer<DirtyContext> errorHandler);
}
