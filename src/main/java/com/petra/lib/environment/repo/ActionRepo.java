package com.petra.lib.environment.repo;

import com.petra.lib.XXXXXcontext.DirtyVariablesList;
import com.petra.lib.block.BlockId;
import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.state.State;
import org.apache.kafka.common.metrics.Stat;

import java.util.Optional;
import java.util.UUID;

public class ActionRepo {

    /**
     * �������� ������ ���� ����������� ������ �������� ������ �� ���������
     * @param scenarioId - ���� ��������
     * @return
     */
    public Optional<ScenarioContext> getCurrentScenarioAction(UUID scenarioId, BlockId blockId){

    }

    /**
     * �������� �� � ����� ���������� ������ ��������� ����������� ��������
     * @param scenarioId
     * @param actionId
     * @param serviceName
     * @return
     */
    public boolean updateScenarioModel(UUID scenarioId, BlockId actionId, String serviceName){

    }

    /**
     * �������� ����� ��������
     * @param scenarioId
     * @param actionId
     * @param serviceName
     * @return
     */
    public ScenarioContext createScenarioModel(UUID scenarioId, BlockId actionId, String serviceName){

    }

    /**
     * ����� ���� ����������� �������� � ��
     * @param scenarioId
     * @param actionId
     * @param dirtyContext
     */
    public boolean flushScenarioContext(UUID scenarioId, BlockId actionId, DirtyContext dirtyContext){

    }

    /**
     * �������� ����� ����� �� ����� ���������� �����
     * @param scenarioId
     * @param blockId
     * @param state
     */
    public void updateActionState(ScenarioContext scenarioContext, State state){

    }

    public void updateVariables(ScenarioContext context,  DirtyVariablesList dirtyVariablesList){

    }

}
