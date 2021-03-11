package com.util;

/**
 * 总页数公式：dataNumber是总记录数，pageSize是一页分多少条记录
 * pages = (dataNumber + pageSize - 1) / pageSize;
 */

public class PageLength {
    public static Integer getPages(Integer dataNumber,Integer pageSize){
        return (dataNumber + pageSize - 1) / pageSize;
    }
}
