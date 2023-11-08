package com.petra.lib.state.error;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.ActionState;
import com.petra.lib.remote.output.OutputConsumeSocket;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState implements StateHandler {

    OutputConsumeSocket outputSocket;
    TransactionManager transactionManager;
//    ActionRepo actionRepo;

    @Override
    public void execute(ActivityContext context) throws Exception {
        throw new NullPointerException("NOT WORKING YET");
//        transactionManager.commitInTransaction(new TransactionCallable() {
//            @Override
//            public void run(JpaTransactionManager jpaTransactionManager) {
//                actionRepo.updateActionType(context.getBusinessId(), context.getCurrentBlockId(),
//                        getState());
//            }
//        });
//        outputSocket.answer(context, context.getRequestType().getAnswerType());
    }

    @Override
    public void start() {

    }

    @Override
    public ActionState getState() {
        return ActionState.ERROR;
    }
}
