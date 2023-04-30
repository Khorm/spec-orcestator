package com.petra.lib.worker.handler.impl;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.worker.handler.UserHandler;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.worker.repo.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserHandlerExecutor implements JobStateManager {

    UserHandler userHandler;
    JobStaticManager jobStaticManager;
    JpaTransactionManager jpaTransactionManager;
    JobRepository jobRepository;
    Long blockId;
    VariableList variableList;

    @Override
    public void execute(JobContext jobContext) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(jpaTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
                    UserContextImpl userContext = new UserContextImpl(jobContext, entityManager, variableList);
                    userHandler.execute(userContext);

                    JobContext filledJobContext = userContext.fillContext();
                    entityManager.flush();
                    String variables = filledJobContext.getVariablesJson();
                    UUID scenarioId = jobContext.getScenarioId();
                    jobRepository.saveExecution(scenarioId, blockId, variables);
                    jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
                } catch (Exception e) {
                    e.printStackTrace();
                    status.setRollbackOnly();
                    jobStaticManager.executeState(jobContext, JobState.ERROR);
                }
            }
        });
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
