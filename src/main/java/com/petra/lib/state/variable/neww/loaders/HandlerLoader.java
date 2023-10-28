package com.petra.lib.state.variable.neww.loaders;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.variable.neww.loaders.user.UserContext;
import com.petra.lib.state.variable.neww.loaders.user.UserContextImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManager;

/**
 * Выгружает все переменные из пользовательского хендлера
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HandlerLoader implements VariableLoader{

    UserVariableHandler userVariableHandler;
    JpaTransactionManager jpaTransactionManager;
    @Override
    public void load(ActivityContext activityContext) {
        EntityManager entityManager =
                EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
        UserContext userContext = new UserContextImpl(activityContext, entityManager, activityContext.getPureVariableList());
        userVariableHandler.map(userContext);
    }

}
