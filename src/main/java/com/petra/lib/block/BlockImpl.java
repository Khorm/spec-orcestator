package com.petra.lib.block;

import com.petra.lib.block.switcher.StateSwitchSequence;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.repo.ActivityRepo;
import com.petra.lib.context.state.ActionState;
import com.petra.lib.state.StateHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@RequiredArgsConstructor
class BlockImpl implements Block {

    @Getter
    VersionId id;
    String blockName;

    StateSwitchSequence stateSwitchSequence;
    ActivityRepo activityRepo;

    /**
     * Менеджер переключения стейтов
     */
    StateSwitchSequence stateSwitchSequence;

    EnumMap<ActionState, StateHandler> stateHandlerMap;

    /**
     * Станрадтный список переменных для активности, которые не заполнены.
     * У воркфлоу отсуствует
     */
//    PureVariableList pureVariableList;

    private BlockImpl(BlockModel blockModel) {
//        this.stateQueue = stateQueue;
        this.id = new VersionId(blockModel.getId(), blockModel.getVersion());
        this.blockName = blockModel.getName();
    }

    @Override
    public String getName() {
        return blockName;
    }

    @Override
    public void execute(ActivityContext activityContext) {
        ActionState actionState = stateSwitchSequence.getNextHandler(activityContext);
        log.debug("Start to execute new state {} in {}", actionState, blockName);
        StateHandler handler = stateHandlerMap.get(actionState);
        try {
            handler.execute(activityContext);
        } catch (Exception e) {
            log.error("Error in stateHandler {} : {}", blockName, e.toString());
            e.printStackTrace();
            activityContext.setNewState(ActionState.ERROR);
            execute(activityContext);
        }
    }

}
