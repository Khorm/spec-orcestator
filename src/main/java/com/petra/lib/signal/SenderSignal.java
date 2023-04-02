package com.petra.lib.signal;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;

public interface SenderSignal extends Signal{

    void send(ExecutionContext context);
}
