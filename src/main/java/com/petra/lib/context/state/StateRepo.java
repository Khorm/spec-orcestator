package com.petra.lib.context.state;

import com.petra.lib.state.ActionState;
import com.petra.lib.transaction.TransactionManager;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
class StateRepo {
    private final TransactionManager transactionManager;

    public void updateState(UUID actionId, ActionState actionState){
        throw new NullPointerException("NO WORKING YET");
    }

}
