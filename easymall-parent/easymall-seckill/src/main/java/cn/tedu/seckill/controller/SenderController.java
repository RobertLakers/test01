package cn.tedu.seckill.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
public class SenderController {
    @Autowired
    private RabbitTemplate template;
    //接收请求，发送消息
    @Autowired
    private JedisCluster jedisCluster;
    @RequestMapping("send")
    public String sendMsg(String msg){
        //直接将消息msg作为消息体的内容，让template发送
        /*
            exchange:交换机名称
            routingKey:路由key
            msg：Object类型msg 自动进行byte转化
         */
        /*template.convertAndSend("directEx",
                "北京",msg);*/
        //相当于 channel.basicPublish("ex","routing",msg.geBytes)
        //关心发送消息时，消息有一些属性想要携带，使用send方法
        /*MessageProperties properties=new MessageProperties();
        properties.setPriority(100);
        //properties.setUserId("110");
        Message message=new Message(msg.getBytes(),properties);
        template.send("directEx1","北京",message);
        return "success";*/
        //redis限制同一个用户秒杀的商品

        template.convertAndSend("seckillD01","seckill",msg);
        return "success";
    }
}
