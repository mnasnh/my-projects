package com.myprojects.exception;

/**
 * Custom exception for cookie log parsing
 */
public class LogParsingException extends Exception {
    public LogParsingException(Throwable error) {
        super(error);
    }

    public LogParsingException(String errorMessage) {
        super(errorMessage);
    }
}
