package com.petra.lib.state.initialize;

import com.petra.lib.block.Block;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;

public class InitializeFactory {
    public static StateHandler createInitState(VariableMapper variableMapper,
                                               Block block,
                                               TransactionManager transactionManager) {
        return new Initializer(variableMapper, block, transactionManager);
    }
}
