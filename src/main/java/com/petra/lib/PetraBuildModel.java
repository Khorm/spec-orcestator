package com.petra.lib;

import com.petra.lib.action.ActionBuildModel;
import com.petra.lib.source.SourceBuildModel;
import com.petra.lib.workflow.WorkflowBuildModel;
import com.petra.lib.workflow.block.BlockBuilderModel;
import com.petra.lib.workflow.signal.SignalBuildModel;
import lombok.Getter;

import java.util.Collection;

@Getter
public class PetraBuildModel {
    private Collection<SourceBuildModel> sourceBuildModels;
    private Collection<ActionBuildModel> actionBuildModels;
    private Collection<BlockBuilderModel> blockBuilderModels;

    /**
     * Все сигналы которые обрабатывает сервис, в том числе входящие и исходящие
     */
    private Collection<SignalBuildModel> signalBuildModels;
    private  Collection<WorkflowBuildModel> workflowBuildModels;
}
