package cn.tedu.config;

import cn.tedu.bean.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 可以将MyConfig看成是一个xml配置文件只要spring容器
 * 启动时加载这个配置类，就能实现相同的功能
 */
@Configuration
@ComponentScan("cn.tedu")//默认不给扫描包路径，直接使用配置的包进行配置
public class MyConfig {
    //如果类的代码里是空的，相当于xml配置文件什么都没有
    //利用@Bean注解实现配置Bean1的容器管理对象
    @Bean
    public Bean1 initBean1() {
        //将其生成new
        Bean1 b1 = new Bean1();
        b1.setAge(18);
        b1.setName("王翠花");
        return b1;
    }
}
