package com.petra.lib.state.variable.group.loaders;

import com.petra.lib.state.variable.model.SourceLoaderModel;
import com.petra.lib.XXXXXXsignal.request.controller.SignalRequestManager;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;

import java.util.Collection;
import java.util.List;

public class VariableFabric {
    static VariableLoader getHandlerVariable(Collection<Long> filingProcessVariableIds,
                                             List<HandlerVariableLoader.VariableHandlerKeeper> variableHandlers) {
        return new HandlerVariableLoader(filingProcessVariableIds, variableHandlers);
    }

    static VariableLoader getInitVariableLoader(VariableMapper fromRequestToContextMapper,
                                                Collection<Long> filingProcessVariableIds) {
        return new RequestVariableLoader(fromRequestToContextMapper, filingProcessVariableIds);
    }

    static VariableLoader getSourceVariableLoader( SourceLoaderModel sourceLoaderModel, SignalRequestManager signalRequestManager) {
        VariableMapper contextToSource = VariableMapperFactory.createVariableMapper(sourceLoaderModel.getToSourceSignalVariablesMap());
        VariableMapper sourceToContext = VariableMapperFactory.createVariableMapper(sourceLoaderModel.getToContextVariablesMap());
        return new SourceVariableLoader(sourceLoaderModel.getSourceSignalId(), signalRequestManager, contextToSource,
                sourceToContext, sourceLoaderModel.getVariables());
    }
}
