package com.pinyougou.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login.do")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error.do")
    public String loginError(){
        return "login_error";
    }

    @RequestMapping(value = "/loginName.do",method = RequestMethod.GET)
    @ResponseBody
    public Map getName(){
        //用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
