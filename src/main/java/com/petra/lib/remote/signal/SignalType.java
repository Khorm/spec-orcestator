package com.petra.lib.remote.signal;

public enum SignalType {
    //������ ���������� ����������\��������
    REQUEST_ACTIVITY_EXECUTION(true),

    //������ ������
    REQUEST_ROLLOUT(true),

    //������ ���� �� ������
    REQUEST_SOURCE(true),

    //������
    ERROR(false),

    //���������� ���������� ���������
    RESPONSE_ACTIVITY_EXECUTED(false),

    //����� ��������
    RESPONSE_ROLLOUT(false),

    //����� �� ������ ������
    RESPONSE_SOURCE(false)
    ;
    private boolean isRequestSignal;

    SignalType(boolean isRequestSignal){
        this.isRequestSignal = isRequestSignal;
    }

    public boolean isRequestSignal(){
        return isRequestSignal;
    }





}
