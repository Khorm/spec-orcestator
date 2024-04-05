package com.petra.lib.z_user_package.source_handlers;

import com.petra.lib.annotation.WorkflowHandler;
import com.petra.lib.workflow.variable.loader.handler.VariableUserContext;
import com.petra.lib.action.new_action.user.handler.UserActionHandler;
import org.springframework.stereotype.Service;

@WorkflowHandler(name = "test_action_1")
@Service
public class ActionActionHandler implements UserActionHandler {
    @Override
    public void execute(VariableUserContext variableUserContext) {
        System.out.println("EXECUTION test_action_1");
    }
}
