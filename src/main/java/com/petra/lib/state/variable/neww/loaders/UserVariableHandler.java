package com.petra.lib.state.variable.neww.loaders;

import com.petra.lib.state.variable.neww.loaders.user.UserContext;

/**
 * внутри может заполнятся ннесколько переменных, хоть все сразу.
 */
public interface UserVariableHandler {
    void map(UserContext userContext);
}
