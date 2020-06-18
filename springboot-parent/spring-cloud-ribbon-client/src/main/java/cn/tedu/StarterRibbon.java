package cn.tedu;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class StarterRibbon {
    public static void main(String[] args) {
        SpringApplication.run(StarterRibbon.class,args);
    }
    //构造一个RestTemplate对象，注入到业务层使用
    //启动类本身是配置类前提，准备一个bean对象，注入使用

    @Bean
    @LoadBalanced //本质是对该对象添加过滤的逻辑，所有这个对象发送
    //的http请求都会被ribbon过滤
    public RestTemplate initTemplate(){
        return new RestTemplate();
    }
    //实现自定义的负载均衡计算执行对象
    @Bean
    public IRule initMyRule(){
        return new RandomRule();
    }
}
