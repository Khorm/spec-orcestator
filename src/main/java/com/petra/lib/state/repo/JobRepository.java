package com.petra.lib.state.repo;

import com.petra.lib.workflow.model.ExecutionStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;

@Deprecated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobRepository {
//    NamedParameterJdbcTemplate jdbcTemplate;
    JpaTransactionManager jpaTransactionManager;

    public JobRepository(JpaTransactionManager jpaTransactionManager) {
        this.jpaTransactionManager = jpaTransactionManager;
    }
//
//    /**
//     * Проверяет, был ли блок выполнен раньше
//     *
//     * @param scenarioId - айжи сценария
//     * @param blockId    - айди блока
//     * @return
//     */
    public Optional<ExecutionStatus> isExecutedBefore(UUID scenarioId, BlockId blockId) {



        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(jpaTransactionManager.getDataSource());

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId.getBlockId())
                .addValue("blockVersion", blockId.getVersion().toString());

        String status = jdbcTemplate.queryForObject("SELECT execution_status FROM EXECUTION_HISTORY WHERE SCENARIO_ID " +
                " = :scenarioId AND BLOCK_ID = :blockId AND BLOCK_VERSION = :blockVersion ", namedParameters, String.class);
        return Optional.ofNullable(ExecutionStatus.valueOf(status));
    }
//
//    public void setExecutingStatus(ExecutionStatus executionStatus) {
//
//
//    }
//
//    public String getErrorMessage(UUID scenarioId, BlockId blockId) {
//
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("scenarioId", scenarioId)
//                .addValue("blockId", blockId.getBlockId())
//                .addValue("blockVersion", blockId.getVersion().toString());
//
//        return jdbcTemplate.queryForObject("SELECT result FROM EXECUTION_HISTORY WHERE SCENARIO_ID " +
//                " = :scenarioId AND BLOCK_ID = :blockId AND BLOCK_VERSION = :blockVersion " +
//                " AND execution_status != 'EXECUTING' ", namedParameters, String.class);
//    }
//
//    public int saveExecution(UUID scenarioId, BlockId blockId, String variablesJson) {
////        SqlParameterSource namedParameters = new MapSqlParameterSource()
////                .addValue("scenarioId", scenarioId)
////                .addValue("blockId", blockId.getBlockId())
////                .addValue("blockVersion", blockId.getVersion().toString())
////                .addValue("result_variables", variablesJson);
////
////        return jdbcTemplate.update("INSERT INTO JOB_HISTORY VALUES (:scenarioId, :blockId,:blockVersion, :result_variables)", namedParameters);
//
//    }
//
//    public Collection<ProcessValue> getVariables(UUID scenarioId, BlockId blockId) throws JsonProcessingException {
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("scenarioId", scenarioId)
//                .addValue("blockId", blockId.getBlockId())
//                .addValue("blockVersion", blockId.getVersion().toString());
//
//        String SQL = "SELECT result_variables FROM EXECUTION_HISTORY WHERE scenario_id = :scenarioId " +
//                " AND block_id = :blockId AND BLOCK_VERSION = :blockVersion";
//        String values = jdbcTemplate.queryForObject(SQL, namedParameters, String.class);
//        return new ObjectMapper().readValue(values, new TypeReference<>() {
//        });
//    }
}
