package com.petra.lib.workflow.repo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class WorkflowActionRepoImpl /*implements WorkflowActionRepo*/ {

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
