package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class LoginController {

    @RequestMapping(value = "/register.do",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/shoplogin.do",method = RequestMethod.GET)
    public String shopLogin(){
        return "shoplogin";
    }

    @RequestMapping("/login-name.do")
    @ResponseBody
    public Map getLoginName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }

}
