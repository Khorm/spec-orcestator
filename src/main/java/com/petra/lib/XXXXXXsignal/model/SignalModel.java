package com.petra.lib.XXXXXXsignal.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignalModel {
    Long signalId;
    Version version;
    String signalName;
    String serviceName;
    SignalType signalType;
    MessageType messageType;
}
