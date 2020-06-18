package cn.tedu.controller;

import cn.tedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService = null;

    @ResponseBody
    @RequestMapping("/point.action")
    public String point(String userId){
        int point = userService.point(userId);
        return "{\"points\":\""+point+"\"}";
    }
}
