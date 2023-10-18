package com.petra.lib.state.variable.group.loaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.XXXXXcontext.ContextFabric;
import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.state.variable.group.handler.VariableContext;
import com.petra.lib.state.variable.group.handler.VariableHandler;
import com.petra.lib.block.ProcessValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
class HandlerVariableLoader implements VariableLoader {


    final ObjectMapper objectMapper = new ObjectMapper();
    final Collection<Long> filingProcessVariableIds;
    final List<VariableHandlerKeeper> variableHandlers;
    BiConsumer<Collection<Long>, DirtyContext> filledHandler;
    Consumer<DirtyContext> errorHandler;


    @RequiredArgsConstructor
    public static class VariableHandlerKeeper {
        private final Long processVariableId;
        private final VariableHandler variableHandler;
    }


    @Override
    public void load(DirtyContext actionContext) {
        VariableContext variableContext = ContextFabric.createUserContext(actionContext);
        try {
            for (VariableHandlerKeeper variableHandlerKeeper : variableHandlers) {
                Object result = variableHandlerKeeper.variableHandler.map(variableContext);
                ProcessValue processValue = new ProcessValue(variableHandlerKeeper.processVariableId, objectMapper.writeValueAsString(result));
                actionContext.setValue(processValue);
            }
        } catch (JsonProcessingException e) {
            errorHandler.accept(actionContext);
        }
        filledHandler.accept(filingProcessVariableIds, actionContext);
    }

    @Override
    public Collection<Long> getFilingProcessVariableIds() {
        return filingProcessVariableIds;
    }

    @Override
    public void setFillHandler(BiConsumer<Collection<Long>, DirtyContext> fillHandler) {
        this.filledHandler = fillHandler;
    }

    @Override
    public void setErrorHandler(Consumer<DirtyContext> errorHandler) {
        this.errorHandler = errorHandler;
    }
}
