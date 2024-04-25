package com.petra.lib.workflow.subscribe;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscribeManager {

    Collection<Listener> listeners = new ArrayList<>();

    public void sendSignal(WorkflowSignal signal, WorkflowSignalModel workflowSignalModel){
        try {
            for (Listener listener : listeners){
                listener.handle(signal,workflowSignalModel);
            }
        }catch (Exception e){
            e.printStackTrace();
            for (Listener listener : listeners){
                listener.handle(WorkflowSignal.EXECUTION_BLOCK_ERROR ,workflowSignalModel);
            }
        }

    }

    public synchronized void subscribe(Listener listener){
        listeners.add(listener);
    }
}
