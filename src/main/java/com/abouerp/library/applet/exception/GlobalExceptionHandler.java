package com.abouerp.library.applet.exception;

import com.abouerp.library.applet.bean.ResultBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Abouerp
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static ResponseEntity<ResultBean<Object>> handleException(ClientErrorException exception) {
        return ResponseEntity.status(exception.getCode())
                .body(exception.getResultBean());
    }

    private static ResponseEntity<ResultBean<String>> handleException(ResultBean<String> resultBean) {
        return ResponseEntity.status(resultBean.getCode())
                .body(resultBean);
    }

    @ExceptionHandler(ClientErrorException.class)
    public HttpEntity<ResultBean<Object>> clientErrorException(ClientErrorException ex) {
        return handleException(ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object httpRequestMethodNotSupportedException() {
        return handleException(ResultBean.of(405, "Method Not Allowed"));
    }

}