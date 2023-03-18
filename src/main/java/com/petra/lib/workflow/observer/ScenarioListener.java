package com.petra.lib.workflow.observer;

import com.petra.lib.signal.model.ExecutionResponse;

public interface ScenarioListener {
    void update(ExecutionResponse executionResponseDto);
}
