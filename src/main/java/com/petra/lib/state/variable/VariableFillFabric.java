package com.petra.lib.state.variable;

import com.petra.lib.state.StateHandler;
import com.petra.lib.state.variable.group.VariableGroupList;
import com.petra.lib.state.variable.group.handler.VariableHandler;
import com.petra.lib.state.variable.group.repo.ContextRepo;
import com.petra.lib.state.variable.model.VariableGroupModel;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.request.controller.SignalRequestManager;

import java.util.*;

public class VariableFillFabric {

    public static StateHandler createFillVariablesExecutor(BlockModel blockModel, Collection<VariableHandler> handlers,
                                                           StateManager stateManager, SignalRequestManager signalRequestManager){
//        this.jobManager = jobManager;

        Map<SignalId, VariableGroupList> variableGroupControllerMap = new HashMap<>();

        //variable groups by init signal
        Map<SignalId, List<VariableGroupModel>> requestsGroupMap = new HashMap<>();
        for (VariableGroupModel variableGroupModel : blockModel.getVariableGroups()) {
            if (!requestsGroupMap.containsKey(variableGroupModel.getInitSignal())) {
                requestsGroupMap.put(variableGroupModel.getInitSignal(), new ArrayList<>());
            }
            requestsGroupMap.get(variableGroupModel.getInitSignal()).add(variableGroupModel);
        }

        FillVariablesStateHandler fillingIsDone = new FillVariablesStateHandler();
        requestsGroupMap.forEach((key, value) -> {
            VariableGroupList variableGroupList = new VariableGroupList(
                    ContextRepo.getLocalContextRepo(),
                    blockModel,
                    value,
                    handlers,
                    signalRequestManager,
                    this::fillingIsDone,
                    this::fillingError);
            variableGroupControllerMap.put(key, variableGroupList);

            return new FillVariablesStateHandler(stateManager, )
    }
}
