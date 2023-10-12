package com.petra.lib.block;

import com.petra.lib.context.ContextFabric;
import com.petra.lib.context.ExecutionContext;
import com.petra.lib.context.StateError;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.StateManager;
import com.petra.lib.state.queue.StateQueue;
import com.petra.lib.variable.base.StatelessVariableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Optional;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
class BlockImpl implements StateManager, Block {

    StateQueue stateQueue;

    @Getter
    BlockId id;
    String blockName;

    @Getter
    Collection<SignalId> listeningSignalIds;

    StatelessVariableList statelessVariableList;


//    BlockImpl(StateQueue stateQueue, BlockModel blockModel) {
//        this.stateQueue = stateQueue;
//        this.id = new BlockId(blockModel.getId(), blockModel.getVersion());
//        this.blockName = blockModel.getName();
//        this.listeningSignalIds = blockModel.getListeningSignals().stream()
//                .map(id -> new SignalId(id.getId(), id.getMajorVersion())).collect(Collectors.toList());
//        this.statelessVariableList = new StatelessVariableList(blockModel.getStatelessVariables());
//    }

    @Override
    public void executedState(ExecutionContext actionContext, State executedState, StateError error) {
        if (error == null) {
            executedState(actionContext, executedState);
        } else {
            StateHandler stateHandler = stateQueue.getErrorHandler(error);
            try {
                stateHandler.execute(actionContext);
            } catch (Exception ex) {
                throw new Error("TASK CAN NOT BE COMPUTED");
            }
        }
    }

    @Override
    public void executedState(ExecutionContext actionContext, State executedState) {
        try {
            Optional<StateHandler> nextStateHandler = stateQueue.getNextStateHandler(executedState);
            if (nextStateHandler.isPresent()) {
                nextStateHandler.get().execute(actionContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                stateQueue.getErrorStateHandler().execute(actionContext);
            } catch (Exception ex) {
                throw new Error("TASK CAN NOT BE COMPUTED");
            }
        }
    }

    @Override
    public void start() {
        stateQueue.start();
    }

    @Override
    public void executeSignal(RequestDto requestDto) throws Exception {
        ExecutionContext actionContext = ContextFabric.createContext(requestDto, statelessVariableList, id);
        log.info("Start execution {} with params {}", blockName, requestDto.toString());
        stateQueue.getInputStateHandler().execute(actionContext);
    }

    private BlockImpl(StateQueue stateQueue, BlockId id, String blockName,
                      Collection<SignalId> listeningSignalIds, StatelessVariableList statelessVariableList) {
        this.stateQueue = stateQueue;
        this.id = id;
        this.blockName = blockName;
        this.listeningSignalIds = listeningSignalIds;
        this.statelessVariableList = statelessVariableList;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Builder {
        StateQueue stateQueue;
        BlockId id;
        String blockName;
        Collection<SignalId> listeningSignalIds;
        StatelessVariableList statelessVariableList;

        public Block build(BlockModel blockModel){
        this.id = new BlockId(blockModel.getId(), blockModel.getVersion());
        this.blockName = blockModel.getName();
        this.listeningSignalIds = blockModel.getListeningSignals().stream()
                .map(id -> new SignalId(id.getId(), id.getMajorVersion())).collect(Collectors.toList());
        this.statelessVariableList = new StatelessVariableList(blockModel.getStatelessVariables());
        }


    }
}
