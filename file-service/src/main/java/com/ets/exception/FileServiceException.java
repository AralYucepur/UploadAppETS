package com.ets.exception;

import lombok.Getter;

@Getter
public class FileServiceException extends RuntimeException{

    private final ErrorType errorType;

    public FileServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
