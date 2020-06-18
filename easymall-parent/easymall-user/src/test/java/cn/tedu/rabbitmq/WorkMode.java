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
 资源争抢模式
 多个消费端同时监听一个消息队列

 */
public class WorkMode {

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
    @Test
    public void send() throws IOException {
        String msg="发送消息";
        channel.queueDeclare("小红",false,false,false,null);

        for(int i=0;i<100;i++){
            channel.basicPublish("", "小红", null, msg.getBytes());
        }
        }

    /*
    消费端逻辑
     */
    @Test
    public void consumer01() throws Exception {
        //创建出来消费端对象
        //配置客户端消费逻辑，每次空闲时（回执之后）最多处理1条消息
        channel.basicQos(1);
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume("queue01direct",false,consumer);

        while(true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//从监听的队列中获取一个
//存在的消息
            Thread.sleep(50);
            //delivery接收的除了消息体body以外还有header properties
            System.out.println("阿强拿到消息：" + new String(delivery.getBody()));//body是二进制数组
            //手动回执
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
                    false);
        }
     }

    /*
    消费端逻辑
     */
    @Test
    public void consumer02() throws Exception {
        //创建出来消费端对象
        channel.basicQos(1);
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume("小红",false,consumer);

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//从监听的队列中获取一个
//存在的消息
            //delivery接收的除了消息体body以外还有header properties
            System.out.println("阿明拿到消息："+new String(delivery.getBody()));//body是二进制数组
            /*channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
                    false);*/
        }
    }
}
