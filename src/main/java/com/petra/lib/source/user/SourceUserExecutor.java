package com.petra.lib.source.user;

import com.petra.lib.source.SourceContext;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class SourceUserExecutor {

    TransactionManager transactionManager;
    UserSourceHandler userSourceHandler;
    PureVariableList sourceVariables;

    public void execute(SourceContext sourceContext) throws Exception {

        transactionManager.executeInTransaction(jpaTransactionManager -> {
            EntityManager entityManager = EntityManagerFactoryUtils
                    .getTransactionalEntityManager(Objects.requireNonNull(jpaTransactionManager.getEntityManagerFactory()));
            UserSourceContextImpl userSourceContext = new UserSourceContextImpl(entityManager, sourceContext.getArguments(), sourceVariables);
            userSourceHandler.execute(userSourceContext);
            sourceContext.setResult(userSourceContext.getResultContainer());
        });
    }
}
