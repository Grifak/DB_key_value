package com.example.db_key_value.exception;

public class InvalidFileFormatException extends RuntimeException{
    public InvalidFileFormatException(String filename) {
        super(String.format("Invalid file format. Filename: %s", filename));
    }
}
