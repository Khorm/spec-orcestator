package com.petra.lib.state.variable.group;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.variable.loader.VariableLoader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Группа содержит одновременно загружаемые переменные.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VariableGroupImpl implements VariableGroup{

    /**
     * Список всех лоадеров, принадлежащих группе
     */
    List<VariableLoader> variablesLoaders;

    /**
     * Заполнена ли группа переменными
     * @param activityContext
     * @return
     */
    @Override
    public boolean isReady(ActivityContext activityContext) {
        return variablesLoaders.stream().anyMatch(variableLoader -> variableLoader.isReady(activityContext));
    }


    /**
     * Обработать лоадерами входящее собщение
     * @param activityContext
     */
    @Override
    public void execute(ActivityContext activityContext) {
        for (VariableLoader variableLoader : variablesLoaders){
            variableLoader.handle(activityContext);
        }
    }


}
