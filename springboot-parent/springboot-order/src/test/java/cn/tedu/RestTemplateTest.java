package cn.tedu;

import com.sun.javafx.collections.MappingChange;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateTest {
    //测试1，RestTemplate访问现有的网站
    //https://www.jd.com/
    @Test
    public void surfJD(){
        //准备一个访问的url地址
        String url="https://www.jd.com/";
        //新建一个RestTemplate
        RestTemplate template=new RestTemplate();
        //使用api发起向jd的访问
        //url，访问的服务器地址
        //responseType（Class）：接收的响应体数据类型
        String body=template.getForObject(url,String.class);//
        System.out.println(body);
    }

    //尝试使用RestTemplate访问user系统
    @Test
    public void userPoint(){
        //准备一个访问的url地址
        String url="http://localhost:8092/user/point.action?userId=[userId]&name=[userName]";
        //新建一个RestTemplate
        RestTemplate template=new RestTemplate();
        //接收json字符串
        Map<String,Object> map=new HashMap<>();
        map.put("userId",1);
        map.put("userName","王老师");
        template.getForObject(url,String.class,map);

    }
}
