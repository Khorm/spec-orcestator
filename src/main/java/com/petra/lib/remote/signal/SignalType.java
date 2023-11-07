package com.petra.lib.remote.signal;

public enum SignalType {
    //запрос исполнения активности\воркфлоу
    REQUEST_ACTIVITY_EXECUTION,

    //запрос отката
    REQUEST_ROLLOUT,

    //запрос инфы из соурса
    REQUEST_SOURCE,

    //ошибка
    ERROR,

    //выполнение активности исполнено
    RESPONSE_ACTIVITY_EXECUTED,

    //откат исполнен
    RESPONSE_ROLLOUT,

    //ответ от соурса принят
    RESPONSE_SOURCE,



}
