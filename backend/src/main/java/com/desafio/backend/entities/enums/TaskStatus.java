package com.desafio.backend.entities.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {

    PENDING(1),
    CONCLUDED(2);

    private final int code;

    TaskStatus(int code) {
        this.code = code;
    }

    public static TaskStatus valueOf(int code) {
        for (TaskStatus value : TaskStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus code: " + code);
    }
}
