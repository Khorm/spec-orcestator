package com.petra.lib.XXXXXXsignal.___new.connector.consumer;

import com.petra.lib.XXXXXcontext.DirtyVariablesList;
import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ProducerHandler;

import java.util.UUID;

public interface Consumer {

    void send(DirtyVariablesList sendingVariables, UUID businessId, ProducerHandler producerHandler);
}
