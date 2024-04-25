package com.petra.lib.workflow.repo;

import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.repo.mapper.WorkflowActionContextRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class WorkflowActionRepoImpl implements WorkflowActionRepo {

    private final TransactionManager transactionManager;

    @Override
    public Optional<WorkflowActionContext> findContext(UUID scenarioId, Long workflowId, Long actionId) {
        WorkflowActionContext context = transactionManager.executeInTransaction(task -> {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("scenarioId", scenarioId)
                    .addValue("workflowId", workflowId)
                    .addValue("execBlock", actionId);

            return namedParameterJdbcTemplate.queryForObject("SELECT * FROM workflow_action_context " +
                            " WHERE scenario_id = :scenarioId AND workflow_id = :workflowId AND exec_block = :execBlock;",
                    namedParameters, new WorkflowActionContextRowMapper());
        });

        return Optional.ofNullable(context);
    }

    @Override
    public boolean createContext(WorkflowActionContext workflowActionContext) {
        String sql = "INSERT INTO workflow_action_context VALUES (:scenarioId, :workflowId, :execBlockId, :state," +
                " null, null, :callingSignalVariables, :callingSignalId, null, {})";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
                = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

        SqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("scenarioId", workflowActionContext.getScenarioId())
                .addValue("workflowId", workflowActionContext.getWorkflowId())
                .addValue("execBlockId", workflowActionContext.getExecBlock())
                .addValue("state", WorkflowActionState.LOAD_VARIABLES.name())
                .addValue("callingSignalVariables", ValuesContainerFactory.toJson(workflowActionContext.getCallingSignalVariables()))
                .addValue("requestServiceName", workflowActionContext.getCallingSignalId());

        return namedParameterJdbcTemplate.update(sql, updateParams) == 1;
    }


    @Override
    public boolean updateStateToComplete(ValuesContainer blockVariables, Long nextSignalId,
                                         WorkflowActionContext workflowActionContext) {
        return transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_action_context SET block_variables = :blockVariables," +
                    " action_state = 'COMPLETE', next_signal_id = :nextSignalId " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId AND exec_block = :execBlockId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("blockVariables", blockVariables)
                    .addValue("nextSignalId", nextSignalId)
                    .addValue("workflowId", workflowActionContext.getWorkflowId())
                    .addValue("scenarioId", workflowActionContext.getScenarioId())
                    .addValue("execBlockId", workflowActionContext.getExecBlock());
            boolean result;
            try {
                result = namedParameterJdbcTemplate.update(updateSql, updateParams) == 1;
            } catch (Exception e) {
                transaction.rollback();
                if (e.getMessage().contains("not accepted state")) {
                    return false;
                }
                throw e;
            }
            return result;
        });
    }

    @Override
    public boolean updateStateToExecuting(WorkflowActionContext workflowActionContext) {
        return transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_action_context SET " +
                    " action_state = 'EXECUTING' " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId AND exec_block = :execBlockId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", workflowActionContext.getWorkflowId())
                    .addValue("scenarioId", workflowActionContext.getScenarioId())
                    .addValue("execBlockId", workflowActionContext.getExecBlock());
            boolean result;
            try {
                result = namedParameterJdbcTemplate.update(updateSql, updateParams) == 1;
            } catch (Exception e) {
                transaction.rollback();
                if (e.getMessage().contains("not accepted state")) {
                    return false;
                }
                throw e;
            }
            return result;
        });
    }

    @Override
    public boolean updateStateToError(WorkflowActionContext workflowActionContext) {
        return transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_action_context SET " +
                    " action_state = 'ERROR' " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId AND exec_block = :execBlockId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", workflowActionContext.getWorkflowId())
                    .addValue("scenarioId", workflowActionContext.getScenarioId())
                    .addValue("execBlockId", workflowActionContext.getExecBlock());
            boolean result;
            try {
                result = namedParameterJdbcTemplate.update(updateSql, updateParams) == 1;
            } catch (Exception e) {
                transaction.rollback();
                if (e.getMessage().contains("not accepted state")) {
                    return false;
                }
                throw e;
            }
            return result;
        });
    }

    @Override
    public Collection<WorkflowActionContext> getScenarioContexts(UUID scenarioId, Long workflowID) {
        return transactionManager.executeInTransaction(jpaTransactionManager -> {
             NamedParameterJdbcTemplate namedParameterJdbcTemplate
                     = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

             SqlParameterSource namedParameters = new MapSqlParameterSource()
                     .addValue("workflowId", workflowID)
                     .addValue("scenarioId", scenarioId);
             return namedParameterJdbcTemplate.query(" SELECT * FROM workflow_action_context " +
                             " WHERE scenario_id = :scenarioId AND workflow_id = :workflowId",
                     namedParameters, new WorkflowActionContextRowMapper());

         });
    }

    @Override
    public void updateLoadingGroup(WorkflowActionContext context, int newGroup) {
        transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE loading_group SET " +
                    " action_state = :newGroup " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId AND exec_block = :execBlockId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", context.getWorkflowId())
                    .addValue("scenarioId", context.getScenarioId())
                    .addValue("execBlockId", context.getExecBlock())
                    .addValue("newGroup", newGroup);
            namedParameterJdbcTemplate.update(updateSql, updateParams);
        });
    }

    @Override
    public void updateLoadSource(WorkflowActionContext context, ValuesContainer newValuesList, Long sourceId) {
        transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE loading_group SET " +
                    " loaded_sources = array_append(round_scores, :loadSource) " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId AND exec_block = :execBlockId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", context.getWorkflowId())
                    .addValue("scenarioId", context.getScenarioId())
                    .addValue("execBlockId", context.getExecBlock())
                    .addValue("loadSource", sourceId);
            namedParameterJdbcTemplate.update(updateSql, updateParams);
        });
    }


