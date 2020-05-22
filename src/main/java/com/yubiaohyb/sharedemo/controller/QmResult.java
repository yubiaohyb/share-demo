package com.yubiaohyb.sharedemo.controller;

import lombok.Data;

import java.io.Serializable;

@Data
public class QmResult<T> implements Serializable {
    private Integer code;
    private String message;
    private String flag;
    private String errorCode;
    private String errorMessage;
    private T data;
}
