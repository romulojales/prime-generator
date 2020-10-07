package com.romulojales.prime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(int number) {
        super("The received number: " + number + " is not valid.");
    }

}
