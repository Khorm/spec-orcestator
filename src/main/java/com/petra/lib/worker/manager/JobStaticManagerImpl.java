package com.petra.lib.worker.manager;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.StateController;
import com.petra.lib.manager.thread.ThreadManager;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class JobStaticManagerImpl implements JobStaticManager {

//    /**
//     * список переменных блока
//     */
//    VariableList variableList;
//
//    /**
//     * маппер от входящего сигнала
//     */
//    VariableMapper inputMapper;
//
//    /**
//     * Управление менеджерами
//     */
////    ThreadManager threadManager;
//
//    /**
//     * контроллер переключения стейтов
//     */
//    StateController stateController;
//
    PlatformTransactionManager transactionManager;
    Map<JobState, JobStateManager> jobStateJobStateManagerMap = new HashMap<>();


//    private void start(SignalTransferModel signalTransferModel) {
//        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
//        definition.setTimeout(3);
//        TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
//
//        JobContext jobContext = new JobContext(variableList, inputMapper, signalTransferModel, transactionStatus);
//        JobStateManager firstState = stateController.getState(JobState.INITIALIZING);
//        executeTask(jobContext, firstState);
//    }

    JobStaticManagerImpl(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }


    public void executeNext(JobContext jobContext, JobState executedState) {
//        Optional<JobState> nextState = stateController.getNextState(executedState);
//        if (nextState.isPresent()) {
//            executeState(jobContext, nextState.get());
//        }
        throw new Error("not allowd method");
    }

    @Override
    public void executeState(JobContext jobContext, JobState executedState) {
        JobStateManager jobStateManager = jobStateJobStateManagerMap.get(executedState);
        executeTask(jobContext, jobStateManager);
    }

    @Override
    public void setStateManager(JobStateManager jobStateManager) {
        jobStateJobStateManagerMap.put(jobStateManager.getManagerState(), jobStateManager);
    }

    @Override
    public void start() {
        jobStateJobStateManagerMap.values().forEach(manager -> manager.start());
    }

    private void executeTask(JobContext jobContext, JobStateManager task) {
        JobState jobState = task.getManagerState();
        if (jobState.isExecuteInNewThread()) {
            ThreadManager.execute(() -> {
                try {
                    task.execute(jobContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionManager.rollback(jobContext.getTransactionStatus());
                }
            });
        } else {
            try {
                task.execute(jobContext);
            } catch (Exception e) {
                e.printStackTrace();
                transactionManager.rollback(jobContext.getTransactionStatus());
            }
        }
    }
}
