package com.petra.lib.z_user_package.source_handlers;

import com.petra.lib.annotation.WorkflowHandler;
import com.petra.lib.state.variable.neww.loaders.user.UserContext;
import com.petra.lib.state.handler.UserHandler;
import org.springframework.stereotype.Service;

@WorkflowHandler(name = "test_action_1")
@Service
public class ActionHandler implements UserHandler {
    @Override
    public void execute(UserContext userContext) {
        System.out.println("EXECUTION test_action_1");
    }
}
