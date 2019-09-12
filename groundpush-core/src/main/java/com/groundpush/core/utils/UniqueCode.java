package com.groundpush.core.utils;

import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.model.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * @description: 生成邀请码
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午4:18
 */
@Component
public class UniqueCode {

    public String getCode() {
        //获得当前时间的毫秒数
        Long h = System.currentTimeMillis();
        //转化为字符串
        String str = h.toString();
        //总长度
        int i = str.length();
        //用来取此字符串的末尾7位数，因为前面的数是年份什么的基本不变，我们只用后面的7位
        int j = i - 7;
        //将取到的7位数做成数组
        char[] charArray = str.substring(j, i).toCharArray();
        //将26位字母做成数组
        String[] arr = {"a", "b", "c", "d", "e", "f", "g",
                "h", "i", "g", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};
        //将字母数组随机取3个字母组成一个字符串，一共组成3个字符串放到目标数组target中
        // 用于生成唯一ID
        StringBuffer uniqueId = new StringBuffer();
        //用于取随机数和布尔值
        Random random = new Random();
        //用来控制是插入数字还是字母
        boolean insertflag = true;
        //用来控制插入数字的长度，别超过7
        int timecount = 0;
        //用来控制插入字母的总数，别超过9 7个数字加上9个字母组合
        int zimucount = 0;
        //判断时间是否插入了7位，默认true为不满
        boolean timeflag = true;
        //判断字幕是否插入了9位，默认true为不满
        boolean zimuflag = true;
        while (zimucount < 9 || timecount < 7) {
            //默认为ture，先加字母
            if (insertflag) {
                //如果uniqueId插入的字幕总数没超过9个
                if (zimucount < 9) {
                    //则在字母数组中随机选一个插入
                    uniqueId.append(arr[random.nextInt(26)]);
                    //对应加1
                    zimucount++;
                    //如果时间没有插入满7位则重新抓阄看插入时间还是数字
                    if (timeflag) {
                        //重置flag，随机产生false还是true
                        insertflag = random.nextBoolean();
                    }
                    //如果timeflag=false,时间数字已经插入满7位，则不抓阄了。保持insertflag=true 如果已经加够了否则不操作
                } else {
                    //将zimuflag变为已加够，false
                    zimuflag = false;
                    //将插入权判断给时间数字
                    insertflag = false;
                }
            } else {
                //先加时间转化成的数组，你也可以先加字母
                if (timecount < 7) {
                    //此处取时间数字数组不能用random随机取。那样用时间来生成数组就没意义了  //不可打乱顺序
                    uniqueId.append(charArray[timecount]);
                    //对应加1
                    timecount++;
                    //如果字母没有插入满9位则重新抓阄看插入时间还是数字
                    if (zimuflag) {
                        insertflag = random.nextBoolean();
                    }//如果zimuflag=false,字幕已经插入满9位，则不抓阄了。保持insertflag=false
                } else {
                    //将timeflag变为已加够，false
                    timeflag = false;
                    //将插入权判断给字母
                    insertflag = true;
                }
            }
        }
        return uniqueId.toString();
    }

    /**
     * 生成订单号
     * yyyyMMddHHmmssSSS+主键
     *
     * @return
     */
    public String generateUniqueCodeByPrimaryKey(Integer primaryKey) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        StringBuffer stringBuffer = new StringBuffer().append(LocalDateTime.now().format(dtf)).append(String.format("%07d", primaryKey));
        return stringBuffer.toString();
    }


    /**
     * 时间戳+随机数+主键
     *
     * @param primaryKey
     * @return
     */
    public String generateRandomCode(Integer primaryKey) {
        StringBuffer stringBuffer = new StringBuffer().append(System.currentTimeMillis()).append(RandomStringUtils.randomNumeric(6)).append(primaryKey);
        return stringBuffer.toString();
    }
}
