package com.petra.lib.state.variable.group.loaders;

import com.petra.lib.context.ExecutionContext;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import com.petra.lib.signal.response.ResponseType;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
class SourceVariableLoader implements VariableLoader {

    final SignalId signalId;
    final SignalRequestManager signalRequestManager;
    final VariableMapper contextToSignalMapper;
    final VariableMapper signalToContextMapper;
    final Collection<Long> filingProcessVariableIds;
    BiConsumer<Collection<Long>, ExecutionContext> filledHandler;
    Consumer<ExecutionContext> errorHandler;


    @Override
    public void load(final ExecutionContext context) {
        Collection<ProcessValue> signalValues = contextToSignalMapper.map(context.getProcessValues());
        signalRequestManager.request(signalId, signalValues, context.getScenarioId(), context.getBlockId(), responseDto -> {
            if (responseDto.getResponseType() == ResponseType.RESPONSE){
                Collection<ProcessValue> newContextVariables = signalToContextMapper.map(responseDto.getSignalVariables());
                context.setValues(newContextVariables);
                filledHandler.accept(filingProcessVariableIds, context);
            } else {
                errorHandler.accept(context);
            }
        });
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
