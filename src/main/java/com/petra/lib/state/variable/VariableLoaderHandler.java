package com.petra.lib.state.variable;

import com.petra.lib.block.Block;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.variable.group.VariableGroup;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Стейт выгрузки переменных
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VariableLoaderHandler implements StateHandler {

    Collection<VariableGroup> variableGroups;
    Block blockManager;

    /**
     * Перебирает по очереди группы и вызывает их исполнение.
     * Группа может как запрашивать сигнал, так и получить на него ответ.
     * @param context - current execution context
     * @throws Exception
     */
    @Override
    public void execute(ActivityContext context) throws Exception {
        context.setNewState(ActionState.FILL_CONTEXT_VARIABLES);
        for (VariableGroup group : variableGroups) {
            if (!group.isReady(context)) {
                group.execute(context);
                return;
            }
        }
        blockManager.execute(context);
    }

    @Override
    public void start() {

    }

    @Override
    public ActionState getState() {
        return ActionState.FILL_CONTEXT_VARIABLES;
    }

}
