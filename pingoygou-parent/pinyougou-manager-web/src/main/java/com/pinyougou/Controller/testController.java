package com.pinyougou.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class testController {

    @RequestMapping("/demo")
    public String index(){

        return "demo8";
    }

    @RequestMapping("/demo2")
    @ResponseBody
    public String index2(){
        String JSON="[\n" +
                "  {\"name\":\"zhangsan\",\"shuxue\":\"100\",\"yuwen\":\"100\"},\n" +
                "  {\"name\":\"lisi\",\"shuxue\":90,\"yuwen\":80},\n" +
                "  {\"name\":\"wangwu\",\"shuxue\":60,\"yuwen\":20}\n" +
                "]";
        return JSON;
    }

}
