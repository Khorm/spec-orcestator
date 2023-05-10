package com.petra.lib.manager.block;

import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.signal.model.RequestDto;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
class JobStaticManagerImpl implements JobStaticManager, Block {

    Map<JobState, JobStateManager> jobStateJobStateManagerMap = new HashMap<>();
    Long id;
    String blockName;

    @Getter
    Collection<Long> listeningSignalIds;

    /**
     * Request signal variable mapper
     */
    VariableMapper inputMapper;

    JobStaticManagerImpl(Long id, String blockName, Collection<Long> listeningSignalIds, VariableMapper inputMapper) {
        this.id = id;
        this.blockName = blockName;
        this.listeningSignalIds = listeningSignalIds;
        this.inputMapper = inputMapper;
    }

    @Override
    public void executeState(JobContext jobContext, JobState executedState) {
        JobStateManager jobStateManager = jobStateJobStateManagerMap.get(executedState);
        try {
            jobStateManager.execute(jobContext);
        } catch (Exception e) {
            log.error(e);
            try {
                jobStateJobStateManagerMap.get(JobState.ERROR).execute(jobContext);
            } catch (Exception ex) {
                log.error(ex);
                throw new Error("Critical error in error handler", ex);
            }
        }
    }

    @Override
    public void putStateManager(JobStateManager jobStateManager) {
        jobStateJobStateManagerMap.put(jobStateManager.getManagerState(), jobStateManager);
    }

    @Override
    public void start() {
        jobStateJobStateManagerMap.values().forEach(manager -> manager.start());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void execute(RequestDto requestDto) {
        Collection<ProcessVariableDto> processVariableDtos = inputMapper.map(requestDto.getSignalVariables());
        JobContext jobContext = new JobContext(requestDto.getScenarioId(), processVariableDtos, requestDto.getSignalId());
        log.info("Start execution {} with params {}", blockName, requestDto.toString());
        executeState(jobContext, JobState.INITIALIZING);
    }
}
