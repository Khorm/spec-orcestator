package com.petra.lib.workflow.variables;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.source.enums.SourceResultStatus;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.context.WorkflowActionContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class LoadGroup {

    @Getter
    int groupNumber;
    Collection<SourceLoader> sourceLoaders;
    VariableMapper fromAllSourcesVariableMapper;

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public void load(WorkflowActionContext workflowActionContext) throws ExecutionException, InterruptedException {
        Collection<Future<SourceResponseDto>> sourceFutures = new ArrayList<>();
        for (SourceLoader sourceLoader : sourceLoaders) {
//            асинхронно запрашивать переменные и понять что они все были запрошены
            Future<SourceResponseDto> requestSourceFuture =
                    executorService.submit(() -> sourceLoader.load(workflowActionContext));
            sourceFutures.add(requestSourceFuture);
        }
        for (Future<SourceResponseDto> responseDtoFuture : sourceFutures) {
            SourceResponseDto sourceResponseDto = responseDtoFuture.get();
            if (sourceResponseDto.getStatus() == SourceResultStatus.ERROR) throw new IllegalCallerException();
            VariablesContainer fromSourceParsedVariablesContainer =
                    fromAllSourcesVariableMapper.map(sourceResponseDto.getSignalVariables());
            workflowActionContext.setBlockVariables(fromSourceParsedVariablesContainer);
        }
    }
}
