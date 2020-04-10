package org.wdzl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 王文
 * @Date: 2020/4/4 7:57
 * @Version: 1.0
 * @Description:
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
    @RequestMapping("/userList")
    public String userList(){
        return "user_list";
    }
}
