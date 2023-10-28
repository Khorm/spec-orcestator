package com.petra.lib.context.repo;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.state.ActionState;
import com.petra.lib.transaction.TransactionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ActionRepo {

    private final TransactionManager transactionManager;

    public Optional<ActivityContext> getActionContext(UUID scenarioId, VersionBlockId blockVersionId) {

        ActivityContext context = transactionManager.executeInTransaction(task -> {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("scenarioId", scenarioId)
                    .addValue("blockId", blockVersionId.getBlockId())
                    .addValue("blockVersion", blockVersionId.getVersion());
            ActivityContext activityContext = namedParameterJdbcTemplate.queryForObject("SELECT * FROM action_history" +
                    " WHERE scenario_id = :scenarioId AND current_block_version_id = :blockVersionId",
                    namedParameters, ActivityContext.class);

            return activityContext;
        }, TransactionDefinition.ISOLATION_READ_UNCOMMITTED);

        return Optional.ofNullable(context);
    }


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

    /**
     * создать новый конекст активности
     *
     * @param signal
     * @return
     */
    public ActivityContext createNewActionContext(Signal signal) {
        ActivityContext activityContext =

    }
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

    public boolean updateActionType(UUID scenario, VersionBlockId blockId, ActionState actionState) {

    }

}
