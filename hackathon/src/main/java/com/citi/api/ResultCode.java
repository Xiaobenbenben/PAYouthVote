package com.citi.api;

/**
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "success"),
    FAILED(500, "fail"),
    VALIDATE_FAILED(404, "params validation fail"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "no permission");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
