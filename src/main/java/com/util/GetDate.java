package com.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**时间工具类*/
public class GetDate {
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**获取当前系统时间*/
    private static Date date = new Date();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ///获得string类型时间
    public static String getDate() {
        return dateFormat.format(date);
    }

    //str->Date
    public static Date strToDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateFormat.format(date));
        return dateTime.toDate();
    }

    public static void main(String[] args) {
        System.out.println(GetDate.getDate());
        System.out.println(GetDate.strToDate());
    }
}
