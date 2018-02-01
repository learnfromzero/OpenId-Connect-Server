package com.shaun.commons.controller;

import com.shaun.commons.annotation.BussinessLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/8.
 */
@Controller
public class IndexController {
    @BussinessLog(module = "系统登录",methoed = "首页")
    @RequestMapping("indexAction.do")
    public String IndexAction(){
        return "index";
    }
}
