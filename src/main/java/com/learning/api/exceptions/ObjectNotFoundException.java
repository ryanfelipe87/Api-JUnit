package com.learning.api.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message){
        super(message);
    }

}
