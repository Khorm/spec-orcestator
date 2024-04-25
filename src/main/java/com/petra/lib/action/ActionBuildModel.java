package com.petra.lib.action;

import com.petra.lib.variable.pure.PureVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ActionBuildModel {
    Long actionId;
    String actionName;
    Collection<PureVariable> actionVariables;
    Long callingSignalId;
}
