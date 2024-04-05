package com.petra.lib.workflow.variables;

import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.context.WorkflowActionContext;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ��������� ���������� ����� ����������� �����
 */
public class LoadVariablesManager {

    Map<Long, VariableMapper> variableMappersBySignalIds;

    /**
     * ������ ���� ������������� �� ������� ���� �� 0
     */
    Collection<LoadGroup> loadGroups;

    public void start(WorkflowActionContext workflowActionContext) throws ExecutionException, InterruptedException {
        //����������� ���������� ��������� ������� � ���������� ����������
        VariablesContainer blockContainer =
                variableMappersBySignalIds.get(workflowActionContext.getCurrentSignalId())
                        .map(workflowActionContext.getSignalVariables());
        workflowActionContext.setBlockVariables(blockContainer);

        //����������� ������� � �������
        for (LoadGroup loadGroup : loadGroups) {
            loadGroup.load(workflowActionContext);
        }
    }

}
