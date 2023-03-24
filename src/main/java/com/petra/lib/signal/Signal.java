package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface Signal {
    void send(UUID scenarioId, Collection<ProcessVariable> valueList,
              BiConsumer<Exception, SignalTransferModel> sendErrorHandler);
    void accept();
}
