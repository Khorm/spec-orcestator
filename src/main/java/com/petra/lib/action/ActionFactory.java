package com.petra.lib.action;

import com.petra.lib.action.answer.ActionAnswerExecutor;
import com.petra.lib.action.repo.ActionRepo;
import com.petra.lib.action.user.UserExecutor;
import com.petra.lib.action.user.handler.UserActionHandler;
import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.response.block.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.pure.PureVariableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class ActionFactory {

    private static Action createAction(ActionBuildModel actionBuildModel, ActionRepo actionRepo, ThreadQuery threadQuery,
                                      TransactionManager transactionManager, UserActionHandler userActionHandler,
                                      BlockResponse blockResponse, RemoteThreadManager remoteThreadManager){
        PureVariableList actionVariableList = new PureVariableList(actionBuildModel.getActionVariables());
        UserExecutor userExecutor  = new UserExecutor(transactionManager, userActionHandler, actionRepo, actionVariableList);
        ActionAnswerExecutor actionAnswerExecutor = new ActionAnswerExecutor(actionBuildModel.getCallingSignalId(),
                blockResponse, remoteThreadManager, actionRepo);
        return new Action(actionBuildModel.getActionId(),
                actionRepo,
                threadQuery,
                userExecutor,
                actionAnswerExecutor);

    }

    public static Collection<Action> createActions(Collection<ActionBuildModel> actionBuildModels,
                                                   Map<String, UserActionHandler> handlerMap,
                                                   ActionRepo actionRepo, ThreadQuery threadQuery,
                                                   TransactionManager transactionManager,
                                                   BlockResponse blockResponse, RemoteThreadManager remoteThreadManager){
        Collection<Action> actions = new ArrayList<>();
        for (ActionBuildModel actionBuildModel : actionBuildModels){
            actions.add(createAction(actionBuildModel,
                    actionRepo,
                    threadQuery,
                    transactionManager,
                    handlerMap.get(actionBuildModel.getActionName()),
                    blockResponse,
                    remoteThreadManager));
        }
        return actions;
    }
}
