package com.example.db_key_value.exception;

public class FileEmptyException extends RuntimeException{
    public FileEmptyException(String file) {
        super(String.format("File '%s' is empty", file));
    }
}
