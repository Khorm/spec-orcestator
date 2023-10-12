package com.petra.lib.state.variable.group.loaders;

import com.petra.lib.context.ExecutionContext;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
class RequestVariableLoader implements VariableLoader {

    final VariableMapper fromRequestToContextMapper;
    final Collection<Long> filingProcessVariableIds;
    BiConsumer<Collection<Long>, ExecutionContext> filledHandler;
    Consumer<ExecutionContext> errorHandler;


    @Override
    public void load(ExecutionContext actionContext) {
        try {
            Collection<ProcessValue> mappedValues = fromRequestToContextMapper.map(actionContext.getRequestValues());
            actionContext.setValues(mappedValues);
            filledHandler.accept(filingProcessVariableIds, actionContext);
        } catch (Exception e) {
            errorHandler.accept(actionContext);
        }
    }

    @Override
    public Collection<Long> getFilingProcessVariableIds() {
        return filingProcessVariableIds;
    }

    @Override
    public void setFillHandler(BiConsumer<Collection<Long>, ExecutionContext> fillHandler) {
        this.filledHandler = fillHandler;
    }

    @Override
    public void setErrorHandler(Consumer<ExecutionContext> errorHandler) {
        this.errorHandler = errorHandler;
    }
}
