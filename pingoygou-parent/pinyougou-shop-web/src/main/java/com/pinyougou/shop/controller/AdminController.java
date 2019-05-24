package com.pinyougou.shop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index.do")
    public String index(){
        return "index";
    }

    @GetMapping("/goods-edit.do")
    public String goods_edit(){
        return "goods_edit";
    }

    @GetMapping("/home.do")
    public String home(){
        return "home";
    }

    @GetMapping("/password.do")
    public String password(){
        return "password";
    }

    @GetMapping("/seller.do")
    public String seller(){
        return "index";
    }
}
