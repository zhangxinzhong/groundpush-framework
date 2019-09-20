package com.groundpush.core.utils;

import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Date;

/**
 * @description: localDateTime utils
 *
 * @author: zhangxinzhong
 * @date: 2019-09-10 上午10:45
 */
@Component
public class DateUtils {

    /**
     * 获得某天最大时间 2017-10-15 23:59:59
     */
    public LocalDateTime getMaxOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }

    /**
     * 获得某天最小时间 2017-10-15 00:00:00
     * @param date
     * @return
     */
    public LocalDateTime getMinOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }


    /**
     * 判断时间是否超过24小时
     * @param date
     * @return
     */
    public Boolean plusMinutesTime(LocalDateTime date) {
        LocalDateTime endTime = date.plusMinutes(24*60);
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = java.time.Duration.between(startTime,endTime);
        return duration.toMinutes() > 5?true:false;
    }

    /**
     * 获得当前时间-当前当天最大时间的秒值
     * @return
     */
    public Long getIntervalSecond() {
        LocalDateTime nowLocDateTime = LocalDateTime.now();
        LocalDateTime  endTime = getMaxOfDay(nowLocDateTime);
        LocalDateTime  startTime = nowLocDateTime;
        Duration duration = java.time.Duration.between(startTime,endTime);
        long  intervals = duration.getSeconds();
        return intervals <= 30?1*60:intervals;
    }

    /**
     * 获取增加天数后的时间-当前时间天数的间隔
     * @param date     需要增加天数的时间
     * @param auditDay 增加天数
     * @return
     */
    public Long getIntervalDays(LocalDateTime date,Integer auditDay) {
        LocalDateTime endTime = date.plusDays(auditDay);
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = java.time.Duration.between(startTime,endTime);
        return duration.toDays();
    }

}
