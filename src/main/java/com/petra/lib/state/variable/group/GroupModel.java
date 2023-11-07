package com.petra.lib.state.variable.group;

import com.petra.lib.XXXXXXsignal.SignalId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GroupModel {
    Long groupNumber;
    SignalId signalId;
    List<Long> groupVariableIds;
}
