package com.petra.lib.manager.block;

import com.petra.lib.manager.BlockMap;
import com.petra.lib.manager.models.BlockModel;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import com.petra.lib.signal.response.controller.ResponseReadyListener;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.MapperVariableModel;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;
import com.petra.lib.worker.error.ErrorState;
import com.petra.lib.worker.handler.UserHandler;
import com.petra.lib.worker.handler.impl.UserHandlerExecutor;
import com.petra.lib.worker.initialize.Initializer;
import com.petra.lib.worker.repo.JobRepository;
import com.petra.lib.worker.response.ResponseState;
import com.petra.lib.worker.variable.VariableManager;
import com.petra.lib.worker.variable.group.handler.VariableHandler;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockBuilder {

    public synchronized BlockMap build(Collection<BlockModel> blockModels, Collection<VariableHandler> variableHandlers, Collection<UserHandler> userHandlers){
        for (BlockModel blockModel : blockModels){
            JobStaticManagerImpl jobStaticManager = new JobStaticManagerImpl();
        }
    }



    private Block buildBlock(BlockModel blockModel, JpaTransactionManager jpaTransactionManager, Collection<VariableHandler> variableHandlers,
                             SignalRequestManager signalRequestManager, ResponseReadyListener responseReadyListener){
        JobStaticManagerImpl jobStaticManager = new JobStaticManagerImpl(blockModel);

        JobRepository jobRepository = new JobRepository(jpaTransactionManager);
        Initializer initializer = new Initializer(blockModel, jobStaticManager, jobRepository);
        jobStaticManager.putStateManager(initializer);


        VariableManager variableManager = new VariableManager(blockModel, variableHandlers, jobStaticManager, signalRequestManager);
        jobStaticManager.putStateManager(variableManager);

        VariableList variableList = new VariableList(blockModel.getVariables());
        UserHandlerExecutor userHandlerExecutor = new UserHandlerExecutor(, jobStaticManager, jpaTransactionManager,
                jobRepository, blockModel.getBlockId(), variableList, blockModel.getName());
        jobStaticManager.putStateManager(userHandlerExecutor);


        /**
         * TODO хз надо нет, либо в ответ отправлять для следующих сигналов что не очень,
         * либо напрямую отпралять наполнение контекста и парсить на оркестраторе(Лучше так наверное)
         */
        Map<SignalId, Collection<MapperVariableModel>> variableMapperMap = new HashMap<>();
        blockModel.getMappedVariablesToResponseSignals().forEach(mapperVariableModel -> {
            if (!variableMapperMap.containsKey(mapperVariableModel.getSignalId())){
                variableMapperMap.put(mapperVariableModel.getSignalId(), new ArrayList<>());
            }
            variableMapperMap.get(mapperVariableModel.getSignalId()).add(mapperVariableModel);
        });

        Map<SignalId, VariableMapper> responseMappers = new HashMap<>();
        variableMapperMap.forEach((signalId, mappersForSignal) ->{
            responseMappers.put(signalId, VariableMapperFactory.createVariableMapper(mappersForSignal));
        });

        ResponseState responseState = new ResponseState(responseMappers, blockModel.getName(), responseReadyListener,)

        ErrorState errorState = new ErrorState()
    }
}
