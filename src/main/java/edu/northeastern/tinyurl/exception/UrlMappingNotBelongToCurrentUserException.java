package edu.northeastern.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UrlMappingNotBelongToCurrentUserException extends RuntimeException{
    public UrlMappingNotBelongToCurrentUserException(String message){
        super(message);
    }
}
