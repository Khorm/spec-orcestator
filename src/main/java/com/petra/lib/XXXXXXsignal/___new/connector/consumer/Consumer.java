package com.petra.lib.XXXXXXsignal.___new.connector.consumer;

import com.petra.lib.environment.context.variables.VariablesContext;
import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ProducerHandler;

import java.util.UUID;

public interface Consumer {

    void send(VariablesContext sendingVariables, UUID businessId, ProducerHandler producerHandler);
}
