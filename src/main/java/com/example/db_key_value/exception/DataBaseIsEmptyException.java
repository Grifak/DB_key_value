package com.example.db_key_value.exception;

public class DataBaseIsEmptyException extends RuntimeException{
    public DataBaseIsEmptyException() {
        super("DataBase is empty");
    }
}
