package com.petra.lib.worker.variable.group;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.worker.variable.group.loaders.VariableLoader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
class VariableGroup {

    @Getter
    final Set<Long> parentGroups;

    @Getter
    final Set<Long> childGroups;

    @Getter
    final Long groupId;

    /**
     * Loads a source for a group if required
     */
    final Optional<SourceRequestManager> sourceLoader;

    final Collection<VariableLoader> variableLoaders;

    @Setter
    BiConsumer<Long, JobContext> loadHandler;

    @Setter
    BiConsumer<Exception, JobContext> errorHandler;

    VariableGroup(Set<Long> parentGroups, Long groupId, Set<Long> childGroups,
                  SourceRequestManager loader, Collection<VariableLoader> variableLoaders) {
        this.parentGroups = parentGroups;
        this.childGroups = childGroups;
        this.groupId = groupId;
        this.sourceLoader = Optional.ofNullable(loader);
        if (loader != null) {
            loader.setLoadHandler(this::variablesLoad);
            loader.setErrorHandler(this::errorLoadHandler);
        }
        this.variableLoaders = variableLoaders;
//        this.groupHandler = groupHandler;
//        groupHandler.setErrorHandler(this::errorLoadHandler);
    }

    /**
     * Fill group variables
     * @param jobContext
     */
    void fillGroup(JobContext jobContext) {
        if (sourceLoader.isPresent()){
            sourceLoader.get().load(jobContext);
        }else {
            variablesLoad(null, jobContext);
        }
    }

    /**
     * After source load
     * @param sourceVariables
     * @param jobContext
     */
    private void variablesLoad(Collection<ProcessVariableDto> sourceVariables, JobContext jobContext) {
//        Collection<ProcessVariableDto> mappedVariables = fromSourceMapper.map(loadedVariables);
//        mappedVariables.forEach(jobContext::setVariable);
//        groupHandler.handle(jobContext);
        variableLoaders.forEach(variableLoader -> variableLoader.load(sourceVariables, jobContext));
        loadHandler.accept(groupId, jobContext);
    }

    private void errorLoadHandler(Exception e, JobContext jobContext) {
        errorHandler.accept(e, jobContext);
    }


}
