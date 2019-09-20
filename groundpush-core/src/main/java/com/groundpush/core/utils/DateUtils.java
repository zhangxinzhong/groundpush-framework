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
        long nowMinutes = LocalDateTime.now().getMinute();
        LocalDateTime local = date.plusMinutes(24*60-1);
        long localMinute = local.getMinute();
        return (localMinute - nowMinutes) > 0?true:false;
    }

    /**
     * 获得当前毫秒数-当天最大时间毫秒数的秒值
     * @return
     */
    public Long getIntervalSecond() {
        LocalDateTime  localDateTime = getMaxOfDay(LocalDateTime.now());
        long maxSecond = localDateTime.getSecond();
        long currSecond  = LocalDateTime.now().getSecond();
        long intervals = maxSecond - currSecond;
        return intervals <= 30?1*60:intervals;
    }
}
