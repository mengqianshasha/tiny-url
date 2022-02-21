package edu.northeastern.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ShortUrlNotBelongToCurrentUserException extends RuntimeException{
    public ShortUrlNotBelongToCurrentUserException(String message){
        super(message);
    }
}
