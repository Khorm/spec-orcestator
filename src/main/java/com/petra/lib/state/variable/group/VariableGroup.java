package com.petra.lib.state.variable.group;

import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.state.variable.group.loaders.VariableLoader;
import com.petra.lib.state.variable.group.repo.ContextRepo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
class VariableGroup {

    /**
     * Groups, which fill before current group and current group
     * depends on them
     */
    @Getter
    final Set<Long> parentGroups;

    /**
     *  Groups, depend on this group
     */
    @Getter
    final Set<Long> childGroups;

    /**
     * Group id
     */
    @Getter
    final Long groupId;

    /**
     * Threadsafe context repository
     */
    final ContextRepo contextRepo;

    /**
     * Variable loaders of current group
     */
    final Collection<VariableLoader> variableLoaders;

    /**
     * Current variable loaders ids
     */
    final Set<Long> fillingVariableIds = new HashSet<>();

    /**
     * Callback after loaders execute.
     */
    @Setter
    BiConsumer<Long, DirtyContext> loadHandler;

    /**
     * Callback for any error
     */
    @Setter
    Consumer<DirtyContext> errorHandler;

    VariableGroup(Set<Long> parentGroups, Long groupId, Set<Long> childGroups,
                  ContextRepo contextRepo, Collection<VariableLoader> variableLoaders) {
        this.parentGroups = parentGroups;
        this.childGroups = childGroups;
        this.groupId = groupId;
        this.contextRepo = contextRepo;

        this.variableLoaders = variableLoaders;
        for (VariableLoader variableLoader : variableLoaders){
            fillingVariableIds.addAll(variableLoader.getFilingProcessVariableIds());
        }
        for (VariableLoader variableLoader : variableLoaders){
            variableLoader.setFillHandler(this::variablesLoad);
            variableLoader.setErrorHandler(this::errorLoadHandler);
        }
    }

    /**
     * Fill group variables
     *
     * @param actionContext - process context
     */
    void fillGroup(DirtyContext actionContext) {
        variableLoaders.forEach(variableLoader -> variableLoader.load(actionContext));
    }

    /**
     * After source load
     *
     * @param filledContextVariableId - id of filled context variable
     * @param context                 - process context
     */
    private void variablesLoad(Collection<Long> filledContextVariableId, DirtyContext context) {
        Set<Long> allFilledVariables = contextRepo.setFilledVariables(filledContextVariableId, context.getScenarioId());
        if (fillingVariableIds.containsAll(allFilledVariables)) {
            loadHandler.accept(groupId, context);
        }
    }

    private void errorLoadHandler(DirtyContext actionContext) {
        errorHandler.accept(actionContext);
    }

}
