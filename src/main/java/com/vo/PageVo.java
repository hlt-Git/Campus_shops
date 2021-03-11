package com.vo;

public class PageVo<T> {
    private Integer status; //状态码
    private String message; //返回信息
    private Integer pages;  //返回页数
    private Integer dataNumber;//总记录数
    private T data;    //返回数据

    public PageVo(){
        super();
    }

    public PageVo(Integer status, String message, Integer pages, Integer dataNumber, T data) {
        this.status = status;
        this.message = message;
        this.pages = pages;
        this.dataNumber = dataNumber;
        this.data = data;
    }

    public PageVo(Integer status, String message,Integer dataNumber) {
        this.status = status;
        this.message = message;
        this.dataNumber = dataNumber;
    }

    public PageVo(Integer status, String message, Integer pages, T data) {
        this.status = status;
        this.message = message;
        this.pages = pages;
        this.data = data;
    }

    public PageVo(Integer status, Integer pages, T data) {
        this.status = status;
        this.pages = pages;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(Integer dataNumber) {
        this.dataNumber = dataNumber;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
