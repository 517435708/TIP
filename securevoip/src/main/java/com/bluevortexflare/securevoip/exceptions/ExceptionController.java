package com.bluevortexflare.securevoip.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(VoiPRuntimeException.class)
    ResponseEntity<String> handleException(VoiPRuntimeException ex) {
        return ResponseEntity.status(ex.getHttpErrorCode()).body(ex.toString());
    }

}
