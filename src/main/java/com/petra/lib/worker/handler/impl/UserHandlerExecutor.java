package com.petra.lib.worker.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.worker.handler.UserHandler;
import com.petra.lib.worker.repo.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserHandlerExecutor implements JobStateManager {

    UserHandler userHandler;
    JobStaticManager jobStaticManager;
    JpaTransactionManager jpaTransactionManager;
    JobRepository jobRepository;
    BlockId blockId;
    VariableList variableList;
    String blockName;

    @Override
    public void execute(JobContext jobContext) throws JsonProcessingException {
        log.debug("Start UserHandlerExecutor {} {}", blockName, jobContext.toString());

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);

        try {
            EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
            UserContextImpl userContext = new UserContextImpl(jobContext, entityManager, variableList);
            userHandler.execute(userContext);

            JobContext filledJobContext = userContext.fillContext();
            entityManager.flush();
            String variables = filledJobContext.getVariablesJson();
            UUID scenarioId = jobContext.getScenarioId();
            int insertsCount = jobRepository.saveExecution(scenarioId, blockId, variables);
            if (insertsCount == 0){
                jpaTransactionManager.rollback(transactionStatus);
                jobContext.setAlreadyDone();
                log.info("UserHandlerExecutor already execute this scenario {}" , scenarioId);
                return;
            }
            log.debug("Executed UserHandlerExecutor {} {}", blockName, jobContext.toString());

            jpaTransactionManager.commit(transactionStatus);
            jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
        }
        catch (Exception e){
            log.error(e);
            jpaTransactionManager.rollback(transactionStatus);
            throw e;
        }


//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus status) {
//                try {
//                    EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
//
//                    TransactionStatus transactionStatus = jpaTransactionManager.getTransaction();
//                    jpaTransactionManager.getDataSource().getConnection().
//
//                    UserContextImpl userContext = new UserContextImpl(jobContext, entityManager, variableList);
//                    userHandler.execute(userContext);
//
//                    JobContext filledJobContext = userContext.fillContext();
//                    entityManager.flush();
//                    String variables = filledJobContext.getVariablesJson();
//                    UUID scenarioId = jobContext.getScenarioId();
//                    jobRepository.saveExecution(scenarioId, blockId, variables);
//                    log.debug("Executed UserHandlerExecutor {} {}", blockName, jobContext.toString());
//                    jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
//                }catch (SQLIntegrityConstraintViolationException e) {
//
//                } catch (Exception e) {
//                    //TODO при ошибке фиксации если сообщение уже ранее было завфиксировано возвращать результат фиксации
//                    e.printStackTrace();
//                    status.setRollbackOnly();
//                    jobStaticManager.executeState(jobContext, JobState.ERROR);
//                }
//            }
//        });
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTING;
    }

    @Override
    public void start() {
        //do nothin
    }
}
