package com.petra.lib.remote.request;

import com.petra.lib.block.BlockId;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RequestDto {
    /**
     * айди бизнес задачи
     */
    UUID scenarioId;

    /**
     * айди сигнала
     */
    Long id;

    /**
     * параметры сигнала
     */
    VariablesContainer signalVariables;

    /**
     * айди запрашивающего блока
     */
    Long requestBlockId;
    /**
     * имя сервиса запрашивающей воркфлоу
     */
    String requestWorkflowServiceName;

    /**
     * блок к исполнению
     */
    Long executingBlockId;
//    RequestSignalType requestSignalType;
//    Long responseBlockId;


}
