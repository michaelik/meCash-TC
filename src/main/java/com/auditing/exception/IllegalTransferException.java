package com.auditing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalTransferException extends RuntimeException {
    public IllegalTransferException(String message) {
        super(message);
    }
}
