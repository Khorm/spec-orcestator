package com.petra.lib.state.variable;

import com.petra.lib.block.Block;
import com.petra.lib.block.model.source.BlockGroup;
import com.petra.lib.block.model.source.BlockRemoteSourceModel;
import com.petra.lib.block.model.source.BlockSourceHandlerModel;
import com.petra.lib.remote.output.http.SignalManager;
import com.petra.lib.remote.output.http.request.OutputRequestFactory;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.variable.group.VariableGroup;
import com.petra.lib.state.variable.group.VariableGroupImpl;
import com.petra.lib.state.variable.loader.VariableLoader;
import com.petra.lib.state.variable.loader.handler.HandlerLoader;
import com.petra.lib.state.variable.loader.handler.UserVariableHandler;
import com.petra.lib.state.variable.loader.source.SourceLoader;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.pure.PureVariableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ValueLoaderFactory {
    public static StateHandler createValueLoader(Collection<BlockGroup> blockSourceModels,
                                                 Map<String, UserVariableHandler> sourceHandlersNames,
                                                 String currentServiceName,
                                                 Block blockManager,
                                                 PureVariableList pureVariableList,
                                                 VariableMapper inputMapper) {

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

    private static List<VariableLoader> createSourceLoadersForGroup(BlockGroup blockGroup,
                                                                    String currentServiceName,
                                                                    VariableMapper inputMapper) {
        List<VariableLoader> loadersForGroup = new ArrayList<>();
        for (BlockRemoteSourceModel blockRemoteSourceModel : blockGroup.getBlockRemoteSourceModels()) {
            SignalManager signalManager =
                    OutputRequestFactory.getSourceStrategy(blockRemoteSourceModel.getSourceServiceName(),
                            blockRemoteSourceModel.getLocalVariableIds(), blockRemoteSourceModel.getSourceId(),
                            currentServiceName);
            SourceLoader sourceLoader = new SourceLoader(blockRemoteSourceModel.getLocalVariableIds(),
                    signalManager, inputMapper);

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
