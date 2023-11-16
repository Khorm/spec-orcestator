package com.petra.lib.block;

import com.petra.lib.block.model.BlockModel;
import com.petra.lib.block.model.BlockType;
import com.petra.lib.block.switcher.StateSwitchSequencePureFactory;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.user.UserHandlerFactory;
import com.petra.lib.state.user.handler.UserActionHandler;
import com.petra.lib.state.user.handler.UserSourceHandler;
import com.petra.lib.state.variable.loader.handler.UserVariableHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockFactory {

    public synchronized static Block buildBlock(BlockModel blockModel,
                                                TransactionManager transactionManager,
                                                Map<String, UserVariableHandler> sourceHandlersNames,
                                                String currentServiceName,
                                                UserActionHandler userActionHandler,
                                                UserSourceHandler userSourceHandler,
                                                ) {
        EnumMap<ActionState, StateHandler> stateHandlerMap = new EnumMap<>(ActionState.class);
        Collection<PureVariable> pureVariablesColl = blockModel.getVariables().stream()
                .map(blockVariable -> new PureVariable(blockVariable.getId(), blockVariable.getName()))
                .collect(Collectors.toList());

        BlockVersionId blockVersionId = new BlockVersionId(blockModel.getId(), blockModel.getVersion());
        PureVariableList pureVariableList = new PureVariableList(pureVariablesColl);
        Block resultBlock = new BlockImpl(
                blockVersionId,
                blockModel.getName(),
                StateSwitchSequencePureFactory.getActionSwitcher(),
                stateHandlerMap,
                pureVariableList
        );

        //создание маппера для блока
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(blockModel.getVariables());


        //создание инициалайзера
        StateHandler initialization = StateFactory.createInitState(variableMapper, resultBlock, transactionManager);
        stateHandlerMap.put(initialization.getState(),initialization);

        //создание загрузчика переменных
        if (blockModel.getBlockType() == BlockType.ACTIVITY || blockModel.getBlockType() == BlockType.SOURCE){
            StateHandler valueLoader = StateFactory.createValueLoader(blockModel.getBlockGroups(), sourceHandlersNames,
                    currentServiceName,resultBlock, pureVariableList);
            stateHandlerMap.put(valueLoader.getState(),valueLoader);
        }

        //создание польовательского обработчика
        if (blockModel.getBlockType() == BlockType.ACTIVITY){
            StateHandler userHandler = UserHandlerFactory.createActionUserHandler(transactionManager,
                    userActionHandler);
            stateHandlerMap.put(userHandler.getState(),userHandler);
        }else if (blockModel.getBlockType() == BlockType.SOURCE){
            StateHandler userHandler = UserHandlerFactory.createSourceStateHandler(transactionManager,
                    userSourceHandler);
            stateHandlerMap.put(userHandler.getState(),userHandler);
        }


    }
}
