package com.petra.lib.source;

import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.response.source.SourceResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.source.answer.SourceAnswerExecutor;
import com.petra.lib.source.user.SourceUserExecutor;
import com.petra.lib.source.user.UserSourceHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.pure.PureVariableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class SourceFactory {

    private static Source createSource(SourceBuildModel sourceBuildModel, TransactionManager transactionManager,
                                       UserSourceHandler userSourceHandler,
                                       ThreadQuery threadQuery,
                                       SourceResponse sourceResponse,
                                       RemoteThreadManager remoteThreadManager){
        SourceUserExecutor sourceUserExecutor = new SourceUserExecutor(transactionManager,
                userSourceHandler, new PureVariableList(sourceBuildModel.getSourceVariables()));
        SourceAnswerExecutor sourceAnswerExecutor = new SourceAnswerExecutor(sourceResponse,
                 remoteThreadManager);

        return new Source(sourceBuildModel.getSourceId(),
                sourceBuildModel.getName(),
                sourceUserExecutor,
                threadQuery,
                sourceAnswerExecutor);
    }


    public static Collection<Source> createSources(Collection<SourceBuildModel> sourceBuildModels,
                                                   Map<String, UserSourceHandler> userSourceHandlerMap,
                                                   ThreadQuery threadQuery,
                                                   SourceResponse sourceResponse,RemoteThreadManager remoteThreadManager,
                                                   TransactionManager transactionManager){
        Collection<Source> sources = new ArrayList<>();
        for (SourceBuildModel sourceBuildModel : sourceBuildModels){
            sources.add(createSource(sourceBuildModel,
                    transactionManager,
                    userSourceHandlerMap.get(sourceBuildModel.getName()),
                    threadQuery,
                    sourceResponse,
                    remoteThreadManager));
        }

        return sources;

    }
}
