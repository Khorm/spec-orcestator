package com.petra.lib.source.handler.response;

import com.petra.lib.remote.response.Response;
import com.petra.lib.remote.response.ResponseSignal;
import com.petra.lib.remote.response.ResponseSignalType;
import com.petra.lib.source.SourceContext;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class SourceResponseManager {

//    SignalId answerSignalId;
//    Response response;
//
//    public void response(SourceContext sourceContext){
//            List<ResponseSignal> responseSignals = new ArrayList<>();
//            VariablesContainer outputSignalVariables = sourceContext.getArguments();
//            responseSignals.add(new ResponseSignal(answerSignalId, outputSignalVariables));
//
//            response.response(
//                    sourceContext.getScenarioId(),
//                    sourceContext.getActionId(),
//                    responseSignals,
//                    ResponseSignalType.SOURCE_RESPONSE,
//                    sourceContext.getRequestBlockId(),
//                    sourceContext.getRequestServiceName()
//            );
//    }
}
