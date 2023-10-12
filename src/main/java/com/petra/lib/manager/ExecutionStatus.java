package com.petra.lib.manager;

public enum ExecutionStatus {

    //обработка блока была начала
    STARTED,

    //блок был принят в разработку
    EXECUTING,

    //был был обработан и сохранен
    EXECUTED,

    //в блоке была получена и сохрарнена ошибка.
    ERROR
}
