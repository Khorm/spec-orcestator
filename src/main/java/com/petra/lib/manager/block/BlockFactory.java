package com.petra.lib.manager.block;

import com.petra.lib.context.Initializer;
import com.petra.lib.handler.UserHandler;
import com.petra.lib.handler.UserHandlerExecutor;
import com.petra.lib.manager.state.StateController;
import com.petra.lib.registration.ExecutionRepository;
import com.petra.lib.registration.RegistrationState;
import com.petra.lib.response.ResponseState;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalFactory;
import com.petra.lib.signal.SignalModel;
import com.petra.lib.signal.source.SourceSignalRequestManager;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.factory.VariableFactory;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class BlockFactory {

    private BlockFactory() {
    }

    public static ExecutionManager createAction(ActionModel actionModel,
                                                UserHandler userHandler, ExecutionRepository executionRepository,
                                                PlatformTransactionManager transactionManager,
                                                String bootstrapServers) {
        VariableList variableList = VariableFactory.createVariableList(actionModel.getVariables());
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(actionModel.getVariables());


        StateController stateController = StateController.createStateController();

        ExecutionManager executionManager = new ExecutionManager(variableList, variableMapper,
                stateController, transactionManager);

        ResponseSignal blockResponseSignal = initInitializerState(actionModel.getId(), executionManager,
                executionRepository, stateController, bootstrapServers, actionModel.getRequest());

        initDataRequestState(executionManager, actionModel.getSources(), stateController, actionModel.getId());
        initExecutingState(userHandler, executionManager, stateController);
        initRegistrationState(actionModel.getId(), transactionManager, executionRepository, executionManager, stateController);
        initResponseState(executionManager, stateController, blockResponseSignal);

        return executionManager;
    }

    private static ResponseSignal initInitializerState(Long blockId, ExecutionHandler executionHandler,
                                                       ExecutionRepository executionRepository, StateController stateController,
                                                       String bootstrapServers, SignalModel blockSignalModel
    ) {
        Initializer initializer = new Initializer(blockId, executionHandler, executionRepository);
        stateController.registerManager(initializer.getManagerState(), initializer);
        ResponseSignal blockResponseSignal = SignalFactory.createAsyncResponseSignal(
                bootstrapServers,
                initializer,
                blockId,
                blockSignalModel.getVersion(),
                blockSignalModel.getId()
        );
        initializer.setBlockResponseSignal(blockResponseSignal);
        return blockResponseSignal;
    }

    private static void initDataRequestState(ExecutionHandler executionHandler, Collection<SourceSignalModel> sources,
                                             StateController stateController, Long blockId) {
        SignalObserverWrapper signalObserverWrapper = new SignalObserverWrapper();
        List<RequestSignal> sourceSignals = new ArrayList<>();
        for (SignalModel signalModel : sources) {
            sourceSignals.add(SignalFactory.createSyncRequestSignal(
                    signalModel.getPath(),
                    signalObserverWrapper,
                    signalModel.getRequestVariableCollection(),
                    blockId,
                    signalModel.getVersion(),
                    signalModel.getId()
            ));
        }
        SourceSignalRequestManager sourceSignalRequestManager = new SourceSignalRequestManager(sources, sourceSignals,
                executionHandler);
        signalObserverWrapper.setListener(sourceSignalRequestManager);
        stateController.registerManager(sourceSignalRequestManager.getManagerState(), sourceSignalRequestManager);
    }

    private static void initExecutingState(UserHandler userHandler, ExecutionHandler executionHandler, StateController stateController) {
        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler, executionHandler);
        stateController.registerManager(userHandlerExecutor.getManagerState(), userHandlerExecutor);

    }

    private static void initRegistrationState(Long blockId, PlatformTransactionManager transactionManager,
                                              ExecutionRepository executionRepository,
                                              ExecutionHandler executionHandler, StateController stateController) {
        RegistrationState registrationState = new RegistrationState(blockId, executionHandler,
                executionRepository, transactionManager);
        stateController.registerManager(registrationState.getManagerState(), registrationState);
    }

//    private static void initReleaseState(ExecutionHandler executionHandler, StateController stateController) {
//        SignalObserverWrapper signalObserverWrapper = new SignalObserverWrapper();
//        Signal inputSignal = SignalFactory.createReceiverSignal(actionModel.getRequest(), signalObserverWrapper);
//        ReleaseState releaseState = new ReleaseState(inputSignal);
//        stateController.registerManager(releaseState.getManagerState(), releaseState);
//    }

    private static void initResponseState(ExecutionHandler executionHandler, StateController stateController, ResponseSignal blockSignal) {
        ResponseState responseState = new ResponseState(blockSignal, executionHandler);
        stateController.registerManager(responseState.getManagerState(), responseState);
    }


}
