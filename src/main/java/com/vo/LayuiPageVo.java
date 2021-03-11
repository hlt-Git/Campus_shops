package com.vo;

public class LayuiPageVo<T> {
    private String msg;
    private Integer code;
    private Integer count;
    private T data;

    public LayuiPageVo(String msg, Integer code, Integer count, T data) {
        this.msg = msg;
        this.code = code;
        this.count = count;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
