package com.petra.lib.source;

import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.source.answer.SourceAnswerExecutor;
import com.petra.lib.source.enums.SourceResultStatus;
import com.petra.lib.source.user.SourceUserExecutor;
import com.petra.lib.variable.value.ValuesContainerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Source {

    Long id;
    String name;
    SourceUserExecutor sourceUserExecutor;
    ThreadQuery threadQuery;
    SourceAnswerExecutor sourceAnswerExecutor;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BlockRequestResult execute(SourceRequestDto sourceRequestDto) {
        SourceContext sourceContext = createSourceContext(sourceRequestDto);
        try {
            startSourceExecuting(sourceContext);
            return BlockRequestResult.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return BlockRequestResult.ERROR;
        }
    }

    private SourceContext createSourceContext(SourceRequestDto requestDto) {
        return new SourceContext(
                id,
                requestDto.getScenarioId(),
                ValuesContainerFactory.fromJson(requestDto.getSourceVariables()),
                requestDto.getWorkflowId(),
                requestDto.getRequestServiceName(),
                requestDto.getRequestBlockId()
        );
    }


    private void startSourceExecuting(SourceContext sourceContext) {
        threadQuery.popInQueue(() -> {
            try {
                sourceUserExecutor.execute(sourceContext);
                sourceAnswerExecutor.execute(sourceContext, SourceResultStatus.EXECUTED);
            } catch (Exception e) {
                sourceAnswerExecutor.execute(sourceContext, SourceResultStatus.ERROR);
                throw new RuntimeException(e);
            }
        });
    }
}
