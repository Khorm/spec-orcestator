package com.petra.lib.action.user;

import com.petra.lib.action.ActionContext;
import com.petra.lib.action.BlockState;
import com.petra.lib.action.repo.ActionRepo;
import com.petra.lib.action.user.handler.UserActionHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import java.util.Objects;

/**
 * обрабатывает вызов функции юзера
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@RequiredArgsConstructor
public class UserExecutor {
    TransactionManager transactionManager;
    UserActionHandler userActionHandler;

    ActionRepo actionRepo;
    PureVariableList actionVariables;

    /**
     * В одной транзакции выставляет стейт и обработывает пользоватский обработчик.
     *
     * @param context - current execution context
     * @throws Exception
     */
    public void execute(ActionContext context) throws Exception {
        transactionManager.executeInTransaction(jpaTransactionManager -> {

            EntityManager entityManager = EntityManagerFactoryUtils
                    .getTransactionalEntityManager(Objects.requireNonNull(jpaTransactionManager.getEntityManagerFactory()));
            UserActionContextImpl userContext = new UserActionContextImpl(entityManager, context, actionVariables);
            userActionHandler.execute(userContext);

            context.setActionState(BlockState.EXECUTED);
            actionRepo.updateActionContextVariables(context.getScenarioId(), context.getActionId(),
                    context.getActionVariables(), context.getActionState());
        });
    }

//    UserHandler userHandler;
//    StateManager stateManager;
//    JpaTransactionManager jpaTransactionManager;
//    JobRepository jobRepository;
//    VersionId blockId;
//    PureVariableList pureVariableList;
//    String blockName;
//
//
//    TransactionManager transactionManager;
//    ActionRepo actionRepo;
//    Block blockManager;

//    @Override
//    public void execute(ActionContext actionContext) throws JsonProcessingException {
//        log.debug("Start UserHandlerExecutor {} {}", blockName, actionContext.toString());
//
//        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);
//
//        try {
//            EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
//            ImplUserContext userContext = new ImplUserContext(actionContext, entityManager, pureVariableList);
//            userHandler.execute(userContext);
//
//            ActionContext filledActionContext = userContext.fillContext();
//            entityManager.flush();
//            String variables = filledActionContext.getVariablesJson();
//            UUID scenarioId = actionContext.getScenarioId();
//            int insertsCount = jobRepository.saveExecution(scenarioId, blockId, variables);
//            if (insertsCount == 0) {
//                jpaTransactionManager.rollback(transactionStatus);
//                actionContext.setAlreadyDone();
//                log.info("UserHandlerExecutor already execute this scenario {}", scenarioId);
//                return;
//            }
//            log.debug("Executed UserHandlerExecutor {} {}", blockName, actionContext.toString());
//
//            jpaTransactionManager.commit(transactionStatus);
//            stateManager.executeState(actionContext, JobState.EXECUTION_RESPONSE);
//        } catch (Exception e) {
//            log.error(e);
//            jpaTransactionManager.rollback(transactionStatus);
//            throw e;
//        }
//
//
////        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
////            @Override
////            protected void doInTransactionWithoutResult(TransactionStatus status) {
////                try {
////                    EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
////
////                    TransactionStatus transactionStatus = jpaTransactionManager.getTransaction();
////                    jpaTransactionManager.getDataSource().getConnection().
////
////                    UserContextImpl userContext = new UserContextImpl(jobContext, entityManager, variableList);
////                    userHandler.execute(userContext);
////
////                    JobContext filledJobContext = userContext.fillContext();
////                    entityManager.flush();
////                    String variables = filledJobContext.getVariablesJson();
////                    UUID scenarioId = jobContext.getScenarioId();
////                    jobRepository.saveExecution(scenarioId, blockId, variables);
////                    log.debug("Executed UserHandlerExecutor {} {}", blockName, jobContext.toString());
////                    jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
////                }catch (SQLIntegrityConstraintViolationException e) {
////
////                } catch (Exception e) {
////                    //TODO при ошибке фиксации если сообщение уже ранее было завфиксировано возвращать результат фиксации
////                    e.printStackTrace();
////                    status.setRollbackOnly();
////                    jobStaticManager.executeState(jobContext, JobState.ERROR);
////                }
////            }
////        });
//    }
//
//    @Override
//    public JobState getManagerState() {
//        return JobState.EXECUTING;
//    }

//    @Override
//    public void execute(ActivityContext context) throws Exception {
//        transactionManager.commitInTransaction(new TransactionCallable() {
//            @Override
//            public void run(JpaTransactionManager jpaTransactionManager) {
//
//                //запис нового состояния исполнения. Если оно уже в нем то ошибка.
//                boolean isTypeAccepted = actionRepo.updateActionType(context.getBusinessId(), context.getCurrentBlockId(),
//                        ActionState.EXECUTING);
//                if (!isTypeAccepted) {
//                    blockManager.error();
//                }
//
//                //исполнение польозвательского кода
//                EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
//                ImplUserContext userContext = new ImplUserContext(entityManager, context, pureVariableList);
//                userHandler.execute(userContext);
//                entityManager.flush();
//            }
//        });
//    }



//    UserExecutor(TransactionManager transactionManager, UserActionHandler userActionHandler,
//                 ActionRepo actionRepo, PureVariableList pureVariablesList) {
//        this.transactionManager = transactionManager;
//        this.userActionHandler = userActionHandler;
//        this.actionRepo = actionRepo;
//        this.pureVariablesList = pureVariablesList;
//    }



}
