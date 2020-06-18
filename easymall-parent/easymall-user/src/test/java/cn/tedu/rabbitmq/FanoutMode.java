package cn.tedu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
发布订阅
 */
public class FanoutMode {

    private Channel channel;

    @Before
    public void initChannel() throws IOException, TimeoutException {
        //获取连接工厂对象，赋值连接信息 ip port user pw vh
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("10.9.104.184");
        factory.setPort(5672);
        //factory.setUsername("guest");
        //factory.getPassword("guest")
        //factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        channel=connection.createChannel();
    }
    //准备几个静态常亮
    private static final String type="fanout";//交换机类型
    private static final String exName=type+"Ex";//交换机名称
    private static final String q1="queue01"+type;
    private static final String q2="queue02"+type;

    @Test
    public void send() throws IOException {
        //准消息
        String msg = "hello " + type;
        //声明交换机 exName type
        channel.exchangeDeclare(exName, type);
        //声明队列，多个队列，同时绑定一个fanout交换机
        channel.queueDeclare(q1,false,false,false,null);
        channel.queueDeclare(q2,false,false,false,null);
        //绑定
        channel.queueBind(q1,exName,"keke");
        channel.queueBind(q2,exName,"keke");
        //发送消息
        channel.basicPublish(exName,"haha",null,msg.getBytes());
    }

}
