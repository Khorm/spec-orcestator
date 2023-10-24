package com.petra.lib.state.variable.group.loaders;

import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.request.controller.SignalRequestManager;
import com.petra.lib.XXXXXXsignal.response.ResponseType;
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
    BiConsumer<Collection<Long>, DirtyContext> filledHandler;
    Consumer<DirtyContext> errorHandler;


    @Override
    public void load(final DirtyContext context) {
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
    public void setFillHandler(BiConsumer<Collection<Long>, DirtyContext> fillHandler) {
        this.filledHandler = fillHandler;
    }

    @Override
    public void setErrorHandler(Consumer<DirtyContext> errorHandler) {
        this.errorHandler = errorHandler;
    }
}
