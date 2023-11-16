package com.petra.lib.remote.signal;

public enum SignalType {
    //запрос исполнения активности\воркфлоу
    REQUEST_ACTIVITY_EXECUTION(true),

    //запрос отката
    REQUEST_ROLLOUT(true),

    //запрос инфы из соурса
    REQUEST_SOURCE(true),

    //ошибка
    ERROR(false),

    //выполнение активности исполнено
    RESPONSE_ACTIVITY_EXECUTED(false),

    //откат исполнен
    RESPONSE_ROLLOUT(false),

    //ответ от соурса принят
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
