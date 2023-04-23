package com.petra.lib.z_user_package.source_handlers;

import com.petra.lib.annotation.SourceHandler;
import com.petra.lib.handler.UserContext;
import com.petra.lib.handler.UserHandler;
import org.springframework.stereotype.Service;

@SourceHandler(name = "test_handler")
@Service
public class TestHandler implements UserHandler {
    @Override
    public void execute(UserContext userContext) {
        System.out.println("Hello world");
    }
}
