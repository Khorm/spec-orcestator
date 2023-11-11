package com.petra.lib.state.initialize;

import com.petra.lib.block.Block;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.ActionState;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;


/**
 * ��������� ���������� �� ��������� �������.
 */
@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Initializer implements StateHandler {
    /**
     * ������ � ���������� ���������� ��
     */
    VariableMapper initSignalToActionVariableMapper;

    /**
     * ���� ���������� �����������
     */
    Block blockManager;

    /**
     * �������� ����������
     */
    TransactionManager transactionManager;

    @Override
    public ActionState getState() {
        return ActionState.INITIALIZING;
    }

    @Override
    public void execute(ActivityContext context) throws Exception {
        log.debug("Start initialization {}", blockManager.getName());

        //�������� ���������� �� ��������� �������
        VariablesContainer variablesFromSignal
                = initSignalToActionVariableMapper.map(context.getRequestSignalVariables());


        //�������� ���������� ������ � ���������� ���������� � ����.
        transactionManager.executeInTransaction(jpaTransactionManager -> {
            context.setNewState(ActionState.INITIALIZING);
            context.addVariables(variablesFromSignal);
        });
        blockManager.execute(context);
    }

    @Override
    public void start() {

    }
}
