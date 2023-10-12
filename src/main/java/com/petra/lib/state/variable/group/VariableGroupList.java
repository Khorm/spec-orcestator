package com.petra.lib.state.variable.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.context.ExecutionContext;
import com.petra.lib.state.variable.group.handler.VariableHandler;
import com.petra.lib.state.variable.group.repo.ContextRepo;
import com.petra.lib.state.variable.model.VariableGroupModel;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Controller for list of groups for one request signal.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class VariableGroupList {

    /**
     * Threadsafe repository
     */
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
    Consumer<ExecutionContext> afterFillingHandler;
    Consumer<ExecutionContext> afterErrorHandler;


    public VariableGroupList(ContextRepo contextRepo, BlockModel blockModel, List<VariableGroupModel> variableGroupModels,
                             Collection<VariableHandler> handlers, SignalRequestManager signalRequestManager,
                             Consumer<ExecutionContext> afterFillingHandler, Consumer<ExecutionContext> afterErrorHandler) {
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
     * @param actionContext
     * @throws JsonProcessingException
     */
    public void fillVariables(ExecutionContext actionContext) throws JsonProcessingException {
        //reqister new context for async value loads
        contextRepo.addNewRequestData(actionContext);

        groupsWithNoParents.forEach(variableGroup -> variableGroup.fillGroup(actionContext));
    }

    /**
     * Group is filled
     *
     * @param executedGroupId
     * @param context
     */
    private void executeNext(Long executedGroupId, ExecutionContext context) {
        try {
            Set<Long> executedGroups = contextRepo.setFilledGroup(executedGroupId, context.getScenarioId());

            //if all groups filled
            if (executedGroups.size() == groupList.size()) {
                contextRepo.clear(context.getScenarioId());
                afterFillingHandler.accept(context);
                return;
            }

            //find next groups to fill
            for (VariableGroup variableGroup : groupList) {
                if (isGroupAcceptedToExecute(executedGroups, variableGroup)) {
                    variableGroup.fillGroup(context);
                }
            }
        } catch (Exception e) {
            log.error(e);
            contextRepo.clear(context.getScenarioId());
            afterErrorHandler.accept(context);
        }
    }


    private boolean isGroupAcceptedToExecute(Set<Long> executedGroups, VariableGroup nextGroup) {
        return !executedGroups.contains(nextGroup.getGroupId())
                && executedGroups.containsAll(nextGroup.getParentGroups());
    }

    /**
     * Error when filling variables in group
     *
     * @param context
     */
    private void fillingError(ExecutionContext context) {
        contextRepo.clear(context.getScenarioId());
        afterErrorHandler.accept(context);
    }
}
