package com.petra.lib.environment.output.enums;

import lombok.Getter;

public enum RequestType {
    //������ ���������� ����������\��������
    REQUEST_ACTIVITY_EXECUTION(AnswerType.RESPONSE_ACTIVITY_EXECUTION),

    //������ ������
    REQUEST_ROLLOUT(AnswerType.RESPONSE_ROLLOUT),

    //������ ���� �� ������
    REQUEST_SOURCE(AnswerType.RESPONSE_SOURCE),

    //������
    ERROR(null);

    @Getter
    private final AnswerType answerType;

    RequestType(AnswerType answerType) {
        this.answerType = answerType;
    }

}
