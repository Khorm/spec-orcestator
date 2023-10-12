package com.petra.lib.z_user_package.source_handlers;

import com.petra.lib.annotation.WorkflowHandler;
import com.petra.lib.context.UserContext;
import com.petra.lib.state.handler.UserHandler;
import org.springframework.stereotype.Service;

@WorkflowHandler(name = "test_source_1")
@Service
public class TestHandler implements UserHandler {
    @Override
    public void execute(UserContext userContext) {
        String value = userContext.getExecutionVariable("fst_variable", String.class);
        System.out.println("Hello world " + value);
        TestEntity testEntity = new TestEntity();
        testEntity.setMessage(value);
        userContext.getEntityManager().persist(testEntity);
        userContext.putExecutionVariable("scd_variable", "test");
    }
}
