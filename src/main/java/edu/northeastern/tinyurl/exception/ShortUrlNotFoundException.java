package edu.northeastern.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortUrlNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 544024681772054289L;

    public ShortUrlNotFoundException(String message){
        super(message);
    }
}
