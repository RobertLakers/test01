package cn.tedu;

import cn.tedu.bean.Bean1;
import cn.tedu.config.MyConfig;
import cn.tedu.service.Service1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunClass {
    public static void main(String[] args) {
        //加载xml
        //ClassPathXmlApplicationContext
        //加载一个配置类,将配置类作为参数传递给这个上下文对象
        //配置类的反射对象
        AnnotationConfigApplicationContext context
                =new AnnotationConfigApplicationContext(MyConfig.class);
        //使用上下文对象 获取容器自定义bean Bean1 调用方法 get方法
        Bean1 b1=context.getBean(Bean1.class);
        System.out.println(b1.getAge());
        System.out.println(b1.getName());
        Service1 s1=context.getBean(Service1.class);
        System.out.println("cn.tedu.service:"+s1.getB1Name());
    }
}
