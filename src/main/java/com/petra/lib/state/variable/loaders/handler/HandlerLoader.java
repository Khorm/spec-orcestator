package com.petra.lib.state.variable.loaders.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.value.ProcessValue;
import com.petra.lib.state.variable.loaders.VariableLoader;
import com.petra.lib.variable.pure.PureVariable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Выгружает все переменные из пользовательского хендлера
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HandlerLoader implements VariableLoader {

    UserVariableHandler userVariableHandler;
    PureVariable loadingVariable;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(ActivityContext activityContext) {
        //защищает метод от повторных вызовов
        if (isReady(activityContext)) return;

        //обработка польозвательского хендлера
        Object userObject = userVariableHandler.map(new VariableUserContextImpl(activityContext));
        try {
            ProcessValue processValue = new ProcessValue(loadingVariable.getId(), loadingVariable.getName(),
                    objectMapper.writeValueAsString(userObject));
            activityContext.setValue(processValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReady(ActivityContext activityContext) {
        return activityContext.getValueById(loadingVariable.getId()).isPresent();
    }

}
