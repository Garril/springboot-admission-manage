package com.admit.admit.util;


public class ApiResult<T> {

    private int code; // 状态码

    private String message; // 请求信息

    private T data; // 数据结果

    public ApiResult() {
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    // get
    public int getCode() {
        return code;
    }
    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
    // set
    public void setCode(int code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setData(T data) {
        this.data = data;
    }
    public static <T> ApiResult buildApiResult(Integer code, String message, T data) {
        ApiResult apiResult = new ApiResult();

        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }
}
