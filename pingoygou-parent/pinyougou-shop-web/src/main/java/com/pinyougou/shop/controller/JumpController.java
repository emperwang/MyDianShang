package com.pinyougou.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shop")
public class JumpController {

    @RequestMapping(value = "/register.do",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/shoplogin.do",method = RequestMethod.GET)
    public String shopLogin(){
        return "shoplogin";
    }

}
