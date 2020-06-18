package cn.tedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.tedu.service.RibbonService;

@RestController
public class RibbonController {
    @Autowired
    private RibbonService rs;
    //localhost:9004/ribbon/hello?name=wang
    //ribbon也想给它返回一个英文，
    //假设当前ribbon工程不具备说英文的能力，这次请求虽然
    //到达了该ribbon的web应用，无法单独处理功能
    //想方法实现调用service-hi的功能
    @RequestMapping("ribbon/hello")
    public String sayHello(String name){
        //使用业务层返回调用service-hi的结果
        return "RIBBON:"+rs.sayHello(name);
    }
}
