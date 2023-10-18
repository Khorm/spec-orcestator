package com.petra.lib.XXXXXXsignal;

import com.petra.lib.PetraProps;
import com.petra.lib.XXXXXXsignal.response.controller.SignalResponseHandler;
import com.petra.lib.XXXXXXsignal.model.MessageType;
import com.petra.lib.XXXXXXsignal.model.SignalModel;
import com.petra.lib.XXXXXXsignal.model.SignalType;
import com.petra.lib.XXXXXXsignal.request.SignalRequestListener;
import com.petra.lib.XXXXXXsignal.request.SyncRequest;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Collection;

public final class SignalFactory {

    public synchronized SignalMap buildSignals(Collection<SignalModel> signalModels, SignalRequestListener signalRequestListener,
                                               SignalResponseHandler signalResponseHandler, ConfigurableListableBeanFactory beanFactory,
                                               PetraProps petraProps) {
        SignalMapImpl signalMap = new SignalMapImpl();

        for (SignalModel signalModel : signalModels) {
            if (signalModel.getSignalType() == SignalType.HTTP) {
                if (signalModel.getMessageType() == MessageType.REQUEST) {
                    signalMap.setRequestSignal(new SyncRequest(signalModel, signalRequestListener));
                } else if (signalModel.getMessageType() == MessageType.RESPONSE) {
                    signalMap.setResponseSignal(new SyncResponse(signalModel, beanFactory, signalResponseHandler));
                }
            } else if (signalModel.getSignalType() == SignalType.KAFKA) {
                throw new IllegalStateException("Kafka not supported yet");
//                if (signalModel.getMessageType() == MessageType.REQUEST) {
//                    signalMap.setRequestSignal(new KafkaRequest(signalModel, petraProps, signalRequestListener));
//                } else if (signalModel.getMessageType() == MessageType.RESPONSE) {
//                    signalMap.setResponseSignal(new KafkaResponse(signalModel, petraProps, signalResponseListener));
//                }
            }
        }

        return signalMap;
    }
}
