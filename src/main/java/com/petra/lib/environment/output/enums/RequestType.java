package com.petra.lib.environment.output.enums;

import lombok.Getter;

public enum RequestType {
    //запрос исполнения активности\воркфлоу
    REQUEST_ACTIVITY_EXECUTION(AnswerType.RESPONSE_ACTIVITY_EXECUTION),

    //запрос отката
    REQUEST_ROLLOUT(AnswerType.RESPONSE_ROLLOUT),

    //запрос инфы из соурса
    REQUEST_SOURCE(AnswerType.RESPONSE_SOURCE),

    //ошибка
    ERROR(null);

    @Getter
    private final AnswerType answerType;

    RequestType(AnswerType answerType) {
        this.answerType = answerType;
    }

}
