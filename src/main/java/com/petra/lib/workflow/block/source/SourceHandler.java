package com.petra.lib.workflow.block.source;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.workflow.context.WorkflowActionContext;

public interface SourceHandler {
    /**
     * Запускает загрузку переменных
     * @param workflowActionContext
     * @return
     * true - переменные загружены
     * false - переменные загружаются асинхронно
     */
    boolean startValuesFilling(WorkflowActionContext workflowActionContext);

    boolean setSourceAnswer(WorkflowActionContext workflowActionContext, SourceResponseDto sourceResponseModel);
}
