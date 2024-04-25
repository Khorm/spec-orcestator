package com.petra.lib.workflow.block.executor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class BlockExecutorBuildModel {
    String producerServiceName;
    String consumerServiceName;
}
