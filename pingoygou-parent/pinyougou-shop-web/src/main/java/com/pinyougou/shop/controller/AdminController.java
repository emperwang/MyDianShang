package com.pinyougou.shop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index.do")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/goods-edit.do")
    public String goods_edit(){
        return "admin/goods_edit";
    }

    @GetMapping("/goods.do")
    public String goods(){
        return "admin/goods";
    }

    @GetMapping("/home.do")
    public String home(){
        return "admin/home";
    }

    @GetMapping("/password.do")
    public String password(){
        return "admin/password";
    }

    @GetMapping("/seller.do")
    public String seller(){
        return "admin/seller";
    }
}
