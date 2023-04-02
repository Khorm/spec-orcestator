package com.petra.lib.manager.factory;

import com.petra.lib.context.Initializer;
import com.petra.lib.handler.UserHandler;
import com.petra.lib.handler.UserHandlerExecutor;
import com.petra.lib.manager.ExecutionManager;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.ThreadManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.manager.state.StateControllerImpl;
import com.petra.lib.registration.RegistrationState;
import com.petra.lib.release.ReleaseState;
import com.petra.lib.response.ResponseState;
import com.petra.lib.signal.Signal;
import com.petra.lib.signal.SignalFactory;
import com.petra.lib.signal.SignalModel;
import com.petra.lib.signal.SignalObserver;
import com.petra.lib.signal.source.SourceSignalRequestManager;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.factory.VariableFactory;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;

import java.util.*;

public final class BlockFactory {

    private BlockFactory() {
    }

    public static ExecutionManager createAction(ActionModel actionModel, ThreadManager threadManager, UserHandler userHandler) {
        VariableList variableList = VariableFactory.createVariableList(actionModel.getVariables());
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(actionModel.getVariables());


        Map<ExecutionState, ExecutionStateManager> stateList = new HashMap<>();
        ExecutionManagerWrapper executionManagerWrapper = new ExecutionManagerWrapper();

        Initializer initializer = new Initializer(actionModel.getId(), executionManagerWrapper);
        stateList.put(ExecutionState.INITIALIZING, initializer);

        stateList.put(ExecutionState.REQUEST_SOURCE_DATA, createDataRequest(actionModel.getSources(), executionManagerWrapper));

        stateList.put(ExecutionState.EXECUTING, new UserHandlerExecutor(userHandler, executionManagerWrapper));

        stateList.put(ExecutionState.EXECUTION_REGISTRATION, new RegistrationState(actionModel.getId(), executionManagerWrapper));

        SignalObserverWrapper signalObserverWrapper = new SignalObserverWrapper();
        Signal inputSignal = SignalFactory.createSignal(actionModel.getRequest(), signalObserverWrapper);
        stateList.put(ExecutionState.EXECUTION_RELEASE, new ReleaseState(inputSignal));

        ResponseState responseState = new ResponseState(inputSignal, executionManagerWrapper);
        signalObserverWrapper.setListener(responseState);
        stateList.put(ExecutionState.EXECUTION_RESPONSE, responseState);

        StateControllerImpl stateController = new StateControllerImpl(stateList);

        ExecutionManager executionManager = new ExecutionManager(variableList, threadManager, variableMapper, stateController);
        executionManagerWrapper.setExecutionManager(executionManager);

        return executionManager;
    }


    private static SourceSignalRequestManager createDataRequest(Collection<SourceSignalModel> sources, ExecutionManagerWrapper executionManagerWrapper){
        SignalObserverWrapper signalObserverWrapper = new SignalObserverWrapper();
        List<Signal> sourceSignals = new ArrayList<>();
        for (SignalModel signalModel : sources) {
            sourceSignals.add(SignalFactory.createSignal(signalModel, signalObserverWrapper));
        }
        SourceSignalRequestManager sourceSignalRequestManager = new SourceSignalRequestManager(sources, sourceSignals, executionManagerWrapper);
        signalObserverWrapper.setListener(signalObserverWrapper);
        return sourceSignalRequestManager;
    }



}
