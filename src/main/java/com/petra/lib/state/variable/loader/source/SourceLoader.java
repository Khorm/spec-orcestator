package com.petra.lib.state.variable.loader.source;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.remote.output.http.SignalManager;
import com.petra.lib.state.variable.loader.VariableLoader;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Загружает переменные из соурсов
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceLoader implements VariableLoader {
    /**
     * Переменные блока, значения которых загружает лоадер
     */
    Collection<Long> loaderValIds;
    SignalManager signalManager;
    VariableMapper fromSignalToContextMapper;


    /**
     * Обработчик выполняет либо функцию запроса либо ответа
     * @param activityContext
     */
    @Override
    public void handle(ActivityContext activityContext) {
        if (isReady(activityContext)) return;

        switch (activityContext.getSignalType()) {
            case REQUEST_ACTIVITY_EXECUTION:
                requestData(activityContext);
                break;
            case RESPONSE_SOURCE:
                setAnswer(activityContext);
                break;
        }
    }

    /**
     * Проверяет все ли переменные группы заполнены
     * @param activityContext
     * @return
     */
    @Override
    public boolean isReady(ActivityContext activityContext) {
        return loaderValIds.stream()
                .allMatch(variableId -> activityContext.getValueById(variableId).isPresent());

    }
    private void requestData(ActivityContext activityContext){
        signalManager.send(activityContext);
    }

    private void setAnswer(ActivityContext activityContext){
        VariablesContainer variablesContainer = fromSignalToContextMapper.map(activityContext.getRequestSignalVariables());
        activityContext.addVariables(variablesContainer);
    }

}
