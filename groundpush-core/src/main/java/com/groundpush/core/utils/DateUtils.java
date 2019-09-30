package com.groundpush.core.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @description: localDateTime utils
 * @author: zhangxinzhong
 * @date: 2019-09-10 上午10:45
 */
@Component
public class DateUtils {

    /**
     * 获得某天最大时间 2017-10-15 23:59:59
     */
    public LocalDateTime getMaxOfDay(LocalDateTime date) {
        LocalDateTime localDateTime = date.with(LocalTime.MAX);
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime;
    }

    /**
     * 获得某天最小时间 2017-10-15 00:00:00
     *
     * @param date
     * @return
     */
    public LocalDateTime getMinOfDay(LocalDateTime date) {
        LocalDateTime localDateTime = date.with(LocalTime.MIN);
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime;
    }


    /**
     * 判断时间是否超过24小时
     *
     * @param date
     * @return
     */
    public Boolean plusMinutesTime(LocalDateTime date) {
        LocalDateTime endTime = date.plusMinutes(24 * 60);
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = java.time.Duration.between(startTime, endTime);
        return duration.toMinutes() > 5 ? true : false;
    }

    /**
     * 获得当前时间-当前当天最大时间的秒值
     *
     * @return
     */
    public Long getIntervalSecond() {
        LocalDateTime nowLocDateTime = LocalDateTime.now();
        LocalDateTime endTime = getMaxOfDay(nowLocDateTime);
        LocalDateTime startTime = nowLocDateTime;
        Duration duration = java.time.Duration.between(startTime, endTime);
        long intervals = duration.getSeconds();
        return intervals <= 30 ? 1 * 60 : intervals;
    }

    /**
     * 获取增加天数后的时间-当前时间天数的间隔
     *
     * @param date     需要增加天数的时间
     * @param auditDay 增加天数
     * @return
     */
    public Long getIntervalDays(LocalDateTime date, Integer auditDay) {
        LocalDateTime endTime = date.plusDays(auditDay);
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = java.time.Duration.between(startTime, endTime);
        return duration.toDays();
    }

    /**
     * 日期字符串格式转LocalDateTime
     *
     * @param dateTime
     * @return
     */
    public LocalDateTime transToLocalDateTime(String dateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, df);
        return ldt;
    }

    /**
     * 日期转字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public String transToString(Date dateTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(dateTime);
    }

    /**
     * 日期转字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @param format
     * @return
     */
    public LocalDateTime transToLocalDateTime(String dateTime, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateTime,df);
        return LocalDateTime.from(localDate.atStartOfDay());
    }


    /**
     * 日期转字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public String localDateTimetransToString(LocalDateTime dateTime, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(dateTime);
    }
}
