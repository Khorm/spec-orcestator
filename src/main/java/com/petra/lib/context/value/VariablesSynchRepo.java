package com.petra.lib.context.value;

import com.petra.lib.transaction.TransactionManager;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
class VariablesSynchRepo {

    private final TransactionManager transactionManager;

    void commitValues(Map<Long, ProcessValue> processValueMap, UUID actionId){
        throw new NullPointerException("NO WORKING YET");
    }

}
