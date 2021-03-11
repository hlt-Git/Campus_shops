package com.util;

import java.util.regex.Pattern;

/**
 * 判断用户输入的账号是否复合规则
 * */
public class JustPhone {

    public static boolean justPhone(String phoneNum){
        if(phoneNum.length()!=11){
            return false;//不符合规则的账号
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(!pattern.matcher(phoneNum).matches()){//判断是否包含字符
            return false;//包含字符不是手机号
        }
        return true;
    }
}
