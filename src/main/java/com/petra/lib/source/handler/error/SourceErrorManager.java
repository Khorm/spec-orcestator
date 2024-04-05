package com.petra.lib.source.handler.error;

import com.petra.lib.remote.response.Response;
import com.petra.lib.remote.response.ResponseSignal;
import com.petra.lib.remote.response.ResponseSignalType;
import com.petra.lib.source.SourceContext;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Deprecated
public class SourceErrorManager {

//    SignalId answerSignalId;
//    Response response;
//    public void error(SourceContext sourceContext){
//        List<ResponseSignal> responseSignals = new ArrayList<>();
//        responseSignals.add(new ResponseSignal(answerSignalId, null));
//
//        response.response(
//                sourceContext.getScenarioId(),
//                sourceContext.getActionId(),
//                responseSignals,
//                ResponseSignalType.SOURCE_ERROR,
//                sourceContext.getRequestBlockId(),
//                sourceContext.getRequestServiceName()
//        );
//    }
}
