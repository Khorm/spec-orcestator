package com.petra.lib.response;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.worker.manager.JobStaticManager;
import com.petra.lib.manager.block.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseState implements JobStateManager, SignalRequestListener {

    ResponseSignal responseSignal;
    JobStaticManager jobStaticManager;

    @Override
    public void execute(JobContext jobContext) throws Exception {
        UUID scenarioId = jobContext.getScenarioId();
        Collection<ProcessVariable> variableList = jobContext.getVariablesList();
        responseSignal.setAnswer(variableList, scenarioId);
        jobStaticManager.executeNext(jobContext, getManagerState());
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTION_RESPONSE;
    }

    @Override
    public void start() {
    }

    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        //do nothin
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        e.printStackTrace();
    }
}
