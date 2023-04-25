package com.petra.lib.manager.block;

import com.petra.lib.worker.initialize.Initializer;
import com.petra.lib.handler.UserHandler;
import com.petra.lib.handler.UserHandlerExecutor;
import com.petra.lib.manager.models.ActionModel;
import com.petra.lib.manager.state.StateController;
import com.petra.lib.registration.JobRepository;
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
import com.petra.lib.worker.manager.JobStaticManager;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class BlockFactory {

    private BlockFactory() {
    }

//    public static ExecutionManager createAction(ActionModel actionModel,
//                                                UserHandler userHandler, ExecutionRepository executionRepository,
//                                                PlatformTransactionManager transactionManager,
//                                                String bootstrapServers) {
//        VariableList variableList = VariableFactory.createVariableList(actionModel.getVariables());
//        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(actionModel.getVariables());
//
//
//        StateController stateController = StateController.createStateController();
//
//        ExecutionManager executionManager = new ExecutionManager(variableList, variableMapper,
//                stateController, transactionManager);
//
//        ResponseSignal blockResponseSignal = initInitializerState(actionModel.getId(), executionManager,
//                executionRepository, stateController, bootstrapServers, actionModel.getExecutionSignal());
//
//        initDataRequestState(executionManager, actionModel.getSources(), stateController, actionModel.getId());
//        initExecutingState(userHandler, executionManager, stateController);
//        initRegistrationState(actionModel.getId(), transactionManager, executionRepository, executionManager, stateController);
//        initResponseState(executionManager, stateController, blockResponseSignal);
//
//        return executionManager;
//    }


    public static JobStaticManager createSource(ActionModel actionModel,
                                                UserHandler userHandler, JobRepository jobRepository,
                                                PlatformTransactionManager transactionManager,
                                                String sourceUrl, ConfigurableListableBeanFactory beanFactory) {
        VariableList variableList = VariableFactory.createVariableList(actionModel.getVariables());
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(actionModel.getVariables());
        JobStaticManager jobStaticManager = JobStaticManager.createJobStaticManager(transactionManager);

//        ResponseSignal blockResponseSignal = initInitializerState(actionModel.getId(), jobStaticManagerImpl,
//                executionRepository, stateController, sourceUrl, actionModel.getExecutionSignal());

        ResponseSignal responseSignal = SignalFactory.createSyncResponseSignal(actionModel.getExecutionSignal(),
                actionModel.getId(), beanFactory);


        Initializer initializer = new Initializer(
                actionModel.getId(),
                actionModel.getName(),
                jobStaticManager,
                jobRepository,
                responseSignal,
                transactionManager,
                variableList,
                variableMapper
        );
        responseSignal.setListener(initializer);
        jobStaticManager.setStateManager(initializer);
//
//
//
//
//
//        JobStaticManagerImpl jobStaticManagerImpl = new JobStaticManagerImpl(variableList, variableMapper,
//                stateController, transactionManager);
//
//
//
//        initDataRequestState(jobStaticManagerImpl, actionModel.getSources(), stateController, actionModel.getId());
//        initExecutingState(userHandler, jobStaticManagerImpl, stateController);
//        initRegistrationState(actionModel.getId(), transactionManager, jobRepository, jobStaticManagerImpl, stateController);
//        initResponseState(jobStaticManagerImpl, stateController, blockResponseSignal);

        return jobStaticManager;
    }

//    private static ResponseSignal initInitializerState(Long blockId, JobStaticManager jobStaticManager,
//                                                       JobRepository jobRepository, StateController stateController,
//                                                       String URL, SignalModel blockSignalModel) {
//        Initializer initializer = new Initializer(blockId, jobStaticManager, jobRepository);
//        stateController.registerManager(initializer.getManagerState(), initializer);
////        ResponseSignal blockResponseSignal = SignalFactory.createAsyncResponseSignal(
////                bootstrapServers,
////                initializer,
////                blockId,
////                blockSignalModel.getVersion(),
////                blockSignalModel.getId()
////        );
//        ResponseSignal blockResponseSignal = SignalFactory.createSyncResponseSignal(
//                URL,
//                8080
//                ,initializer,
//                blockId,
//                blockSignalModel.getVersion(),
//                blockSignalModel.getId()
//        );
//        initializer.setBlockResponseSignal(blockResponseSignal);
//        return blockResponseSignal;
//    }

    private static void initDataRequestState(JobStaticManager jobStaticManager, Collection<SourceSignalModel> sources,
                                             StateController stateController, Long blockId) {
        SignalRequestListenerWrapper signalObserverWrapper = new SignalRequestListenerWrapper();
        List<RequestSignal> sourceSignals = new ArrayList<>();
        for (SignalModel signalModel : sources) {
//            sourceSignals.add(SignalFactory.createSyncRequestSignal(
//                    signalModel.getPath(),
//                    signalObserverWrapper,
//                    signalModel.getRequestVariableCollection(),
//                    blockId,
//                    signalModel.getVersion(),
//                    signalModel.getId()
//            ));
        }
        SourceSignalRequestManager sourceSignalRequestManager = new SourceSignalRequestManager(sources, sourceSignals,
                jobStaticManager);
        signalObserverWrapper.setListener(sourceSignalRequestManager);
        stateController.registerManager(sourceSignalRequestManager.getManagerState(), sourceSignalRequestManager);
    }

    private static void initExecutingState(UserHandler userHandler, JobStaticManager jobStaticManager, StateController stateController) {
        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler, jobStaticManager);
        stateController.registerManager(userHandlerExecutor.getManagerState(), userHandlerExecutor);

    }

    private static void initRegistrationState(Long blockId, PlatformTransactionManager transactionManager,
                                              JobRepository jobRepository,
                                              JobStaticManager jobStaticManager, StateController stateController) {
        RegistrationState registrationState = new RegistrationState(blockId, jobStaticManager,
                jobRepository, transactionManager);
        stateController.registerManager(registrationState.getManagerState(), registrationState);
    }

//    private static void initReleaseState(ExecutionHandler executionHandler, StateController stateController) {
//        SignalObserverWrapper signalObserverWrapper = new SignalObserverWrapper();
//        Signal inputSignal = SignalFactory.createReceiverSignal(actionModel.getRequest(), signalObserverWrapper);
//        ReleaseState releaseState = new ReleaseState(inputSignal);
//        stateController.registerManager(releaseState.getManagerState(), releaseState);
//    }

    private static void initResponseState(JobStaticManager jobStaticManager, StateController stateController, ResponseSignal blockSignal) {
        ResponseState responseState = new ResponseState(blockSignal, jobStaticManager);
        stateController.registerManager(responseState.getManagerState(), responseState);
    }


}
