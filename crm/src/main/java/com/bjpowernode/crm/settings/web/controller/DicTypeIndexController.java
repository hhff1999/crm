package com.bjpowernode.crm.settings.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DicTypeIndexController {
    @RequestMapping("/settings/dictionary/index.do")
    public String toIndex(){
        return "settings/dictionary/index";
    }
}