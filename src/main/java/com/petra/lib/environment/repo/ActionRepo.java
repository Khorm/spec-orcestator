package com.petra.lib.environment.repo;

import com.petra.lib.block.BlockId;
import com.petra.lib.state.ActionState;

import java.util.UUID;

public class ActionRepo {

//    /**
//     * Получить список всех активностей оторые работают сейчас со сценарием
//     * @param scenarioId - айди сценария
//     * @return
//     */
//    public Optional<ActivityContext> getCurrentScenarioAction(UUID scenarioId, BlockId blockId){
//
//    }
//
//    /**
//     * Обновить на у какой активности сейчас находится прохождение сценания
//     * @param scenarioId
//     * @param actionId
//     * @param serviceName
//     * @return
//     */
//    public boolean updateActivityContext(ActivityContext activityContext){
//
//    }
//
//
//    public void syncContextVariables(ActivityContext activityContext){
//
//    }
//
//    /**
//     * ССоздать новый сценария
//     * @param scenarioId
//     * @param actionId
//     * @param serviceName
//     * @return
//     */
//    public ActivityContext createScenarioModel(UUID scenarioId, BlockId actionId, String serviceName){
//
//    }
//
//    /**
//     * Слить весь обновленный контекст в БД
//     * @param scenarioId
//     * @param actionId
//     * @param dirtyContext
//     */
//    public boolean flushScenarioContext(UUID scenarioId, BlockId actionId, DirtyContext dirtyContext){
//
//    }
//
//    /**
//     * Обновить новый стейт во вребя выполнения блока
//     * @param scenarioId
//     * @param blockId
//     * @param state
//     */
//    public void updateActionState(ActivityContext activityContext, State state){
//
//    }
//
//    public void updateVariables(ActivityContext context, DirtyVariablesList dirtyVariablesList){
//
//    }

    public boolean updateActionType(UUID scenario, BlockId blockId, ActionState actionState){

    }

}
