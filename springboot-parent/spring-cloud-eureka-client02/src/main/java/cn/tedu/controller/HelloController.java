package cn.tedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //模仿该客户端工程的具体功能
    @RequestMapping("/client/hello")
    public String sayHello(String name){
        return "hello "+name+", i am from 9002";
        //通过端口号的标识，区分一下服务被调用时的响应来源
    }
}
