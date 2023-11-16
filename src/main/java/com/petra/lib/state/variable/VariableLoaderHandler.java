package com.petra.lib.state.variable;

import com.petra.lib.block.Block;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.variable.group.VariableGroup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Стейт выгрузки переменных
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

class VariableLoaderHandler implements StateHandler {

    /**
     * отсортированные по возрастанию группы
     */
    Collection<VariableGroup> variableGroups;
    Block blockManager;


    public VariableLoaderHandler(Block blockManager, Collection<VariableGroup> variableGroups){
        this.blockManager = blockManager;
        this.variableGroups = variableGroups;

        Comparator<VariableGroup> comparator = Comparator.comparingInt(VariableGroup::groupNumber);
        Collections.sort(this.variableGroups, comparator);
    }

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
