package com.desafio.backend.services.exceptions;

public class TooManyPendingTasksException  extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TooManyPendingTasksException(String msg) {
        super(msg);
    }
}