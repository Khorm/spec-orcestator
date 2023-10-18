package com.petra.lib.state.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.XXXXXcontext.user.UserContextImpl;
import com.petra.lib.block.BlockId;
import com.petra.lib.XXXXXcontext.ActionContext;
import com.petra.lib.XXXXXmanager.state.JobStateManager;
import com.petra.lib.variable.base.PureVariableList;
import com.petra.lib.state.handler.UserHandler;
import com.petra.lib.state.repo.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class UserHandlerExecutor implements JobStateManager {

    UserHandler userHandler;
    StateManager stateManager;
    JpaTransactionManager jpaTransactionManager;
    JobRepository jobRepository;
    BlockId blockId;
    PureVariableList pureVariableList;
    String blockName;

    @Override
    public void execute(ActionContext actionContext) throws JsonProcessingException {
        log.debug("Start UserHandlerExecutor {} {}", blockName, actionContext.toString());

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);

        try {
            EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
            UserContextImpl userContext = new UserContextImpl(actionContext, entityManager, pureVariableList);
            userHandler.execute(userContext);

            ActionContext filledActionContext = userContext.fillContext();
            entityManager.flush();
            String variables = filledActionContext.getVariablesJson();
            UUID scenarioId = actionContext.getScenarioId();
            int insertsCount = jobRepository.saveExecution(scenarioId, blockId, variables);
            if (insertsCount == 0){
                jpaTransactionManager.rollback(transactionStatus);
                actionContext.setAlreadyDone();
                log.info("UserHandlerExecutor already execute this scenario {}" , scenarioId);
                return;
            }
            log.debug("Executed UserHandlerExecutor {} {}", blockName, actionContext.toString());

            jpaTransactionManager.commit(transactionStatus);
            stateManager.executeState(actionContext, JobState.EXECUTION_RESPONSE);
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
