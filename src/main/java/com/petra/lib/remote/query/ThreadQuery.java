package com.petra.lib.remote.query;

import com.petra.lib.remote.query.task.InputTask;

public interface ThreadQuery {
    /**
     *
     * @param inputTask
     * @return true ���� ������ ���� �������, false ���� ������ ���� ��������� ���� ��������� �������
     */
    boolean pop(InputTask inputTask);
}
