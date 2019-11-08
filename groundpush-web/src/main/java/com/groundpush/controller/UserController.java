package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.UserQueryCondition;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.User;
import com.groundpush.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description: 用户管理
 * @author: zhangxinzhong
 * @date: 2019-09-17 下午5:15
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/toUser")
    public String toUserPage() {
        return "user/user";
    }

    @PostMapping
    @ResponseBody
    public JsonResp createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        userService.createUser(user);
        return JsonResp.success();
    }

    @PutMapping
    @ResponseBody
    public JsonResp editUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        userService.editUser(user);
        return JsonResp.success();
    }

    @GetMapping
    @ResponseBody
    public JsonResp queryAll(UserQueryCondition userQueryCondition, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Page<User> users = userService.queryAll(userQueryCondition, page, limit);
        return JsonResp.success(new PageResult(users));
    }

    @DeleteMapping
    @ResponseBody
    public JsonResp deleteUser(@RequestParam Integer userId) {
        Boolean bool = userService.findRoleUserByUserId(userId);
        if (bool) {
            throw new BusinessException(ExceptionEnum.USER_AND_DEL_ERROR.getErrorCode(), ExceptionEnum.USER_AND_DEL_ERROR.getErrorMessage());
        }
        userService.deleteUser(userId);
        return JsonResp.success();
    }

    @GetMapping("/{userId:\\d+}")
    @ResponseBody
    public JsonResp getUser(@PathVariable Integer userId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        return JsonResp.success(optionalUser.isPresent() ? optionalUser.get() : null);
    }

    @PutMapping("/{userId:\\d+}/resetPassword")
    @ResponseBody
    public JsonResp resetPassword(@PathVariable Integer userId) {
        try {
            userService.resetPassword(userId);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();
    }

}
