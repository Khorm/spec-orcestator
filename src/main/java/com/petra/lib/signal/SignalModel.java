package com.petra.lib.signal;

import com.petra.lib.signal.model.Version;
import com.petra.lib.variable.factory.VariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class SignalModel {
    Long id;
    Version version;
    String name;
    Collection<VariableModel> requestVariableCollection;
    String path;
}