//    private final TransactionManager transactionManager;
//
//    @Override
//    public Optional<WorkflowActionContext> findContext(UUID scenarioId, BlockId workflowId, BlockId actionId) {
//         WorkflowActionContext workflowActionContext = transactionManager.executeInTransaction(jpaTransactionManager -> {
//             NamedParameterJdbcTemplate namedParameterJdbcTemplate
//                     = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
//
//             SqlParameterSource namedParameters = new MapSqlParameterSource()
//                     .addValue("scenario_id", scenarioId)
//                     .addValue("workflow_id", workflowId)
//                     .addValue("current_action", actionId);
//             return namedParameterJdbcTemplate.queryForObject(" SELECT * FROM workflow_history " +
//                             " WHERE scenario_id = :scenarioId AND workflow_id = :workflow_id " +
//                             " AND current_action = :current_action ",
//                     namedParameters, WorkflowActionContext.class);
//
//         });
//         return Optional.ofNullable(workflowActionContext);
//    }
//
//    @Override
//    public void updateWorkflowContextState(UUID scenarioId, BlockId workflowId, BlockId actionId, WorkflowActionState workflowActionState) {
//        transactionManager.executeInTransaction(jpaTransactionManager -> {
//            NamedParameterJdbcTemplate namedParameterJdbcTemplate
//                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
//            SqlParameterSource namedParameters = new MapSqlParameterSource()
//                    .addValue("scenario_id", scenarioId)
//                    .addValue("workflow_id", workflowId)
//                    .addValue("current_action", actionId)
//                    .addValue("workflow_state", workflowActionState.name());
//            namedParameterJdbcTemplate.update("UPDATE workflow_history SET workflow_state = :workflow_state WHERE " +
//                    " WHERE scenario_id = :scenarioId AND workflow_id = :workflow_id " +
//                    " AND current_action = :current_action ", namedParameters);
//        });
//    }
//
//    @Override
//    public boolean saveContext(WorkflowActionContext workflowActionContext) {
//
//    }
}
