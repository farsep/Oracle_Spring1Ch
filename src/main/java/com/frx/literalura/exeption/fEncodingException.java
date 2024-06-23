package com.frx.literalura.exeption;

public class fEncodingException extends Exception {
    private String message;
    public fEncodingException(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
