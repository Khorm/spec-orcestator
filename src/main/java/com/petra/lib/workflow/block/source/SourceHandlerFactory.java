package com.petra.lib.workflow.block.source;

import com.petra.lib.remote.request.source.SourceRequest;
import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.workflow.block.source.build.LoadGroupBuildModel;
import com.petra.lib.workflow.block.source.build.SourceHandlerBuildModel;
import com.petra.lib.workflow.block.source.build.SourceLoaderBuildModel;
import com.petra.lib.workflow.repo.WorkflowActionRepo;

import java.util.ArrayList;
import java.util.Collection;

public final class SourceHandlerFactory {

    public static SourceHandler createActionSource(SourceHandlerBuildModel sourceHandlerBuildModel,
                                            SourceRequest sourceRequest,
                                            PureVariableList blockPureVariableList,
                                            WorkflowActionRepo workflowActionRepo){
        Collection<LoadGroup> loadGroups = new ArrayList<>();
        for (LoadGroupBuildModel loadGroupBuildModel : sourceHandlerBuildModel.getLoadGroupBuildModels()){
            Collection<SourceLoader> sourceLoaders = new ArrayList<>();
            for (SourceLoaderBuildModel sourceLoaderBuildModel: loadGroupBuildModel.getSourceLoaderBuildModelCollection()){
                SourceLoader sourceLoader = new SourceLoader(sourceRequest,
                        sourceLoaderBuildModel.getRequestingSourceId(),
                        new PureVariableList(sourceLoaderBuildModel.getSourceVariables()),
                        sourceLoaderBuildModel.getRequestingSourceServiceName());
                sourceLoaders.add(sourceLoader);
            }

            LoadGroup loadGroup = new LoadGroup(loadGroupBuildModel.getGroupNumber(),
                    sourceLoaders,
                    blockPureVariableList,
                    workflowActionRepo);
            loadGroups.add(loadGroup);
        }

        return new ActionSourceHandler(loadGroups);
    }

    public static SourceHandler createWorkflowSource(){
        return new WorkflowSourceHandler();
    }
}
