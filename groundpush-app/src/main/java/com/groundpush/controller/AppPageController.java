package com.groundpush.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RestController
@ApiModel(value = "app h5 页面")
public class AppPageController {

    @ApiOperation("用户协议")
    @RequestMapping(name = "/user/protocol",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getUserProtocolPage(){
        return new ModelAndView("/app/user_protocol");
    }

    @ApiOperation("用户隐私")
    @RequestMapping(name = "/user/privacy",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView getUserPrivacyPage(){
        return new ModelAndView("/app/user_privacy");
    }
}
