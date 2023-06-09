package com.petra.lib.worker.variable.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.models.BlockModel;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import com.petra.lib.worker.variable.group.handler.VariableHandler;
import com.petra.lib.worker.variable.group.repo.ContextRepo;
import com.petra.lib.worker.variable.model.VariableGroupModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Controller for list of groups for one request signal.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VariableGroupController {

    ContextRepo contextRepo;

    /**
     * List of groups with parents
     */
    List<VariableGroup> groupList;

    /**
     * List of start groups
     */
    List<VariableGroup> groupsWithNoParents;

    /**
     * callbacks
     */
    Consumer<JobContext> afterFillingHandler;
    BiConsumer<Exception, JobContext> afterErrorHandler;


    public VariableGroupController(ContextRepo contextRepo, BlockModel blockModel, List<VariableGroupModel> variableGroupModels,
                                   Collection<VariableHandler> handlers, SignalRequestManager signalRequestManager,
                                   Consumer<JobContext> afterFillingHandler, BiConsumer<Exception, JobContext> afterErrorHandler) {
        this.contextRepo = contextRepo;
        this.afterFillingHandler = afterFillingHandler;
        this.afterErrorHandler = afterErrorHandler;

        groupList = new ArrayList<>();
        groupsWithNoParents = new ArrayList<>();
        for (VariableGroupModel variableGroupModel : variableGroupModels) {
            VariableGroup variableGroup = GroupFabric.createGroup(signalRequestManager, variableGroupModel, handlers, contextRepo,
                    blockModel.getBlockId(), blockModel.getName());
            variableGroup.setLoadHandler(this::executeNext);
            variableGroup.setErrorHandler(this::fillingError);
            groupList.add(variableGroup);
            if (variableGroup.getParentGroups().isEmpty()) {
                groupsWithNoParents.add(variableGroup);
            }
        }
    }

    /**
     * Start fill variables
     *
     * @param jobContext
     * @throws JsonProcessingException
     */
    public void fillVariables(JobContext jobContext) throws JsonProcessingException {
        contextRepo.addNewRequestData(jobContext);
        groupsWithNoParents.forEach(variableGroup -> variableGroup.fillGroup(jobContext));
    }

    /**
     * Group is filled
     *
     * @param executedGroupId
     * @param context
     */
    private void executeNext(Long executedGroupId, JobContext context) {
        try {
            Set<Long> executedGroups = contextRepo.setExecution(context, executedGroupId);
            if (executedGroups.size() == groupList.size()) {
                contextRepo.clear(context.getScenarioId(), context.getBlockId());
                afterFillingHandler.accept(context);
                return;
            }
            for (VariableGroup variableGroup : groupList) {
                if (isGroupAcceptedToExecute(executedGroups, executedGroupId, variableGroup)) {
                    variableGroup.fillGroup(context);
                }
            }
        } catch (Exception e) {
            contextRepo.clear(context.getScenarioId(), context.getBlockId());
            afterErrorHandler.accept(e, context);
        }
    }


    private boolean isGroupAcceptedToExecute(Set<Long> executedGroups, Long executedGroup, VariableGroup newGroup) {
        return newGroup.getParentGroups().contains(executedGroup)
                && !executedGroups.contains(newGroup.getGroupId())
                && executedGroups.containsAll(newGroup.getParentGroups());
    }

    /**
     * Error when filling variables in group
     *
     * @param e
     * @param context
     */
    private void fillingError(Exception e, JobContext context) {
        contextRepo.clear(context.getScenarioId(), context.getBlockId());
        afterErrorHandler.accept(e, context);
    }
}
