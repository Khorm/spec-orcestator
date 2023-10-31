package com.petra.lib.environment.input.impl;

import com.petra.lib.block.Block;
import com.petra.lib.block.VersionBlockId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.input.WorkEnvironment;
import com.petra.lib.environment.output.enums.SignalResult;
import com.petra.lib.environment.query.ThreadQuery;
import com.petra.lib.environment.query.task.ActionInputTask;
import com.petra.lib.context.repo.ActionRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;

/**
 * ���������� ��������. �������������� ������ � ���������� � �����.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class WorkEnvironmentImpl implements WorkEnvironment {


    ActionRepo actionRepo;

    /**
     * ������ ���� ������
     */
    Map<VersionBlockId, Block> blocks;

    /**
     * ������� ������� ��� ������
     */
    ThreadQuery threadQuery;

    String currentServiceName;
    Long currentServiceId;


    @Override
    public AnswerDto consume(Signal inputSignal) {
        try {
            //���� �������� ������ ������
            Block blockForAction = blocks.get(inputSignal.getConsumerBlockId());
            Optional<ActivityContext> actionOpt =
                    actionRepo.getActionContext(inputSignal.getScenarioId(), inputSignal.getConsumerBlockId());

            //���� ������ ������ �� �������� � ���������� ��������������
            if (actionOpt.isEmpty()) {
                createInitTask(blockForAction, inputSignal);
            } else {
                createContinueSignal(actionOpt.get(), blockForAction, inputSignal);
            }
            return new AnswerDto(SignalResult.OK);
        } catch (Exception e) {
            return new AnswerDto(SignalResult.ERROR);
        }
    }

    /**
     * ��������� ���� �� ����������
     * @param actionBlock
     * @param inputSignal
     */
    private void createInitTask(Block actionBlock, Signal inputSignal) {
        if (actionBlock == null) throw new IllegalArgumentException("Block not found");

        ActivityContext newActivityContext = actionRepo.createNewActionContext(inputSignal, actionBlock, currentServiceName, currentServiceId);

        ActionInputTask initTask = new ActionInputTask(actionBlock,
                newActivityContext,
                inputSignal.getVariablesContext());
        threadQuery.pop(initTask);
    }


    private void createContinueSignal(ActivityContext loadActivityContext, Block blockForAction, Signal inputSignal) {
        threadQuery.pop(new ActionInputTask(
                blockForAction,
                loadActivityContext,
                inputSignal.getVariablesContext()
        ));
    }
}
