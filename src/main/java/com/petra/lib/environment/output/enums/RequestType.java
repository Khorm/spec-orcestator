package com.petra.lib.environment.output.enums;

public enum RequestType {
    //запрос исполнения активности\воркфлоу
    REQUEST_EXEC,

    //запрос отката
    REQUEST_ROLLOUT,

    //запрос инфы из соурса
    REQUEST_SOURCE,

    //исполнено
    EXECUTED,

    //откачено
    ROLLOUT,

    //соурс получен
    SOURCE,

    //ошибка
    ERROR
    }
