package com.petra.lib.manager.thread;

import com.petra.lib.signal.ReceiverSignal;
import com.petra.lib.signal.SignalListener;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class ExecutionThreadController implements SignalListener, ReceiverSignal {

    SignalListener signalListener;
    ReceiverSignal receiverSignal;
    Map<UUID, Object> mutexMap = new HashMap<>();

    @Override
    public void executeSignal(SignalTransferModel request) {        
        mutexMap.put(request.getScenarioId(), new Object());
        signalListener.executeSignal(request);
        
        synchronized (mutexMap){
            Object mutex = mutexMap.get(request.getScenarioId());
            try {
                synchronized (mutex) {
                    mutex.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public void setAnswer(SignalTransferModel answer) {
        receiverSignal.setAnswer(answer);
        synchronized (mutexMap){
            Object mutex = mutexMap.remove(answer.getScenarioId());
            synchronized (mutex){
                mutex.notify();
            }
        }
    }

    @Override
    public void executionError(SignalTransferModel executingRequest) {
        receiverSignal.executionError(executingRequest);
        synchronized (mutexMap){
            Object mutex = mutexMap.remove(executingRequest.getScenarioId());
            synchronized (mutex){
                mutex.notify();
            }
        }
    }

    @Override
    public Long getId() {
        return receiverSignal.getId();
    }

    @Override
    public void start() {
        receiverSignal.start();
    }
}
