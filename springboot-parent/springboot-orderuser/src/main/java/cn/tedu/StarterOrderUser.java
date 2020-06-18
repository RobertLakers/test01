package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tedu.mapper")
public class StarterOrderUser {
    public static void main(String[] args) {
        SpringApplication.run(StarterOrderUser.class,args);
    }
}
