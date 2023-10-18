package com.petra.lib.XXXXXmanager.state;

public final class BlockFactory {
    //TODO сделать для каждого сигнала разные маппинги
    //TODO сделать для заполнения переменных хендлеры
    //TODO попроавить json
    //TODO попроавить сигналы чтобы они работали через имя сервиса
    //TODO дописать репу для queue

    public BlockFactory() {
    }

    Initiali
//
//
//    public JobStaticManager createAction(BlockModel blockModel,
//                                         UserHandler userHandler, JobRepository jobRepository,
//                                         JpaTransactionManager transactionManager,
//                                         TaskQueueManager taskQueueManager
//    ) {
//
//        VariableList variableList = VariableFactory.createVariableList(blockModel.getVariables());
//        VariableMapper sourcesVariableMapper = VariableMapperFactory.createVariableMapper(blockModel.getVariables());
//
//        Collection<Long> subscibedSignals = blockModel.getRequestSignals().stream().map(SignalModel::getId).collect(Collectors.toSet());
//        JobStaticManager jobStaticManager = JobStaticManager.createJobStaticManager(blockModel.getId(), blockModel.getName(), subscibedSignals, sourcesVariableMapper);
//
////        ResponseSignal responseSignal = SignalFactory.createAsyncResponseSignal(blockModel.getExecutionSignal(), bootstrapSignal, blockModel.getId());
//        Initializer initializer = new Initializer(
//                blockModel.getId(),
//                blockModel.getName(),
//                jobStaticManager,
//                jobRepository
//        );
//        jobStaticManager.putStateManager(initializer);
//
//        SourceSignalRequestManager sourceSignalRequestManager =
//                createSourceManagerState(jobStaticManager, blockModel.getSources(), blockModel.getId(), sourcesVariableMapper, blockModel.getName());
//        jobStaticManager.putStateManager(sourceSignalRequestManager);
//
//        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler,
//                jobStaticManager, transactionManager, jobRepository, blockModel.getId(), variableList, blockModel.getName());
//        jobStaticManager.putStateManager(userHandlerExecutor);
//
//        VariableMapper answerVariableMapper = VariableMapperFactory.createVariableMapper(blockModel.getResponseSignal().getVariableCollection());
//        ResponseState responseState = new ResponseState(taskQueueManager, answerVariableMapper, blockModel.getName());
//        jobStaticManager.putStateManager(responseState);
//
//        return jobStaticManager;
//    }
//
//    public JobStaticManager createSource(BlockModel blockModel,
//                                         UserHandler userHandler, JobRepository jobRepository,
//                                         JpaTransactionManager transactionManager,
////                                         ConfigurableListableBeanFactory beanFactory,
//                                         TaskQueueManager taskQueueManager) {
//
//        VariableList variableList = VariableFactory.createVariableList(blockModel.getVariables());
//        VariableMapper inputSourcesVariableMapper = VariableMapperFactory.createVariableMapper(blockModel.getVariables());
//
//        Collection<Long> subscibedSignals = blockModel.getRequestSignals().stream().map(SignalModel::getId).collect(Collectors.toSet());
//        JobStaticManager jobStaticManager = JobStaticManager.createJobStaticManager(blockModel.getId(), blockModel.getName(), subscibedSignals, inputSourcesVariableMapper);
//
////        ResponseSignal responseSignal = SignalFactory.createSyncResponseSignal(blockModel.getExecutionSignal(),
////                blockModel.getId(), beanFactory);
//
//
//        Initializer initializer = new Initializer(
//                blockModel.getId(),
//                blockModel.getName(),
//                jobStaticManager,
//                jobRepository
//        );
//        jobStaticManager.putStateManager(initializer);
//
//        SourceSignalRequestManager sourceSignalRequestManager =
//                createSourceManagerState(jobStaticManager, blockModel.getSources(), blockModel.getId(), VariableMapperFactory.createDummyMapper(), blockModel.getName());
//        jobStaticManager.putStateManager(sourceSignalRequestManager);
//
//
//        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(userHandler,
//                jobStaticManager, transactionManager, jobRepository, blockModel.getId(), variableList, blockModel.getName());
//        jobStaticManager.putStateManager(userHandlerExecutor);
//
//        ResponseState responseState = new ResponseState(taskQueueManager, VariableMapperFactory.createDummyMapper(), blockModel.getName());
//        jobStaticManager.putStateManager(responseState);
//
//        return jobStaticManager;
//    }
//
//
//    private SourceSignalRequestManager createSourceManagerState(JobStaticManager jobStaticManager,
//                                                                Collection<SourceSignalModel> sources,
//                                                                Long blockId, VariableMapper variableMapper,
//                                                                String blockName) {
//        SignalRequestListenerWrapper signalObserverWrapper = new SignalRequestListenerWrapper();
//        List<RequestSignal> sourceSignals = new ArrayList<>();
//        if (sources != null) {
//            for (SignalModel signalModel : sources) {
//                RequestSignal requestSignal = SignalFactory.createSyncRequestSignal(
//                        signalModel,
//                        blockId
//                );
//                requestSignal.setObserver(signalObserverWrapper);
//                sourceSignals.add(requestSignal);
//            }
//        }
//        SourceSignalRequestManager sourceSignalRequestManager = new SourceSignalRequestManager(sources, sourceSignals,
//                jobStaticManager, variableMapper, blockName);
//        signalObserverWrapper.setListener(sourceSignalRequestManager);
//
//        return sourceSignalRequestManager;
//    }


}
