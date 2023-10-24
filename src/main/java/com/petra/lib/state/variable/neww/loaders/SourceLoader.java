package com.petra.lib.state.variable.neww.loaders;

import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.output.OutputSocket;
import com.petra.lib.environment.output.enums.RequestType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Отправляет запрос на соурсы
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SourceLoader implements VariableLoader {
    OutputSocket outputSocket;
    Integer groupNumber;

    @Override
    public void load(ActivityContext activityContext) {
        outputSocket.consume(activityContext, RequestType.SOURCE_REQUEST);
    }

    @Override
    public Integer groupNumber() {
        return groupNumber;
    }
}
