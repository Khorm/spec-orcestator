package com.petra.lib.block;

import com.petra.lib.block.gearbox.Gearbox;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.environment.repo.ActionRepo;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.queue.StateQueue;
import com.petra.lib.variable.base.PureVariableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
class BlockImpl implements Block {

//    StateQueue stateQueue;

    @Getter
    BlockId id;
    String blockName;
    boolean isSequentially;
    Gearbox gearbox;
    ActionRepo actionRepo;

    EnumMap<State, StateHandler> stateHandlerMap;

    /**
     * Станрадтный список переменных для активности, которые не заполнены.
     * У воркфлоу отсуствует
     */
//    PureVariableList pureVariableList;

    private BlockImpl(BlockModel blockModel, boolean isSequentially) {
//        this.stateQueue = stateQueue;
        this.id = new BlockId(blockModel.getId(), blockModel.getVersion());
        this.blockName = blockModel.getName();
        this.pureVariableList = new PureVariableList(blockModel.getStatelessVariables());
        this.isSequentially = isSequentially;
    }

//    @Override
//    public void executedState(DirtyContext actionContext, State executedState, StateError error) {
//        if (error == null) {
//            executedState(actionContext, executedState);
//        } else {
//            StateHandler stateHandler = stateQueue.getErrorHandler(error);
//            try {
//                stateHandler.execute(actionContext);
//            } catch (Exception ex) {
//                throw new Error("TASK CAN NOT BE COMPUTED");
//            }
//        }
//    }
//
//    @Override
//    public void executedState(DirtyContext actionContext, State executedState) {
//        try {
//            Optional<StateHandler> nextStateHandler = stateQueue.getNextStateHandler(executedState);
//            if (nextStateHandler.isPresent()) {
//                nextStateHandler.get().execute(actionContext);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                stateQueue.getErrorStateHandler().execute(actionContext);
//            } catch (Exception ex) {
//                throw new Error("TASK CAN NOT BE COMPUTED");
//            }
//        }
//    }
//
//    @Override
//    public void start() {
//        stateQueue.start();
//    }
//
//    @Override
//    public void executeSignal(RequestDto requestDto) throws Exception {
//        DirtyContext actionContext = ContextFabric.createContext(requestDto, pureVariableList, id);
//        log.info("Start execution {} with params {}", blockName, requestDto.toString());
//        stateQueue.getInputStateHandler().execute(actionContext);
//    }

    @Override
    public String getName() {
        return blockName;
    }

    @Override
    public void execute(ScenarioContext scenarioContext) {
        State state = gearbox.getNextHandler(scenarioContext);
        log.debug("Start to execute new state {} in {}", state, blockName);
        StateHandler handler = stateHandlerMap.get(state);
        actionRepo.updateActionState(scenarioContext.getBusinessId(), id, state);
        try {
            handler.execute(scenarioContext);
        } catch (Exception e) {
            log.error("Error in stateHandler {} : {}", blockName, e.toString());
            e.printStackTrace();
            scenarioContext.setCurrentState(State.ERROR);
            execute(scenarioContext);
        }
    }

    @Override
    public boolean isSequentially() {
        return isSequentially;
    }
}
