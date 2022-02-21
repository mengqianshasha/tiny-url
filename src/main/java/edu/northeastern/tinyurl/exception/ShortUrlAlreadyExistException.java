package edu.northeastern.tinyurl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class ShortUrlAlreadyExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -6020105101600949814L;

    public ShortUrlAlreadyExistException(String message){
        super(message);
    }
}
