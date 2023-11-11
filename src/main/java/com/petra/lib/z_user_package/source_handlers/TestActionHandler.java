package com.petra.lib.z_user_package.source_handlers;

import com.petra.lib.annotation.WorkflowHandler;
import com.petra.lib.state.variable.loaders.handler.VariableUserContext;
import com.petra.lib.state.user.handler.UserActionHandler;
import org.springframework.stereotype.Service;

@WorkflowHandler(name = "test_source_1")
@Service
public class TestActionHandler implements UserActionHandler {
    @Override
    public void execute(VariableUserContext variableUserContext) {
        String value = variableUserContext.getExecutionVariable("fst_variable", String.class);
        System.out.println("Hello world " + value);
        TestEntity testEntity = new TestEntity();
        testEntity.setMessage(value);
        variableUserContext.getEntityManager().persist(testEntity);
        variableUserContext.putExecutionVariable("scd_variable", "test");
    }
}
