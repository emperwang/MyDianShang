package com.pinyougou.protal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class JumpController {

    @RequestMapping("cart.do")
    public String cart(){

        return "cart";
    }
    @RequestMapping("cooperation.do")
    public String cooperation(){

        return "cooperation";
    }
    @RequestMapping("getOrderInfo.do")
    public String getOrderInfo(){

        return "getOrderInfo";
    }
    @RequestMapping("homeIndex.do")
    public String homeIndex(){

        return "home-index";
    }
    @RequestMapping("homeOrderEvaluate.do")
    public String homeOrderEvaluate(){

        return "home-order-evaluate";
    }
    @RequestMapping("homeOrderPay.do")
    public String homeOrderPay(){

        return "home-order-pay";
    }
    @RequestMapping("homeOrderReceive.do")
    public String homeOrderReceive(){

        return "home-order-receive";
    }
    @RequestMapping("homeOrderSend.do")
    public String homeOrderSend(){

        return "home-order-send";
    }
    @RequestMapping("homeOrderDetail.do")
    public String homeOrderDetail(){

        return "home-orderDetail";
    }
    @RequestMapping("homePersonCollect.do")
    public String homePersonCollect(){

        return "home-person-collect";
    }
    @RequestMapping("homePersonFootmark.do")
    public String homePersonFootmark(){

        return "home-person-footmark";
    }
    @RequestMapping("homeSettingAddress.do")
    public String homeSettingAddress(){

        return "home-setting-address";
    }
    @RequestMapping("homeSettingAddressComplete.do")
    public String homeSettingAddressComplete(){

        return "home-setting-address-complete";
    }
    @RequestMapping("homeSettingAddressPhone.do")
    public String homeSettingAddressPhone(){

        return "home-setting-address-phone";
    }
    @RequestMapping("homeSettingInfo.do")
    public String homeSettingInfo(){

        return "home-setting-info";
    }
    @RequestMapping("homeSettingSafe.do")
    public String homeSettingSafe(){

        return "home-setting-safe";
    }
    @RequestMapping("index.do")
    public String index(){

        return "index";
    }
    @RequestMapping("item.do")
    public String item(){

        return "item";
    }
    @RequestMapping("login.do")
    public String login(){

        return "login";
    }
    @RequestMapping("pay.do")
    public String pay(){

        return "pay";
    }
    @RequestMapping("payfail.do")
    public String payfail(){

        return "payfail";
    }
    @RequestMapping("paysuccess.do")
    public String paysuccess(){

        return "paysuccess";
    }
    @RequestMapping("register.do")
    public String register(){

        return "register";
    }
    @RequestMapping("sampling.do")
    public String sampling(){

        return "sampling";
    }
    @RequestMapping("search.do")
    public String search(){

        return "search";
    }
    @RequestMapping("secKillIndex.do")
    public String secKillIndex(){

        return "seckill-index";
    }
    @RequestMapping("secKillItem.do")
    public String secKillItem(){

        return "seckill-item";
    }
    @RequestMapping("shop.do")
    public String shop(){

        return "shop";
    }
    @RequestMapping("successCart.do")
    public String successCart(){

        return "success-cart";
    }
}
