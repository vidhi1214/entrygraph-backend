package com.entrygraph.backend.exception;

public class DestinationNotFoundException extends RuntimeException {

    public DestinationNotFoundException(String message) {
        super(message);
    }
}