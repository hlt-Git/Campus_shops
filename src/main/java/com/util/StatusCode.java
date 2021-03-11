package com.util;

/**
 * @Author: hlt
 * @Description: 状态码工具
 * @Date: 2019/12/27 16:06
 */
public class StatusCode {

    public static final int SMS = 200;//发送成功
    public static final int OK = 200;//成功
    public static final int ERROR = 201;//失败
    public static final int LOGINERROR = 202;//用户名密码错误
    public static final int ACCESSERROR = 203;//权限不足
    public static final int REMOTEERROR = 204;//远程调用失败
    public static final int REPERROR = 205;//重复操作
    public static final int FINDERROR = 404;//找不到
    public static final int SERVERERROR = 500;//服务器错误

}
