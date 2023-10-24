package com.petra.lib.state.error;

import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.output.OutputSocket;
import com.petra.lib.environment.repo.ActionRepo;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.transaction.TransactionRunnable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.jpa.JpaTransactionManager;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState implements StateHandler {

    OutputSocket outputSocket;
    TransactionManager transactionManager;
    ActionRepo actionRepo;

    @Override
    public void execute(ActivityContext context) throws Exception {
        transactionManager.executeInTransaction(new TransactionRunnable() {
            @Override
            public void run(JpaTransactionManager jpaTransactionManager) {
                actionRepo.updateActionType(context.getBusinessId(), context.getCurrentBlockId(),
                        getState());
            }
        });
        outputSocket.answer(context, context.getRequestType().getAnswerType());
    }

    @Override
    public void start() {

    }

    @Override
    public ActionState getState() {
        return ActionState.ERROR;
    }
}
