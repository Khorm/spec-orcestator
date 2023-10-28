package com.petra.lib.state.variable.neww;

import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.block.Block;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.output.enums.RequestType;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.variable.neww.group.GroupModel;
import com.petra.lib.state.variable.neww.group.VariableGroup;
import com.petra.lib.state.variable.neww.group.VariableGroupImpl;
import com.petra.lib.state.variable.neww.loaders.LoadersPureFabric;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VariableLoaderHandler implements StateHandler {

    Collection<VariableGroup> ;
    Block blockManager;
    VariableMapper blockMapper;

    Map<SignalId, Collection<VariableGroup>> variableGroupsBySignal;

    public VariableLoaderHandler(Collection<GroupModel> variableGroups, Block blockManager) {
        variableGroupsBySignal = new HashMap<>();
        for (GroupModel groupModel : variableGroups){
            VariableGroup variableGroup = new VariableGroupImpl(LoadersPureFabric.
                    ,groupModel.getGroupVariableIds());
        }

        this.variableGroups = variableGroups.stream().sorted(Comparator.comparingInt(VariableGroup::getFieldNumber))
                .collect(Collectors.toList());
        this.blockManager = blockManager;
    }

    @Override
    public void execute(ActivityContext context) throws Exception {

        if (context.getCurrentSignal().getRequestType() == RequestType.SOURCE_RESPONSE) {
            //заполнить переменные из пришедшего в ответ сигнала.
            context.getVariablesContext().syncVariables(context.getCurrentSignal().getVariablesContext());
        }

        Collection<VariableGroup> groupsForInputSignal = variableGroupsBySignal.get(context.getCurrentSignalId());
        for (VariableGroup group : groupsForInputSignal){
            if(!group.isReady(context)){
                group.fill(context);
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
