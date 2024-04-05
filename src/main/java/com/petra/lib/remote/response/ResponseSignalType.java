package com.petra.lib.remote.response;

public enum ResponseSignalType {

    /**
     * ����������� ��� ������ ������ �� ����������
     */
    ACTION_REQUEST_ACCEPTED,

    /**
     * ������ ��� ��� ������ � ��������
     */
    ACTION_REQUEST_REPEAT,

    /**
     * ������ � �������� ����������
     */
    ACTION_EXECUTING,

    /**
     * ������ �� ����� ���� ������ ��-�� ����������
     */
    ACTION_OVERLOAD,

    /**
     * ������ ��� ��������� �������
     */
    ACTION_ERROR,

    /**
     * ������ ��������
     */
    ACTION_COMPLETE,

//    SOURCE_ERROR,
//    SOURCE_RESPONSE,
}
