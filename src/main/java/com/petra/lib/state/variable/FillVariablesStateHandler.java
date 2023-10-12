package com.petra.lib.state.variable;

import com.petra.lib.context.ExecutionContext;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.variable.group.VariableGroupList;
import com.petra.lib.state.variable.group.handler.VariableHandler;
import com.petra.lib.state.variable.group.repo.ContextRepo;
import com.petra.lib.state.variable.model.VariableGroupModel;
import com.petra.lib.state.StateManager;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * This job fill variables from sources and request and defaults
 */
@Log4j2
public class FillVariablesStateHandler implements StateHandler {

    /**
     * Map contains variablesGroups by requesting source ids
     */
    private final Map<SignalId, VariableGroupList> variableGroupControllerMap;
    private final StateManager stateManager;


    FillVariablesStateHandler(BlockModel blockModel, Collection<VariableHandler> handlers,
                              StateManager stateManager, SignalRequestManager signalRequestManager) {
        this.stateManager = stateManager;

        variableGroupControllerMap = new HashMap<>();

        //variable groups by init signal
        Map<SignalId, List<VariableGroupModel>> requestsGroupMap = new HashMap<>();
        for (VariableGroupModel variableGroupModel : blockModel.getVariableGroups()) {
            if (!requestsGroupMap.containsKey(variableGroupModel.getInitSignal())) {
                requestsGroupMap.put(variableGroupModel.getInitSignal(), new ArrayList<>());
            }
            requestsGroupMap.get(variableGroupModel.getInitSignal()).add(variableGroupModel);
        }

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
        });

    }

    @Override
    public void execute(ExecutionContext actionContext) throws Exception {
        variableGroupControllerMap.get(actionContext.getRequestSignalId()).fillVariables(actionContext);
    }

    @Override
    public State getManagerState() {
        return State.FILL_CONTEXT_VARIABLES;
    }

    @Override
    public void start() {

    }

    /**
     * Executes when variables filling is ready
     *
     * @param actionContext filled context
     */
    private void fillingIsDone(ExecutionContext actionContext) {
        stateManager.executeState(actionContext, State.EXECUTING);
    }

    /**
     * Executes when error has ocurred in variable filling
     *
     * @param actionContext - context
     */
    private void fillingError(ExecutionContext actionContext) {
        stateManager.executeState(actionContext, State.ERROR);
    }
}
