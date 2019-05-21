package com.pinyougou.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/brand.do")
    public String brand(){
        return "admin/brand";
    }

    @RequestMapping("/content.do")
    public String content(){

        return "admin/content";
    }

    @RequestMapping("/contentcategory.do")
    public String contentcategory(){

        return "admin/content_category";
    }

    @RequestMapping("/goods.do")
    public String goods(){

        return "admin/goods";
    }

    @RequestMapping("/home.do")
    public String home(){

        return "admin/home";
    }
    @RequestMapping("/index.do")
    public String index(){

        return "admin/index";
    }

    @RequestMapping("/itemcat.do")
    public String itemcat(){

        return "admin/item_cat";
    }

    @RequestMapping("/seller.do")
    public String seller(){

        return "admin/seller";
    }

    @RequestMapping("/seller1.do")
    public String seller1(){

        return "admin/seller_1";
    }

    @RequestMapping("/specification.do")
    public String specification(){

        return "admin/specification";
    }

    @RequestMapping("/typetemplate.do")
    public String typetemplate(){

        return "admin/type_template";
    }
}
