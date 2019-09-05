package com.groundpush.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @description: 登录controller
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:34
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginPage() {
        return "login/login";
    }

    @GetMapping("/invalidSession")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void sessionInvalid(HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print("<script>alert('session 失效，请重新登录！'); window.location.href='/'; </script>");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }


    }



}
