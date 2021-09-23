package com.example.db_key_value.exception;


public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long id) {
        super(String.format("Entity with id %d not found", id));
    }
}
