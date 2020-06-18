package cn.tedu.seckill.consumer;

import cn.tedu.seckill.mapper.SecMapper;
import com.jt.common.pojo.Success;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Component
public class SeckillConsumer {
    @Autowired
    private SecMapper secMapper;
    //任意编辑一个方法，实现消费逻辑
    //方法的参数就是发送到rabbitmq中的对象
    //可以String 接收body 也可以是Message接收
    //包含消息属性
    @Autowired
    private JedisCluster jedisCluster;
    @RabbitListener(queues = "seckill01")
    public void consume(String msg){
        //接收到消息msg="1330119123/1"包含了user信息 商品id
        //解析出来
        Long userPhone=Long.parseLong(msg.split("/")[0]);
        Long seckillId=Long.parseLong(msg.split("/")[1]);
       //先连接redis 从redis中获取一个 seckill_1 rpop元素，成功了
        //说明元素没有被rpop完，具备秒杀减库存的权利
        //手动创建这个list 在redis集群，没创建会报错
        String listKey="seckill_"+seckillId;
        String rpop = jedisCluster.rpop(listKey);
        if(rpop==null){
            //如果rpop结果是null说明元素已经被拿完了，后续减库存
            //都不做了
            System.out.println("用户"+userPhone+"秒杀"+seckillId+"由于" +
                    "redis库存见底，秒杀失败");
            //解决了大量请求到达数据判断number>0时出现
            //线程安全问题导致的超卖
            return;
        }
        //执行减库存逻辑 seckill表格 number=number-1
        /*
        UPDATE seckill SET number=number-1 WHERE seckill_id=2
        AND number>0 AND NOW()>start_time AND NOW()<end_time
         */
        int result=secMapper.decrSeckillNum(seckillId);
        //判断减库存是否成功，result=1成功 result=0失败
        if(result==1){
            //成功 当前用户具备购买商品的资格
            //将成功的信息封装数据入库记录success表格
            Success suc=new Success();
                suc.setCreateTime(new Date());
                suc.setSeckillId(seckillId);
                suc.setUserPhone(userPhone);
                suc.setState(1);
            secMapper.insertSuccess(suc);
        }else{
            //失败，result==0 减库存失败，是因为卖完了
            System.out.println("用户"+userPhone+"秒杀"+seckillId+"由于" +
                    "库存见底，秒杀失败");
        }
    }
}
