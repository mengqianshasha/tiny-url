package edu.northeastern.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ShortUrlAlreadyExistException extends RuntimeException{
    public ShortUrlAlreadyExistException(String message){
        super(message);
    }
}
