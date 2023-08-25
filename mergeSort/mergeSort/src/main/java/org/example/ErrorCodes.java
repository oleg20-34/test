package org.example;

public enum ErrorCodes {

    WRONG_LEFT_ARG(-3),
    WRONG_RIGHT_ARG(-5);

    int code;

    ErrorCodes(int code){
        this.code = code;
    }
}
