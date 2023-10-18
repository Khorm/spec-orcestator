package com.petra.lib.block;

@Deprecated
public class BlockBuilder {

//    public synchronized BlockMap build(Collection<BlockModel> blockModels, Collection<VariableHandler> variableHandlers, Collection<UserHandler> userHandlers){
//        for (BlockModel blockModel : blockModels){
//            BlockImpl jobStaticManager = new BlockImpl(stateQueue, id, blockName, listeningSignalIds, statelessVariableList);
//        }
//    }
//
//
//
//    private Block buildBlock(BlockModel blockModel, JpaTransactionManager jpaTransactionManager, Collection<VariableHandler> variableHandlers,
//                             SignalRequestManager signalRequestManager, ResponseReadyListener responseReadyListener){
//        BlockImpl jobStaticManager = new BlockImpl(stateQueue, blockModel);
//
//        JobRepository jobRepository = new JobRepository(jpaTransactionManager, jpaTransactionManager1);
//        Initializer initializer = new Initializer(blockModel, jobStaticManager, jobRepository);
//        jobStaticManager.putStateManager(initializer);
//
//
//        FillVariablesStateHandler fillVariablesJobExecutor = new FillVariablesStateHandler(blockModel, variableHandlers, jobStaticManager, signalRequestManager);
//        jobStaticManager.putStateManager(fillVariablesJobExecutor);
//
//        StatelessVariableList statelessVariableList = new StatelessVariableList(blockModel.getStatelessVariables());
//        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(, jobStaticManager, jpaTransactionManager,
//                jobRepository, blockModel.getBlockId(), statelessVariableList, blockModel.getName());
//        jobStaticManager.putStateManager(userHandlerExecutor);
//
//
//        /**
//         * TODO хз надо нет, либо в ответ отправлять для следующих сигналов что не очень,
//         * либо напрямую отпралять наполнение контекста и парсить на оркестраторе(Лучше так наверное)
//         */
//        Map<SignalId, Collection<MapperVariableModel>> variableMapperMap = new HashMap<>();
//        blockModel.getMappedVariablesToResponseSignals().forEach(mapperVariableModel -> {
//            if (!variableMapperMap.containsKey(mapperVariableModel.getSignalId())){
//                variableMapperMap.put(mapperVariableModel.getSignalId(), new ArrayList<>());
//            }
//            variableMapperMap.get(mapperVariableModel.getSignalId()).add(mapperVariableModel);
//        });
//
//        Map<SignalId, VariableMapper> responseMappers = new HashMap<>();
//        variableMapperMap.forEach((signalId, mappersForSignal) ->{
//            responseMappers.put(signalId, VariableMapperFactory.createVariableMapper(mappersForSignal));
//        });
//
//        ResponseState responseState = new ResponseState(responseMappers, blockModel.getName(), responseReadyListener,)
//
//        ErrorState errorState = new ErrorState()
//    }
}
