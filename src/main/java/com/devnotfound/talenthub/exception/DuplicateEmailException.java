package com.devnotfound.talenthub.exception;

public class DuplicateEmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public DuplicateEmailException(String message) {
        super(message);
    }
}
