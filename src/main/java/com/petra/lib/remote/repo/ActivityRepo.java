package com.petra.lib.remote.repo;

import com.petra.lib.block.Block;
import com.petra.lib.block.VersionId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.variables.VariablesContainerImpl;
import com.petra.lib.context.variables.VariablesSynchRepo;
import com.petra.lib.remote.signal.Signal;
import com.petra.lib.context.state.ActionState;
import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.transaction.TransactionCallable;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ActivityRepo implements VariablesSynchRepo {

    private final TransactionManager transactionManager;

    public Optional<ActivityContext> getActionContext(UUID scenarioId, VersionId blockVersionId) {

        ActivityContext context = transactionManager.commitInTransaction(task -> {
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


    /**
     * создать новый конекст активности
     *
     * @param signal
     * @return
     */
    public ActivityContext createNewActionContext(Signal signal, Block actionBlock, String currentServiceName,
                                                  Long currentServiceId ) {
        VariablesSynchRepo variablesSynchRepo = this;
        return transactionManager.commitInTransaction(jpaTransactionManager -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(
                    Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String INSERT_MESSAGE_SQL = "INSERT INTO action_history VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(INSERT_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, signal.getScenarioId()); //айди сценария
                ps.setLong(2, actionBlock.getId().getBlockId()); //айди блока который сейчас исполняется
                ps.setString(3, actionBlock.getId().getVersion().getVersionName()); // версия блока, которая сейчас исполняется
                ps.setLong(4, 1);// транзакция
                ps.setLong(5, currentServiceId);//айди текщуего сервиса
                ps.setString(6, currentServiceName);//имя текущего сервиса
                ps.setLong(7, signal.getProducerServiceId());//айди сервиса из которого была запущена активность
                ps.setString(8, signal.getProducerServiceName());//имя сервиса из которого была запущена активность
                ps.setLong(9, signal.getProducerBlockId().getBlockId());//айди блока из котрого была запущена активность
                ps.setString(10, signal.getProducerBlockId().getVersion().getVersionName());//версия блока из котрого была запущена активность
                ps.setLong(11, signal.getSignalId().getBlockId());//айди сигнала который запустил активность
                ps.setString(12, signal.getSignalId().getVersion().getVersionName());//версия  сигнала который запустил активность
                ps.setString(13, ActionState.INITIALIZING.toString());//текущий стейт
                ps.setTimestamp(14, new Timestamp(System.currentTimeMillis()));//последнее время изменения
                return ps;
            }, keyHolder);

            UUID actionUUID = (UUID) keyHolder.getKeyList().get(0).get("action_id");
            return ActivityContext.builder()
                    .actionId(actionUUID)
                    .businessId(signal.getScenarioId())
                    .currentBlockId(actionBlock.getId())
                    .currentTransactionId(1l)
                    .currentServiceId(currentServiceId)
                    .currentServiceName(currentServiceName)
                    .startSignal(signal)
                    .currentState(ActionState.INITIALIZING)
                    .pureVariableList(actionBlock.getPureVariableList())
                    .variablesContainer(new VariablesContainerImpl(variablesSynchRepo, actionId))
                    .build();
        });

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

    public boolean updateActionType(UUID scenario, VersionId blockId, ActionState actionState) {

    }

    @Override
    public void commitValues(Map<Long, ProcessValue> processValueMap, UUID actionId) {
        transactionManager.commitInTransaction((TransactionCallable<Void>) jpaTransactionManager -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(
                    Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
            String SQL = "INSERT INTO action_values VALUES(?,?,?)";
            processValueMap.forEach((key,value) -> {
                jdbcTemplate.update(conn -> {
                    PreparedStatement pr = conn.prepareStatement(SQL);
                    pr.setLong(1,key);
                    pr.setObject(2, actionId);
                    pr.setString(3,value.getJsonValue());
                    return pr;
                });
            });

            return null;
        });
    }
}
