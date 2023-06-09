package com.petra.lib.manager.block;

import com.petra.lib.manager.models.BlockModel;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.variable.base.VariableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
class JobStaticManagerImpl implements JobStaticManager, Block {

    Map<JobState, JobStateManager> jobStateJobStateManagerMap = new HashMap<>();
    BlockId id;
    String blockName;

    @Getter
    Collection<SignalId> listeningSignalIds;

    VariableList variableList;


    JobStaticManagerImpl(BlockModel blockModel) {
        this.id = new BlockId(blockModel.getId(), blockModel.getVersion());
        this.blockName = blockModel.getName();
        this.listeningSignalIds = blockModel.getListeningSignals().stream()
                .map(id -> new SignalId(id.getId(),id.getMajorVersion())).collect(Collectors.toList());
        this.variableList = new VariableList(blockModel.getVariables());
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
    public BlockId getId() {
        return id;
    }

    @Override
    public void execute(RequestDto requestDto, Long signalId) {
        JobContext jobContext = new JobContext(requestDto, signalId, variableList, id);
        log.info("Start execution {} with params {}", blockName, requestDto.toString());
        executeState(jobContext, JobState.INITIALIZING);
    }

}
