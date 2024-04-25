package com.petra.lib.workflow.block.source;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Загружает одну из групп переменных
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class LoadGroup {

    @Getter
    int groupNumber;
    Collection<SourceLoader> sourceLoaders;
    PureVariableList blockPureVariableList;
    WorkflowActionRepo workflowActionRepo;


    public boolean load(WorkflowActionContext workflowActionContext) {
        workflowActionRepo.updateLoadingGroup(workflowActionContext,groupNumber);

        //парсинг данных из входящего сигнала
        Collection<ProcessValue> valueCollection =
                blockPureVariableList.parseVariables(workflowActionContext.getCallingSignalVariables().getValues());
        workflowActionContext.getBlockVariables().addValues(valueCollection);
        if (sourceLoaders.isEmpty()) return true;

        for (SourceLoader sourceLoader : sourceLoaders) {
            BlockRequestResult result = sourceLoader.load(workflowActionContext);
            if (result != BlockRequestResult.OK) {
                throw new IllegalStateException("Source request error");
            }
        }
        return false;
    }

    public boolean answered(WorkflowActionContext workflowActionContext, SourceResponseDto sourceResponseDto) {
        //заполнить соурс

        ValuesContainer valueCollection = ValuesContainerFactory.fromJson(sourceResponseDto.getSourceVariables());
        blockPureVariableList.parseVariables(valueCollection.getValues());

        ValuesContainer blockValuesContainer =  workflowActionContext.getBlockVariables();
        blockValuesContainer.addValues(valueCollection);
//        workflowActionContext.addLoadSource(sourceResponseDto.getSourceId());
//        workflowActionRepo.updateContext(workflowActionContext);
        workflowActionRepo.updateLoadSource(workflowActionContext,blockValuesContainer, sourceResponseDto.getSourceId() );


        //проверить все ли соурсы заполнили данные
        boolean isEmptySourceFind = false;
        for (SourceLoader sourceLoader : sourceLoaders) {
            if (!workflowActionContext.getLoadedSources().contains(sourceLoader.getRequestingSourceId())) {
                isEmptySourceFind = true;
                break;
            }
        }
        return !isEmptySourceFind;
    }
}
