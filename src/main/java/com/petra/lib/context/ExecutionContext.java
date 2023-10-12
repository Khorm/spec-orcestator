package com.petra.lib.context;

import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.SignalId;

import java.util.Collection;
import java.util.UUID;

public interface ExecutionContext {

    /**
     * @return current scenario id
     */
    UUID getScenarioId();

    /**
     * @param values - filled process values
     */
    void setValues(Collection<ProcessValue> values);
    void setValue(ProcessValue value);

    /**
     * @return id of scenario request, which starts process
     */
    SignalId getRequestSignalId();

    /**
     * @return id of block, where process executing
     */
    BlockId getBlockId();

//    void setError(StateError stateError);

    Collection<ProcessValue> getProcessValues();
    Collection<ProcessValue> getRequestValues();

//    StatelessVariableList getStatelessVariableList();
}
