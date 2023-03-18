package com.petra.lib.signal.source;

import com.petra.lib.manager.ExecutionContext;

public interface SourceHandler {
    void execute(ExecutionContext executionContext);
    void start();
}
