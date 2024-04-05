package com.petra.lib.action.new_action.repo;

import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.block.BlockId;
import com.petra.lib.action.new_action.ActionState;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class ActivityRepoImpl implements ActionRepo {

    private final TransactionManager transactionManager;


    @Override
    public Optional<ActionContext> findActionContext(UUID scenarioId, BlockId blockId) {
        ActionContext context = transactionManager.executeInTransaction(task -> {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
                    = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("scenarioId", scenarioId)
                    .addValue("blockId", blockId.getBlockId());
            ActionContext actionContext = namedParameterJdbcTemplate.queryForObject("SELECT * FROM action_history" +
                            " WHERE scenario_id = :scenarioId AND current_block_id = :blockId",
                    namedParameters, ActionContext.class);

            return actionContext;
        });

        return Optional.ofNullable(context);
    }

    @Override
    public boolean updateActionContextStatus(UUID scenarioId, BlockId blockId, ActionState newActionState) {
        String blockingSQL = "SELECT * from action_contexts where action_id = :actionId" +
                " AND scenario_id = :scenarioId FOR UPDATE";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
                = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
        SqlParameterSource blockNamedParams = new MapSqlParameterSource()
                .addValue("actionId", blockId.getBlockId())
                .addValue("scenarioId", scenarioId);
        namedParameterJdbcTemplate.queryForObject(blockingSQL, blockNamedParams, Void.class);


        String selectCurrentActionStateSQL = "select action_state from action_contexts where action_id = :actionId " +
                " AND scenario_id = :scenarioId";
        ActionState currentActionState
                = namedParameterJdbcTemplate.queryForObject(selectCurrentActionStateSQL, blockNamedParams, ActionState.class);

        boolean isStateAcceptToUpdate = false;
        if (newActionState == ActionState.INITIALIZING
                && currentActionState != ActionState.INITIALIZING
                && currentActionState != ActionState.EXECUTING
                && currentActionState != ActionState.ERROR
                && currentActionState != ActionState.COMPLETE) {
            isStateAcceptToUpdate = true;
        } else if (newActionState == ActionState.EXECUTING
                && currentActionState != ActionState.EXECUTING
                && currentActionState != ActionState.COMPLETE
                && currentActionState != ActionState.ERROR) {
            isStateAcceptToUpdate = true;
        } else if (newActionState == ActionState.COMPLETE
                && currentActionState != ActionState.COMPLETE
                && currentActionState != ActionState.ERROR){
            isStateAcceptToUpdate = true;
        }else if (newActionState == ActionState.ERROR
                && currentActionState != ActionState.COMPLETE
                && currentActionState != ActionState.ERROR) {
            isStateAcceptToUpdate = true;
        }

        if (!isStateAcceptToUpdate) return false;

        String updateSQL = "UPDATE action_contexts SET action_state = :settingStatus " +
                "where action_id = :actionId AND scenario_id = :scenarioId";
        SqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("actionId", blockId.getBlockId())
                .addValue("scenarioId", scenarioId)
                .addValue("settingStatus", newActionState);
        int rowsUpdatedCount = namedParameterJdbcTemplate.update(updateSQL, updateParams);
        return rowsUpdatedCount > 0;
    }

    @Override
    public void updateActionContextVariables(UUID scenarioId, BlockId blockId, VariablesContainer variablesContainer) {
        String sql = "UPDATE action_contexts SET executing_variables = :executingVariables " +
                " where action_id = :actionId AND scenario_id = :scenarioId";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
                = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));

        SqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("actionId", blockId.getBlockId())
                .addValue("scenarioId", scenarioId)
                .addValue("executingVariables", variablesContainer.toJson());
        namedParameterJdbcTemplate.update(sql, updateParams);

    }

    @Override
    public void updateActionContextOutputSignal(UUID scenarioId, BlockId blockId, SignalId outputSignalId) {
        String sql = "UPDATE action_contexts SET output_signal_id  = :outputSignalId " +
                " where action_id = :actionId AND scenario_id = :scenarioId";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
                = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
        SqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("actionId", blockId.getBlockId())
                .addValue("scenarioId", scenarioId)
                .addValue("outputSignalId", outputSignalId.getId());
        namedParameterJdbcTemplate.update(sql, updateParams);
    }

    @Override
    public boolean saveContext(ActionContext actionContext) {
        String sql = "INSERT INTO action_contexts VALUES (:actionId, :scenarioId, :actionState, :executingVariables," +
                " :requestBlockId, :requestServiceName, :outputSignalId)";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate
                = new NamedParameterJdbcTemplate(Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
        SqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("actionId", actionContext.getActionId().getBlockId())
                .addValue("scenarioId", actionContext.getScenarioId())
                .addValue("actionState", actionContext.getActionState())
                .addValue("executingVariables", actionContext.getExecutingVariables().toJson())
                .addValue("requestBlockId", actionContext.getRequestBlockId().getBlockId())
                .addValue("requestServiceName", actionContext.getRequestServiceName())
                .addValue("outputSignalId", actionContext.getOutputSignalId().getId());

        return namedParameterJdbcTemplate.update(sql, updateParams) == 1;
    }


    /**
     * ������� ����� ������� ����������
     *
     * @param signal
     * @return
     */
//    public ActivityContext createNewActionContext(SignalDTO signal, Block actionBlock, String currentServiceName,
//                                                  Long currentServiceId ) {
//        throw new NullPointerException("NOT WORKING YET");

//        VariablesSynchRepo variablesSynchRepo = this;
//        return transactionManager.commitInTransaction(jpaTransactionManager -> {
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(
//                    Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            String INSERT_MESSAGE_SQL = "INSERT INTO action_history VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//            jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection
//                        .prepareStatement(INSERT_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
//                ps.setObject(1, signal.getScenarioId()); //���� ��������
//                ps.setLong(2, actionBlock.getId().getBlockId()); //���� ����� ������� ������ �����������
//                ps.setString(3, actionBlock.getId().getVersion().getVersionName()); // ������ �����, ������� ������ �����������
//                ps.setLong(4, 1);// ����������
//                ps.setLong(5, currentServiceId);//���� �������� �������
//                ps.setString(6, currentServiceName);//��� �������� �������
//                ps.setLong(7, signal.getProducerServiceId());//���� ������� �� �������� ���� �������� ����������
//                ps.setString(8, signal.getProducerServiceName());//��� ������� �� �������� ���� �������� ����������
//                ps.setLong(9, signal.getProducerBlockId().getBlockId());//���� ����� �� ������� ���� �������� ����������
//                ps.setString(10, signal.getProducerBlockId().getVersion().getVersionName());//������ ����� �� ������� ���� �������� ����������
//                ps.setLong(11, signal.getSignalId().getBlockId());//���� ������� ������� �������� ����������
//                ps.setString(12, signal.getSignalId().getVersion().getVersionName());//������  ������� ������� �������� ����������
//                ps.setString(13, ActionState.INITIALIZING.toString());//������� �����
//                ps.setTimestamp(14, new Timestamp(System.currentTimeMillis()));//��������� ����� ���������
//                return ps;
//            }, keyHolder);
//
//            UUID actionUUID = (UUID) keyHolder.getKeyList().get(0).get("action_id");
//            return ActivityContext.builder()
//                    .actionId(actionUUID)
//                    .businessId(signal.getScenarioId())
//                    .currentBlockId(actionBlock.getId())
//                    .currentTransactionId(1l)
//                    .currentServiceId(currentServiceId)
//                    .currentServiceName(currentServiceName)
//                    .startSignal(signal)
//                    .currentState(ActionState.INITIALIZING)
//                    .pureVariableList(actionBlock.getPureVariableList())
//                    .variablesContainer(new VariablesContainerImpl(variablesSynchRepo, actionId))
//                    .build();
//        });

//    }
//    public boolean updateActionContext(UUID scenario, BlockId blockId, ActionState actionState) {
//        throw new NullPointerException("NOT WORKING YET");
//    }

//    @Override
//    public void commitValues(Map<Long, ProcessValue> processValueMap, UUID actionId) {
//        transactionManager.executeInTransaction((TransactionCallable<Void>) jpaTransactionManager -> {
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(
//                    Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
//            String SQL = "INSERT INTO action_values VALUES(?,?,?)";
//            processValueMap.forEach((key,value) -> {
//                jdbcTemplate.update(conn -> {
//                    PreparedStatement pr = conn.prepareStatement(SQL);
//                    pr.setLong(1,key);
//                    pr.setObject(2, actionId);
//                    pr.setString(3,value.getJsonValue());
//                    return pr;
//                });
//            });
//
//            return null;
//        });
//    }
}
