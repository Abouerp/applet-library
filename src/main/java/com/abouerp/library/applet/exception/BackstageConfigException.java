package com.abouerp.library.applet.exception;

/**
 * @author Abouerp
 */
public class BackstageConfigException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "BackstageConfig Error";

    public BackstageConfigException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
