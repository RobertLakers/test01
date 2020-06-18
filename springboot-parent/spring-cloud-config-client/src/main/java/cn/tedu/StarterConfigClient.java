package cn.tedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StarterConfigClient {
    //验证读取到的properties属性在本地工程能否
    //正常使用
    @Value("${date}")
    private String date;
    @Value("${profile}")
    private String rand;

    public static void main(String[] args) {

        SpringApplication.run(StarterConfigClient.class,args);
    }
    @RequestMapping("profile")
    public String profile(){
        return rand;
    }
}
