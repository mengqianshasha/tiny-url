package edu.northeastern.tinyurl.exception;

import java.io.Serial;

public class ShortUrlExpiredException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3250761285194285110L;

    public ShortUrlExpiredException(String message){
        super(message);
    }
}
