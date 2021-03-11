package com.util;

/**
 * 截取字符串做简介
 **/
public class StringSplit {
//截取字符串长度(中文2个字节，半个中文显示一个)
    public static String subTextString(String str, int len) {
        if (str.length() < len / 2) return str;
        int count = 0;
        StringBuffer sb = new StringBuffer();
        String[] ss = str.split("");
        for (int i = 1; i < ss.length; i++) {
            count += ss[i].getBytes().length > 1 ? 2 : 1;
            sb.append(ss[i]);
            if (count >= len) break;
        }
        //不需要显示...的可以直接return sb.toString();
        return (sb.toString().length() < str.length()) ? sb.append("...").toString() : str;
    }
}
