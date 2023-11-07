package com.petra.lib.XXXXXXsignal.___new.connector.consumer;

import com.petra.lib.context.variables.VariablesContainerImpl;
import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ProducerHandler;

import java.util.UUID;

public interface Consumer {

    void send(VariablesContainerImpl sendingVariables, UUID businessId, ProducerHandler producerHandler);
}
