package com.petra.lib.source.user;

import com.petra.lib.source.SourceContext;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.pure.PureVariableList;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import java.util.Objects;

public class SourceUserExecutor {

    TransactionManager transactionManager;
    UserSourceHandler userSourceHandler;
    PureVariableList pureVariableList;

    public void execute(SourceContext sourceContext) throws Exception {

        transactionManager.executeInTransaction(jpaTransactionManager -> {
            EntityManager entityManager = EntityManagerFactoryUtils
                    .getTransactionalEntityManager(Objects.requireNonNull(jpaTransactionManager.getEntityManagerFactory()));
            UserSourceContextImpl userSourceContext = new UserSourceContextImpl(entityManager, sourceContext.getArguments(), pureVariableList);
            userSourceHandler.execute(userSourceContext);
            sourceContext.setResult(userSourceContext.getResultContainer());
        });
    }
}
