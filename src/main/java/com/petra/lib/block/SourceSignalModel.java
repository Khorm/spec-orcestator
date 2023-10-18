package com.petra.lib.block;

import com.petra.lib.XXXXXXsignal.SignalModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@Deprecated
public class SourceSignalModel extends SignalModel {
    Collection<Long> requestSignalIds;
    Collection<Long> parentIds = new ArrayList<>();
    Collection<Long> childIds = new ArrayList<>();
}
