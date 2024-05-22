package com.desafio.backend.entities.exceptions;

public class TitleLengthException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TitleLengthException(String msg) {
        super(msg);
    }
}
