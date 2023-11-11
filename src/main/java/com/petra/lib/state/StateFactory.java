package com.petra.lib.state;

import com.petra.lib.block.Block;
import com.petra.lib.block.constructor.source.BlockGroup;
import com.petra.lib.block.constructor.source.BlockRemoteSourceModel;
import com.petra.lib.block.constructor.source.BlockSourceHandlerModel;
import com.petra.lib.remote.output.manager.OutputFactory;
import com.petra.lib.remote.output.manager.SignalRequestStrategy;
import com.petra.lib.state.initialize.Initializer;
import com.petra.lib.state.variable.VariableLoaderHandler;
import com.petra.lib.state.variable.group.VariableGroup;
import com.petra.lib.state.variable.group.VariableGroupImpl;
import com.petra.lib.state.variable.loaders.VariableLoader;
import com.petra.lib.state.variable.loaders.handler.HandlerLoader;
import com.petra.lib.state.variable.loaders.handler.UserVariableHandler;
import com.petra.lib.state.variable.loaders.source.SourceLoader;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.pure.PureVariableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StateFactory {

    public static StateHandler createInitState(VariableMapper variableMapper,
                                               Block block,
                                               TransactionManager transactionManager) {
        return new Initializer(variableMapper, block, transactionManager);
    }

    public static StateHandler createValueLoader(Collection<BlockGroup> blockSourceModels,
                                                 Map<String, UserVariableHandler> sourceHandlersNames,
                                                 String currentServiceName,
                                                 Block blockManager,
                                                 PureVariableList pureVariableList) {

        List<VariableGroup> variableGroups = new ArrayList<>();
        for (BlockGroup blockGroup : blockSourceModels) {
            List<VariableLoader> variableLoaders = createHandlerLoader(blockGroup, sourceHandlersNames,
                    pureVariableList);
            variableLoaders.addAll(createSourceLoadersForGroup(blockGroup, currentServiceName));
            VariableGroup variableGroup = new VariableGroupImpl(variableLoaders);
            variableGroups.add(variableGroup);
        }
        return  new VariableLoaderHandler(blockManager, variableGroups);
    }


    public static StateHandler createUserExecutor(){

    }

    private static List<VariableLoader> createSourceLoadersForGroup(BlockGroup blockGroup, String currentServiceName) {
        List<VariableLoader> loadersForGroup = new ArrayList<>();
        for (BlockRemoteSourceModel blockRemoteSourceModel : blockGroup.getBlockRemoteSourceModels()) {
            SignalRequestStrategy signalRequestStrategy =
                    OutputFactory.getSourceStrategy(blockRemoteSourceModel.getSourceServiceName(),
                            blockRemoteSourceModel.getLocalVariableIds(), blockRemoteSourceModel.getSourceId(),
                            currentServiceName);
            SourceLoader sourceLoader = new SourceLoader(blockRemoteSourceModel.getLocalVariableIds(),
                    signalRequestStrategy);

            loadersForGroup.add(sourceLoader);
        }
        return loadersForGroup;
    }
    private static List<VariableLoader> createHandlerLoader(BlockGroup blockGroup,
                                                            Map<String, UserVariableHandler> sourceHandlersNames,
                                                            PureVariableList pureVariables) {
        List<VariableLoader> loadersForGroup = new ArrayList<>();
        for (BlockSourceHandlerModel sourceHandlerModel : blockGroup.getSourceHandlerModels()) {
            HandlerLoader handlerLoader = new HandlerLoader(sourceHandlersNames.get(sourceHandlerModel.getHandlerName()),
                    pureVariables.getVariableById(sourceHandlerModel.getLocalVariableId()));
            loadersForGroup.add(handlerLoader);
        }
        return loadersForGroup;
    }
}
