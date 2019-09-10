package com.groundpush.core.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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

}
