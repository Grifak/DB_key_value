package com.example.db_key_value.exception;

public class RequiredFieldIsEmptyException extends RuntimeException{
    public RequiredFieldIsEmptyException(Long key) {
        super("Field 'key' cant be empty");
    }

    public RequiredFieldIsEmptyException(String value) {
        super("Field 'value' cant be empty");
    }
}
