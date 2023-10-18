package com.petra.lib.state.variable.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.PetraException;
import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.XXXXXXsignal.request.controller.AnswerListener;
import com.petra.lib.XXXXXXsignal.request.controller.SignalRequestManager;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;
import com.petra.lib.XXXXXXsignal.response.ResponseType;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.state.variable.group.repo.ContextRepo;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * Source loader load source variables for group.
 */
@Deprecated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceRequestManager implements AnswerListener {

    @Setter
    BiConsumer<Collection<ProcessValue>, DirtyContext> loadHandler;

    @Setter
    BiConsumer<Exception, DirtyContext> errorHandler;

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

    void load(DirtyContext context) {
        try {
            contextRepo.addNewRequestData(context);
        } catch (JsonProcessingException e) {
            errorHandler.accept(e, context);
        }
//        Collection<ProcessValue> signalVariables = toSourceVariableMapper.map(context.getProcessVariables());
//        signalRequestManager.request(requestSignalId, signalVariables, context.getScenarioId(), blockId);
    }


    private void executed(ResponseDto signalTransferModel) {
        DirtyContext actionContext = contextRepo.getExecutionContext(signalTransferModel.getScenarioId(), blockId);
        loadHandler.accept(signalTransferModel.getSignalVariables(), actionContext);
    }


    private void error(Exception e, ResponseDto signalTransferModel) {
        DirtyContext actionContext = contextRepo.getExecutionContext(signalTransferModel.getScenarioId(), blockId);
        errorHandler.accept(e, actionContext);
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
