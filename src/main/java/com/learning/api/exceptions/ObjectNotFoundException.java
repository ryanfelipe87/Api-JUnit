package com.learning.api.exceptions;

import java.util.function.Supplier;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message){
        super(message);
    }

}
