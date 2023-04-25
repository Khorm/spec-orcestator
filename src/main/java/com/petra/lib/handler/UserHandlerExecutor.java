package com.petra.lib.handler;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.worker.manager.JobStaticManager;
import com.petra.lib.manager.block.JobStateManager;
import com.petra.lib.manager.state.JobState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserHandlerExecutor implements JobStateManager {

    UserHandler userHandler;
    JobStaticManager jobStaticManager;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("petra");

    @Override
    public void execute(JobContext jobContext) {
        EntityManager entityManager = emf.createEntityManager();
        UserContextImpl userContext = new UserContextImpl(jobContext, entityManager);
        userHandler.execute(userContext);
        jobStaticManager.executeNext(jobContext, getManagerState());
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTING;
    }

    @Override
    public void start() {
        //do nothin
    }
}
