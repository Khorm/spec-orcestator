package com.petra.lib.worker.initialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.registration.JobRepository;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.SignalResponseListener;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.worker.manager.JobStaticManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;

/**
 * Manage the start of block execution. Initialize execution context and check previous executions.
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Initializer implements JobStateManager, SignalResponseListener {

    /**
     * Block id, which initializer belongs.
     */
    Long blockId;
    String blockName;
    JobStaticManager jobStaticManager;
    JobRepository jobRepository;

    /**
     * Job request signal listener
     */
    ResponseSignal blockResponseSignal;
    PlatformTransactionManager transactionManager;

    /**
     * List of static block variables
     */
    VariableList variableList;

    /**
     * Request signal variable mapper
     */
    VariableMapper inputMapper;

    @Override
    public void execute(JobContext jobContext) throws JsonProcessingException {
        boolean isExecutedBefore = jobRepository.isExecutedBefore(jobContext.getScenarioId(), blockId);
        if (isExecutedBefore) {
            jobContext.setLoadVariables(jobRepository.getVariables(jobContext.getScenarioId(), blockId));
            jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
            return;
        }
        Collection<ProcessVariable> signalVariables = jobContext.getSignalVariables();
        jobContext.setSignalVariables(signalVariables);
        jobStaticManager.executeState(jobContext, JobState.REQUEST_SOURCE_DATA);
    }

    @Override
    public JobState getManagerState() {
        return JobState.INITIALIZING;
    }

    @Override
    public void start() {
        blockResponseSignal.startSignal();
    }

    @Override
    public void executeSignal(SignalTransferModel request) {
        //обработать сигнал
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        TransactionStatus transactionStatus = transactionManager.getTransaction(definition);

        JobContext jobContext = new JobContext(variableList, inputMapper, request, transactionStatus);
        log.info("Start execution {} with params {}", blockName, jobContext.toString());
        try {
            execute(jobContext);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
