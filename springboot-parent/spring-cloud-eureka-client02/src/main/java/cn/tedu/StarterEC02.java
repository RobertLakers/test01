package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//开启是eureka 客户端进程注解
@EnableEurekaClient
public class StarterEC02 {
    public static void main(String[] args) {
        SpringApplication.run(StarterEC02.class,args);
    }
}
