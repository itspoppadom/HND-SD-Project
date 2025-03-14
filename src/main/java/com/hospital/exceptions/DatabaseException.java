package com.hospital.exceptions;

public class DatabaseException extends Exception {

    // Constructor to retrive the message
    public DatabaseException(String message) {
        super(message);
    }
    // Constructor to retrive the message and the cause
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}