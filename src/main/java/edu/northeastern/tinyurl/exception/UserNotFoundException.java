package edu.northeastern.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2969843024088140974L;

    public UserNotFoundException(String message){
        super(message);
    }
}
