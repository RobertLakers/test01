package cn.tedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RibbonService {
    @Autowired
    private RestTemplate template;
    public String sayHello(String name){
        //需要在业务层想办法调用service-hi的功能
        //通过治理组件注册发现，可以直接调用service-hi
       String result= template.getForObject
                ("http://service-hi/client/hello?name="+name,
                        String.class);
       //result hello i am from 9001/9002
        return result;
    }
}
