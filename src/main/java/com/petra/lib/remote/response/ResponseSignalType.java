package com.petra.lib.remote.response;

public enum ResponseSignalType {

    /**
     * Активностью был принят запрос на исполнение
     */
    ACTION_REQUEST_ACCEPTED,

    /**
     * Запрос уже был принят и исполнен
     */
    ACTION_REQUEST_REPEAT,

    /**
     * Запрос в процессе исполнения
     */
    ACTION_EXECUTING,

    /**
     * Запрос не может быть принят из-за перегрузки
     */
    ACTION_OVERLOAD,

    /**
     * Ошибка при обработке запроса
     */
    ACTION_ERROR,

    /**
     * Запрос исполнен
     */
    ACTION_COMPLETE,

//    SOURCE_ERROR,
//    SOURCE_RESPONSE,
}
