package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//eureka server开启注解 可以在启动当前工程时
//利用配置好的资源，启动内部的eureka-server的进程
@EnableEurekaServer
public class StarterES02 {
    public static void main(String[] args) {
        SpringApplication.run(StarterES02.class,args);
    }
}
