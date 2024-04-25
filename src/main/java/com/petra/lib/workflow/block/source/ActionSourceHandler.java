package com.petra.lib.workflow.block.source;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.workflow.context.WorkflowActionContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ActionSourceHandler implements SourceHandler {

    /**
     * должна быть сгруппирована по номерам груп от 0
     */
    Map<Integer, LoadGroup> loadGroups;

    ActionSourceHandler(Collection<LoadGroup> loadGroups) {
        this.loadGroups = loadGroups.stream()
                .collect(Collectors.toMap(LoadGroup::getGroupNumber, Function.identity()));
    }

    @Override
    public boolean startValuesFilling(WorkflowActionContext workflowActionContext) {
        return loadGroups.get(1).load(workflowActionContext);
    }

    @Override
    public boolean setSourceAnswer(WorkflowActionContext workflowActionContext, SourceResponseDto sourceResponseDto) {
        int currentGroup = workflowActionContext.getLoadingGroup();
        boolean isLoadingReady = loadGroups.get(currentGroup).answered(workflowActionContext, sourceResponseDto);
        while (!isLoadingReady) {
            LoadGroup newLoadGroup = loadGroups.get(++currentGroup);
            if (newLoadGroup == null) break;
            isLoadingReady = newLoadGroup.load(workflowActionContext);
            currentGroup = newLoadGroup.getGroupNumber();
        }
        return isLoadingReady;
    }


}
