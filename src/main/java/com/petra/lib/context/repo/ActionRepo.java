package com.petra.lib.context.repo;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.state.ActionState;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.transaction.TransactionRunnable;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
    public ActivityContext createNewActionContext(Signal signal, VersionBlockId currentBlockId, String currentService, Long currentServiceId) {
        ActivityContext activityContext =
                transactionManager.executeInTransaction(new TransactionRunnable<ActivityContext>() {
                    @Override
                    public ActivityContext run(JpaTransactionManager jpaTransactionManager) {
                        JdbcTemplate jdbcTemplate = new JdbcTemplate(
                                Objects.requireNonNull(transactionManager.getJpaTransactionManager().getDataSource()));
                        KeyHolder keyHolder = new GeneratedKeyHolder();


                        jdbcTemplate.update(connection -> {

                            String INSERT_MESSAGE_SQL = "INSERT INTO action_history VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) VALUES ";

                            PreparedStatement ps = connection
                                    .prepareStatement(INSERT_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
                            ps.setObject(1, signal.getScenarioId()); //айди сценария
                            ps.setLong(2,currentBlockId.getBlockId()); //айди блока который сейчас исполняется
                            ps.setString(3,currentBlockId.getVersion().getVersionName()); // версия блока, которая сейчас исполняется
                            ps.setLong(4,1);// транзакция
                            ps.setLong(5,currentServiceId);//айди текщуего сервиса
                            ps.setString(6, currentService);//имя текущего сервиса
                            ps.setLong(7, signal.getProducerServiceId());//айди сервиса из которого была запущена активность
                            ps.setString(8, signal.getProducerServiceName());//имя сервиса из которого была запущена активность
                            ps.setLong(9, signal.getProducerBlockId().getBlockId());//айди блока из котрого была запущена активность
                            ps.setString(10, signal.getProducerBlockId().getVersion().getVersionName());//версия блока из котрого была запущена активность
                            ps.setLong(11, signal.getSignalId().getBlockId());//айди сигнала который запустил активность
                            ps.setString(12, signal.getSignalId().getVersion().getVersionName());//версия  сигнала который запустил активность
                            ps.setString(13, ActionState.INITIALIZING.toString());//текущий стейт
                            ps.setTimestamp(14, new Timestamp(System.currentTimeMillis()));//последнее время изменения
                            return ps;
                        },keyHolder);

                        UUID actionUUID = (UUID) keyHolder.getKeyList().get(0).get("action_id");

//                        int actionId = jdbcTemplate.update("INSERT INTO action_history VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) VALUES",
//                                ,
//                                signal.getConsumerBlockId().getBlockId(),
//                                signal.getConsumerBlockId().getVersion(),
//                                1,
//                                signal.getConsumerServiceId(),
//                                signal.getConsumerServiceName(),
//                                signal.getProducerServiceId(),
//                                signal.getProducerServiceName(),
//                                signal.getProducerBlockId().getBlockId(),
//                                signal.getProducerBlockId().getVersion(),
//                                signal.getSignalId().getBlockId(),
//                                signal.getSignalId().getVersion(),
//                                ActionState.INITIALIZING,
//                                new Timestamp(System.currentTimeMillis())
//                        );
//
//                        return;
                    }
                })

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
