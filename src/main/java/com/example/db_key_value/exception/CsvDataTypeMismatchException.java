package com.example.db_key_value.exception;

public class CsvDataTypeMismatchException extends RuntimeException{
    public CsvDataTypeMismatchException() {
        super("Data type mismatch");
    }
}
