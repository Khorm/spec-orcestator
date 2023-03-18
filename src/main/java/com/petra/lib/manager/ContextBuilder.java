package com.petra.lib.manager;

import com.petra.lib.signal.model.ExecutionRequest;

public interface ContextBuilder {
    ExecutionContext createContext(ExecutionRequest executionRequest);
}
