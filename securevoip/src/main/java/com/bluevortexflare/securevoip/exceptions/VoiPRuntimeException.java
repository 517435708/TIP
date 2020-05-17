package com.bluevortexflare.securevoip.exceptions;


import lombok.Getter;

@Getter
public class VoiPRuntimeException extends RuntimeException {

    private int httpErrorCode;

    public VoiPRuntimeException(int httpErrorCode) {
        super();
        this.httpErrorCode = httpErrorCode;
    }

}
