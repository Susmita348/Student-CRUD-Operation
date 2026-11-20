package com.student.CRUDoperation.exception;

public class ResourceNotFoundExceptionHandler extends RuntimeException {

    public ResourceNotFoundExceptionHandler(String message){
        super(message);
    }
}
