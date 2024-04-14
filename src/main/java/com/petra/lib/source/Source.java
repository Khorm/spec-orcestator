package com.petra.lib.source;

import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.source.enums.SourceResultStatus;
import com.petra.lib.source.user.SourceUserExecutor;

public class Source {

    Long id;
    String name;
    SourceUserExecutor sourceUserExecutor;
    ThreadQuery threadQuery;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SourceResponseDto execute(SourceRequestDto sourceRequestDto) {
        SourceContext sourceContext = createSourceContext(sourceRequestDto);
        try {
            startSourceExecuting(sourceContext);
            return createResponseDto(sourceContext, SourceResultStatus.EXECUTED);
        } catch (Exception e) {
            e.printStackTrace();
            return createResponseDto(sourceContext, SourceResultStatus.ERROR);
        }
    }

    private SourceContext createSourceContext(SourceRequestDto requestDto) {

        return new SourceContext(
                id,
                requestDto.getScenarioId(),
                requestDto.getSignalVariables()
        );
    }

    private SourceResponseDto createResponseDto(SourceContext sourceContext, SourceResultStatus status) {
        return new SourceResponseDto(
                sourceContext.getScenarioId(),
                sourceContext.getSourceId(),
                sourceContext.getResultVariables(),
                status
        );
    }

    private void startSourceExecuting(SourceContext sourceContext) {
        //TODO сделать асинхронный соурс
//        threadQuery.popInQueueAndWait(() -> {
//            sourceUserExecutor.execute(sourceContext);
//        });
    }
}
