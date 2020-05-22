package com.yubiaohyb.sharedemo.controller;

/**
 * @author zy
 * @describe 统一返回结果
 */
public class QmResults {

    public static <T> QmResult<T> newSuccessResult(T data) {
        QmResult<T> result = new QmResult();
        result.setErrorCode("0");
        result.setErrorMessage("");
        result.setFlag("false");
        result.setData(data);
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static <T> QmResult<T> newFailResult(String code,String message) {
        QmResult<T> result = new QmResult<>();
        result.setErrorCode(code);
        result.setErrorMessage(message);
        result.setFlag("true");
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
