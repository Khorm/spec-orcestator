package com.petra.lib.XXXXXXsignal;

import com.petra.lib.XXXXXXsignal.model.Version;
import com.petra.lib.variable.factory.VariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@Deprecated
public class SignalModel {
    Long id;
    Version version;
    String name;
    String serviceName;
    Collection<VariableModel> variableCollection;
    String path;
}
