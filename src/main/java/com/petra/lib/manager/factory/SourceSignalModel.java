package com.petra.lib.manager.factory;

import com.petra.lib.signal.SignalModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class SourceSignalModel extends SignalModel {
    Collection<Long> parentIds;
    Collection<Long> childIds;
}
