package com.petra.lib.action.repo;

import com.petra.lib.action.BlockState;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.transaction.TransactionManagerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StateTest {

    @Autowired
    JpaTransactionManager jpaTransactionManager;

//    @Test
    public void saveStateTest() throws Exception {
//        System.out.println(jpaTransactionManager);
        TransactionManager transactionManager = new TransactionManagerImpl(jpaTransactionManager);
        ActivityRepoImpl activityRepo = new ActivityRepoImpl(transactionManager);

        UUID scnId = UUID.fromString("292a485f-a56a-4938-8f1a-bbbbbbbbbbb1");
        Long actionId = 1L;


        boolean res = activityRepo.updateActionState(scnId, actionId, BlockState.EXECUTED);
        System.out.println(res);
    }

    @Test
    public void saveStateAndParamTest() throws Exception {
        TransactionManager transactionManager = new TransactionManagerImpl(jpaTransactionManager);
        ActivityRepoImpl activityRepo = new ActivityRepoImpl(transactionManager);
        UUID scnId = UUID.fromString("292a485f-a56a-4938-8f1a-bbbbbbbbbbb1");
        Long actionId = 1L;

        boolean res = activityRepo.updateActionContextVariables(scnId, actionId, null, BlockState.COMPLETE);
        System.out.println(res);

    }
}
