package com.petra.lib.query;

public interface ThreadQuery {
    /**
     *
     * @param inputTask
     * @return true ���� ������ ���� �������, false ���� ������ ���� ��������� ���� ��������� �������
     */
    boolean forcedPop(InputTask inputTask);

    /**
     * ��������� ������ � �������
     * @param inputTask - ������
     */
    void popInQueue(InputTask inputTask);

//    void popInQueueAndWait(InputTask inputTask);
}
