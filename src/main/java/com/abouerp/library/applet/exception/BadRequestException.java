package com.abouerp.library.applet.exception;

/**
 * @author Abouerp
 */
public class BadRequestException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Bad Request";

    public BadRequestException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
