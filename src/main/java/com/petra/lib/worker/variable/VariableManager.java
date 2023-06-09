package com.petra.lib.worker.variable;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.models.BlockModel;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.worker.variable.group.VariableGroupController;
import com.petra.lib.worker.variable.group.handler.VariableHandler;
import com.petra.lib.worker.variable.group.repo.ContextRepo;
import com.petra.lib.worker.variable.model.VariableGroupModel;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class VariableManager implements JobStateManager {

    /**
     * Map contains variablesGroups by requesting source ids
     */
    private final Map<SignalId, VariableGroupController> variableGroupControllerMap;
    private final JobStaticManager jobStaticManager;


    public VariableManager(BlockModel blockModel, Collection<VariableHandler> handlers,
            JobStaticManager jobStaticManager, SignalRequestManager signalRequestManager) {
        this.jobStaticManager = jobStaticManager;

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
            VariableGroupController variableGroupController = new VariableGroupController(
                    ContextRepo.getLocalContextRepo(),
                    blockModel,
                    value,
                    handlers,
                    signalRequestManager,
                    this::fillingIsDone,
                    this::fillingError);
            variableGroupControllerMap.put(key, variableGroupController);
        });

    }

    @Override
    public void execute(JobContext jobContext) throws Exception {
        variableGroupControllerMap.get(jobContext.getRequestSignalId()).fillVariables(jobContext);
    }

    @Override
    public JobState getManagerState() {
        return JobState.FILL_CONTEXT_VARIABLES;
    }

    @Override
    public void start() {

    }

    private void fillingIsDone(JobContext jobContext){
        jobStaticManager.executeState(jobContext, JobState.EXECUTING);
    }

    private void fillingError(Exception e, JobContext jobContext){
        log.error(e);
        jobStaticManager.executeState(jobContext, JobState.ERROR);
    }
}
