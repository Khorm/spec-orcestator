package com.petra.lib.state.variable.neww.loaders.source;

import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.environment.context.variables.VariablesContext;
import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.output.OutputSocket;
import com.petra.lib.environment.output.enums.RequestType;
import com.petra.lib.state.variable.neww.loaders.VariableLoader;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SourceVariableLoader implements VariableLoader {

    OutputSocket outputSocket;
    VariableMapper variableMapper;

    SignalId sourceSignalId;

    @Override
    public void load(ActivityContext activityContext) {
        //Если пришел ответ на нажный сигнал, то распарсить его и применть к текущему контексту
        //иначе игнорировать.
        if (activityContext.getRequestType() == RequestType.SOURCE_RESPONSE
                && activityContext.getInitializingSignal().getSignalId().equals(sourceSignalId)) {
            VariablesContext variablesListFromSignal =
                    variableMapper.map(activityContext.getInitializingSignal().getVariablesContext());
            activityContext.syncCurrentInputVariableList(variablesListFromSignal);
        } else {
            outputSocket.consume(activityContext, RequestType.SOURCE_REQUEST);
        }
    }


}
