package com.petra.lib.manager.state;

import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.block.SourceSignalModel;
import com.petra.lib.manager.models.BlockModel;
import com.petra.lib.queue.InputQueue;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalFactory;
import com.petra.lib.signal.SignalModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.factory.VariableFactory;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;
import com.petra.lib.worker.handler.UserHandler;
import com.petra.lib.worker.handler.impl.UserHandlerExecutor;
import com.petra.lib.worker.initialize.Initializer;
import com.petra.lib.worker.repo.JobRepository;
import com.petra.lib.worker.response.ResponseState;
import com.petra.lib.worker.source.SignalRequestListenerWrapper;
import com.petra.lib.worker.source.SourceSignalRequestManager;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class BlockFactory {
    сделать для каждого сигнала разные маппинги
    сделать для заполнения переменных хендлеры
    попроавить json
    попроавить сигналы чтобы они работали через имя сервиса

    public BlockFactory() {
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

    public JobStaticManager createAction(BlockModel blockModel,
                                         UserHandler userHandler, JobRepository jobRepository,
                                         JpaTransactionManager transactionManager,
//                                         String path,
//                                         ConfigurableListableBeanFactory beanFactory,
//                                         String bootstrapSignal
                                         InputQueue inputQueue
    ) {

        VariableList variableList = VariableFactory.createVariableList(blockModel.getVariables());
        VariableMapper sourcesVariableMapper = VariableMapperFactory.createVariableMapper(blockModel.getVariables());

        Collection<Long> subscibedSignals = blockModel.getRequestSignals().stream().map(SignalModel::getId).collect(Collectors.toSet());
        JobStaticManager jobStaticManager = JobStaticManager.createJobStaticManager(blockModel.getId(), blockModel.getName(),subscibedSignals, sourcesVariableMapper);

//        ResponseSignal responseSignal = SignalFactory.createAsyncResponseSignal(blockModel.getExecutionSignal(), bootstrapSignal, blockModel.getId());
        Initializer initializer = new Initializer(
                blockModel.getId(),
                blockModel.getName(),
                jobStaticManager,
                jobRepository
        );
        jobStaticManager.putStateManager(initializer);

        SourceSignalRequestManager sourceSignalRequestManager =
                createSourceManagerState(jobStaticManager, blockModel.getSources(), blockModel.getId(), sourcesVariableMapper, blockModel.getName());
        jobStaticManager.putStateManager(sourceSignalRequestManager);

        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler,
                jobStaticManager, transactionManager, jobRepository, blockModel.getId(), variableList, blockModel.getName());
        jobStaticManager.putStateManager(userHandlerExecutor);

        VariableMapper answerVariableMapper = VariableMapperFactory.createVariableMapper(blockModel.getResponseSignal().getVariableCollection());
        ResponseState responseState = new ResponseState(inputQueue,answerVariableMapper, blockModel.getName());
        jobStaticManager.putStateManager(responseState);

        return jobStaticManager;
    }

    public JobStaticManager createSource(BlockModel blockModel,
                                         UserHandler userHandler, JobRepository jobRepository,
                                         JpaTransactionManager transactionManager,
//                                         ConfigurableListableBeanFactory beanFactory,
                                         InputQueue inputQueue) {

        VariableList variableList = VariableFactory.createVariableList(blockModel.getVariables());
        VariableMapper inputSourcesVariableMapper = VariableMapperFactory.createVariableMapper(blockModel.getVariables());

        Collection<Long> subscibedSignals = blockModel.getRequestSignals().stream().map(SignalModel::getId).collect(Collectors.toSet());
        JobStaticManager jobStaticManager = JobStaticManager.createJobStaticManager(blockModel.getId(), blockModel.getName(),subscibedSignals, inputSourcesVariableMapper);

//        ResponseSignal responseSignal = SignalFactory.createSyncResponseSignal(blockModel.getExecutionSignal(),
//                blockModel.getId(), beanFactory);


        Initializer initializer = new Initializer(
                blockModel.getId(),
                blockModel.getName(),
                jobStaticManager,
                jobRepository
        );
        jobStaticManager.putStateManager(initializer);

        SourceSignalRequestManager sourceSignalRequestManager =
                createSourceManagerState(jobStaticManager, blockModel.getSources(), blockModel.getId(), VariableMapperFactory.createDummyMapper(), blockModel.getName());
        jobStaticManager.putStateManager(sourceSignalRequestManager);


        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler,
                jobStaticManager, transactionManager, jobRepository, blockModel.getId(), variableList, blockModel.getName());
        jobStaticManager.putStateManager(userHandlerExecutor);

        ResponseState responseState = new ResponseState(inputQueue, VariableMapperFactory.createDummyMapper(), blockModel.getName());
        jobStaticManager.putStateManager(responseState);

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

    private SourceSignalRequestManager createSourceManagerState(JobStaticManager jobStaticManager,
                                                                Collection<SourceSignalModel> sources,
                                                                Long blockId, VariableMapper variableMapper,
                                                                String blockName) {
        SignalRequestListenerWrapper signalObserverWrapper = new SignalRequestListenerWrapper();
        List<RequestSignal> sourceSignals = new ArrayList<>();
        if (sources != null) {
            for (SignalModel signalModel : sources) {
                RequestSignal requestSignal = SignalFactory.createSyncRequestSignal(
                        signalModel,
                        blockId
                );
                requestSignal.setObserver(signalObserverWrapper);
                sourceSignals.add(requestSignal);
            }
        }
        SourceSignalRequestManager sourceSignalRequestManager = new SourceSignalRequestManager(sources, sourceSignals,
                jobStaticManager, variableMapper, blockName);
        signalObserverWrapper.setListener(sourceSignalRequestManager);

        return sourceSignalRequestManager;
    }

//    private UserHandlerExecutor createUserHandlerState(UserHandler userHandler, JobStaticManager jobStaticManager, EntityManagerFactory entityManagerFactory,
//                                                       PlatformTransactionManager transactionManager, JobRepository jobRepository, Long blockId) {
//        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler,
//                jobStaticManager, entityManagerFactory, transactionManager, jobRepository, blockId);
//        return userHandlerExecutor;
//    }

//    private static void initExecutingState(UserHandler userHandler, JobStaticManager jobStaticManager, StateController stateController) {
//        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler, jobStaticManager);
//        stateController.registerManager(userHandlerExecutor.getManagerState(), userHandlerExecutor);
//
//    }

//    private  RegistrationState createRegistrationState(Long blockId, PlatformTransactionManager transactionManager,
//                                              JobRepository jobRepository,
//                                              JobStaticManager jobStaticManager) {
//        RegistrationState registrationState = new RegistrationState(blockId, jobStaticManager,
//                jobRepository, transactionManager);
//        return registrationState;
//    }

//    private static void initReleaseState(ExecutionHandler executionHandler, StateController stateController) {
//        SignalObserverWrapper signalObserverWrapper = new SignalObserverWrapper();
//        Signal inputSignal = SignalFactory.createReceiverSignal(actionModel.getRequest(), signalObserverWrapper);
//        ReleaseState releaseState = new ReleaseState(inputSignal);
//        stateController.registerManager(releaseState.getManagerState(), releaseState);
//    }

//    private static void initResponseState(JobStaticManager jobStaticManager, StateController stateController, ResponseSignal blockSignal) {
//        ResponseState responseState = new ResponseState(blockSignal, jobStaticManager);
//        stateController.registerManager(responseState.getManagerState(), responseState);
//    }


}
