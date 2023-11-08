package com.petra.lib.block;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.variable.pure.PureVariableList;


public interface Block {
    BlockVersionId getId();

    String getName();

    PureVariableList getPureVariableList();

    void execute(ActivityContext activityContext);

//    boolean isSequentially();
//
//    void error();
//
//    VariableMapper getMapperForSignal(VersionId signalId);

//    BlockInfo getBlockInfo();
}
