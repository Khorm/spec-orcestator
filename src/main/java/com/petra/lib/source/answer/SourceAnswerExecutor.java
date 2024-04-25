package com.petra.lib.source.answer;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.remote.response.source.SourceResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.source.SourceContext;
import com.petra.lib.source.enums.SourceResultStatus;
import com.petra.lib.variable.value.ValuesContainerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeUnit;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SourceAnswerExecutor {
    SourceResponse sourceResponse;
    RemoteThreadManager remoteThreadManager;

    public void execute(SourceContext sourceContext, SourceResultStatus status) {
        SourceResponseDto sourceResponseDto = new SourceResponseDto(
                sourceContext.getScenarioId(),
                sourceContext.getSourceId(),
                sourceContext.getRequestingWorkflowId(),
                sourceContext.getRequestingBlockId(),
                ValuesContainerFactory.toJson(sourceContext.getResultVariables()),
                status

        );

        remoteThreadManager.handle(() -> {
            BlockResponseResult blockResponseResult;
            do {
                blockResponseResult = sourceResponse.response(sourceResponseDto, sourceContext.getWorkflowServiceName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (blockResponseResult == BlockResponseResult.ERROR);
        });


    }
}
