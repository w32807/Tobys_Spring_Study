package com.toby.spring.dao;

public class DuplicateUserIdException extends Exception {
    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
