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
     * Получить список всех активностей оторые работают сейчас со сценарием
     * @param scenarioId - айди сценария
     * @return
     */
    public Optional<ScenarioContext> getCurrentScenarioAction(UUID scenarioId, BlockId blockId){

    }

    /**
     * Обновить на у какой активности сейчас находится прохождение сценания
     * @param scenarioId
     * @param actionId
     * @param serviceName
     * @return
     */
    public boolean updateScenarioModel(UUID scenarioId, BlockId actionId, String serviceName){

    }

    /**
     * ССоздать новый сценария
     * @param scenarioId
     * @param actionId
     * @param serviceName
     * @return
     */
    public ScenarioContext createScenarioModel(UUID scenarioId, BlockId actionId, String serviceName){

    }

    /**
     * Слить весь обновленный контекст в БД
     * @param scenarioId
     * @param actionId
     * @param dirtyContext
     */
    public boolean flushScenarioContext(UUID scenarioId, BlockId actionId, DirtyContext dirtyContext){

    }

    /**
     * Обновить новый стейт во вребя выполнения блока
     * @param scenarioId
     * @param blockId
     * @param state
     */
    public void updateActionState(ScenarioContext scenarioContext, State state){

    }

    public void updateVariables(ScenarioContext context,  DirtyVariablesList dirtyVariablesList){

    }

}
