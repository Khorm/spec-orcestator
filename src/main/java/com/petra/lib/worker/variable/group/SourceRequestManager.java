package com.petra.lib.worker.variable.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.PetraException;
import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.request.controller.AnswerListener;
import com.petra.lib.signal.request.controller.SignalRequestManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.ResponseDto;
import com.petra.lib.signal.response.ResponseType;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.worker.variable.group.repo.ContextRepo;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * Source loader load source variables for group.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceRequestManager implements AnswerListener {

    @Setter
    BiConsumer<Collection<ProcessVariableDto>, JobContext> loadHandler;

    @Setter
    BiConsumer<Exception, JobContext> errorHandler;

    final SignalRequestManager signalRequestManager;
    final VariableMapper toSourceVariableMapper;
    final ContextRepo contextRepo;
    final BlockId blockId;
    final SignalId requestSignalId;

    SourceRequestManager(SignalRequestManager signalRequestManager,
                         VariableMapper toSourceVariableMapper,
                         ContextRepo contextRepo,
                         BlockId blockId,
                         SignalId requestSignalId) {
        this.signalRequestManager = signalRequestManager;
        this.toSourceVariableMapper = toSourceVariableMapper;
        this.contextRepo = contextRepo;
        this.blockId = blockId;
        this.requestSignalId = requestSignalId;
    }

    void load(JobContext context) {
        try {
            contextRepo.addNewRequestData(context);
        } catch (JsonProcessingException e) {
            errorHandler.accept(e, context);
        }
        Collection<ProcessVariableDto> signalVariables = toSourceVariableMapper.map(context.getProcessVariables());
        signalRequestManager.request(requestSignalId, signalVariables, context.getScenarioId(), blockId);
    }


    private void executed(ResponseDto signalTransferModel) {
        JobContext jobContext = contextRepo.getExecutionContext(signalTransferModel.getScenarioId(), blockId);
        loadHandler.accept(signalTransferModel.getSignalVariables(), jobContext);
    }


    private void error(Exception e, ResponseDto signalTransferModel) {
        JobContext jobContext = contextRepo.getExecutionContext(signalTransferModel.getScenarioId(), blockId);
        errorHandler.accept(e, jobContext);
    }

    @Override
    public void answer(ResponseDto responseDto) {
        if (responseDto.getResponseType() == ResponseType.RESPONSE){
            executed(responseDto);
        }else if (responseDto.getResponseType() == ResponseType.ERROR){
            error(new PetraException("Source request error", null), responseDto);
        }
    }
}
