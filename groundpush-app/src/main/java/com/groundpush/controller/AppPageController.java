package com.groundpush.controller;

import com.groundpush.core.utils.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-20 17:04
 * @since JDK  1.8
 */
@Slf4j
@Controller
@ApiModel(value = "app h5 页面")
public class AppPageController {

    @ApiOperation("用户协议")
    @GetMapping("/appPage/{type}")
    public String getUserProtocolPage(@PathVariable String type) {
        if (StringUtils.equalsIgnoreCase(type, Constants.CUSTOMER_PROTOCOL)) {
            return "/app/user_protocol";
        }else if(StringUtils.equalsIgnoreCase(type, Constants.CUSTOMER_PRIVACY)){
            return "/app/user_privacy";
        }

        return "error";

    }
}
