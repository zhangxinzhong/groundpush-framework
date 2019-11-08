package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.User;
import com.groundpush.core.utils.Constants;
import com.groundpush.security.core.repository.ObjectRepository;
import com.groundpush.service.MenuService;
import com.groundpush.service.UserService;
import com.groundpush.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description: home 页面相关请求处理
 * @author: zhangxinzhong
 * @date: 2019-08-17 19:00
 */
@Controller
public class HomeController {

    @Resource
    private MenuService menuService;

    @Resource
    private SessionUtils sessionUtils;

    @Resource
    private UserService userService;

    @Resource
    private ObjectRepository objectRepository;


    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("user",sessionUtils.getLogin().isPresent()?sessionUtils.getLogin().get().getUser():null);
        return "home/home";
    }

    @RequestMapping("/page")
    public String homePage() {
        return "home/page";
    }

    @ResponseBody
    @RequestMapping("/loadMenuByLoginUser")
    public JsonResp loadMenuByLoginUser() {
        return JsonResp.success(menuService.loadMenuByLoginUser());
    }


    /**
     * 用于用户自己修改 用户信息
     * @param user
     * @param bindingResult
     * @return
     */
    @PutMapping("/updateUser")
    @ResponseBody
    public JsonResp editUser(HttpServletRequest request, @Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        try{
            userService.editUser(user);
            Optional<LoginUserInfo> optionalLoginUserInfo = objectRepository.queryOrCreate(user.getLoginNo());
            if (optionalLoginUserInfo.isPresent()) {
                request.getSession().setAttribute(Constants.SESSION_LOGIN_USER_INFO, optionalLoginUserInfo.get());
            }
            return JsonResp.success();
        }catch (Exception e){
            throw e;
        }


    }
}
