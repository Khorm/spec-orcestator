package com.petra.lib.state.variable.neww.group;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.variable.neww.loaders.VariableLoader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * группа содержит одновременно зпгружающие параметры.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VariableGroupImpl implements VariableGroup{

    List<VariableLoader> variablesLoaders;
    List<Long> groupVariableIds;

    @Override
    public boolean isReady(ActivityContext activityContext) {
        for (Long valueId: groupVariableIds){
            if (!activityContext.getVariablesContext().getValueById(valueId).isLoaded() ||
                    activityContext.getVariablesContext().getValueById(valueId) == null){
                return false;
            }
        }
        return true;
    }


    @Override
    public void fill(ActivityContext activityContext) {
        for (VariableLoader variableLoader : variablesLoaders){
            variableLoader.load(activityContext);
        }
    }


}
