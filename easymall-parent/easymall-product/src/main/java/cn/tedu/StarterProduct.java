package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.product.mapper")//product,user,cart,order
//cn.tedu.product.**
public class StarterProduct {
    public static void main(String[] args) {
        SpringApplication.run(StarterProduct.class,args);
    }
}
