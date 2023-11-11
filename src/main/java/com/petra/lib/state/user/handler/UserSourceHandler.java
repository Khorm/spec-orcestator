package com.petra.lib.state.user.handler;

import com.petra.lib.state.user.UserSourceContext;

public interface UserSourceHandler {
    void execute(UserSourceContext context);
}
