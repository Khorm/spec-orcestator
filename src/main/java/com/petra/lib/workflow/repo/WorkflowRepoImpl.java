package com.petra.lib.workflow.repo;

import com.petra.lib.action.BlockState;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.repo.mapper.WorkflowContextRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class WorkflowRepoImpl implements WorkflowRepo {

    private final TransactionManager transactionManager;

    @Override
    public Optional<WorkflowContext> findContext(UUID scenarioId, Long workflowId) {
        WorkflowContext context = transactionManager.executeInTransaction(task -> {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("scenarioId", scenarioId)
                    .addValue("workflowId", workflowId);

            return namedParameterJdbcTemplate.queryForObject("SELECT * FROM workflow_context " +
                            " WHERE scenario_id = :scenarioId AND workflow_id = :workflowId",
                    namedParameters, new WorkflowContextRowMapper());
        });

        return Optional.ofNullable(context);
    }

    @Override
    public boolean createContext(WorkflowContext workflowContext) {
        String sql = "INSERT INTO workflow_context VALUES (:scenarioId, :producerId, :consumerId, :signalId," +
                " :signalVariables, :workflowState, :responseVariables, :requestServiceName)";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
                = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

        SqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("scenarioId", workflowContext.getScenarioId())
                .addValue("producerId", workflowContext.getProducerId())
                .addValue("consumerId", workflowContext.getConsumerId())
                .addValue("signalId", workflowContext.getSignalId())
                .addValue("signalVariables", ValuesContainerFactory
                        .toJson(workflowContext.getSignalVariables()))
                .addValue("workflowState", workflowContext.getWorkflowState())
                .addValue("responseVariables", ValuesContainerFactory
                        .toJson(workflowContext.getResponseVariables()))
                .addValue("requestServiceName", workflowContext.getRequestServiceName());

        return namedParameterJdbcTemplate.update(sql, updateParams) == 1;
    }

    @Override
    public boolean updateStateToExecuted(WorkflowContext workflowContext) {
        return transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_context SET " +
                    " action_state = 'EXECUTED' " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", workflowContext.getConsumerId())
                    .addValue("scenarioId", workflowContext.getScenarioId());
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
    public void updateValues(UUID scenarioId, Long workflowId, ValuesContainer valuesContainer) {
        transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_context SET response_variables = :blockVariables" +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("blockVariables", ValuesContainerFactory.toJson(valuesContainer))
                    .addValue("workflowId", workflowId)
                    .addValue("scenarioId",scenarioId);
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
    public boolean updateStateToComplete(WorkflowContext workflowContext, BlockState workflowState) {
        return transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_context SET action_state = 'COMPLETE' " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", workflowContext.getConsumerId())
                    .addValue("scenarioId", workflowContext.getScenarioId());
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
    public boolean updateStateToError(WorkflowContext workflowContext, BlockState workflowState) {
        return transactionManager.executeInTransaction(transaction -> {
            String updateSql = "UPDATE workflow_context SET action_state = 'ERROR' " +
                    " where workflow_id = :workflowId AND scenario_id = :scenarioId;";
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transaction.getDataSource()));
            SqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("workflowId", workflowContext.getConsumerId())
                    .addValue("scenarioId", workflowContext.getScenarioId());
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
    public Collection<WorkflowContext> findAllNotEndedContextsByWorkflowId(Long workflowId) {

        return transactionManager.executeInTransaction(task -> {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("workflowId", workflowId);

            return namedParameterJdbcTemplate.query("SELECT * FROM workflow_context " +
                            " WHERE workflow_id = :workflowId AND (workflow_state = 'EXECUTING' OR " +
                            " workflow_state = 'EXECUTED') ",
                    namedParameters, new WorkflowContextRowMapper());
        });
    }
}
