package com.groundpush.controller;

import com.groundpush.GroundPushAppApplication;
import com.groundpush.core.utils.AesUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-04 下午1:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundPushAppApplication.class)
public class AesControllerTest {

    @Autowired
    private AesUtils aesUtils;

    @Test
    public void whenDecodeSuccess(){
        System.out.println(aesUtils.ecodes("18510874187","!QASZD#%&DFDG()%^&%&325434"));
    }

}
