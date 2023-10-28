package com.petra.lib.state.variable.neww.loaders.user;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.variable.neww.loaders.UserVariableHandler;
import com.petra.lib.state.variable.neww.loaders.VariableLoader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManager;

/**
 * Лоадер обрабатывает передыдущие значения из конектста хендером и возвращает новое.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HandlerVariableLoader implements VariableLoader {

    UserVariableHandler userVariableHandler;
    JpaTransactionManager jpaTransactionManager;

    @Override
    public void load(ActivityContext activityContext) {
        EntityManager entityManager =
                EntityManagerFactoryUtils.getTransactionalEntityManager(jpaTransactionManager.getEntityManagerFactory());
        UserContext userContext = new UserContextImpl(activityContext, entityManager, activityContext.getPureVariableList());
        userVariableHandler.map(userContext);
    }

    @Override
    public boolean filled() {
        return ;
    }
}
