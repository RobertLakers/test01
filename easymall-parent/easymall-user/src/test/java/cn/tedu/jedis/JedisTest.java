package cn.tedu.jedis;

import com.jt.common.utils.UUIDUtil;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JedisTest {
    /*
        如何使用jedis链接到对应的redis服务端
     */
    @Test
    public void test01(){
        //相当于从java代码执行 redis-cli -h 10.9.104.184 -p 6379
        Jedis jedis=new Jedis("10.9.104.184",6380);
        //在所有操作命令之前，对Jedis对象提供密码的方法调用
        //jedis.auth("123456");
        //可以利用jedis对象调用api方法操作redis
        jedis.set("name","王老师");
        System.out.println(jedis.get("name"));
        jedis.exists("name");
        jedis.lpush("list01","1");
        jedis.rpop("list01");
        jedis.hset("user","age","18");
    }
    //分片计算逻辑测试
    @Test
    public void test(){
        //创建一个分片对象，提供3个节点的信息
        List<Jedis> nodes=new ArrayList<Jedis>();
        nodes.add(new Jedis("10.9.104.184",6379));
        nodes.add(new Jedis("10.9.104.184",6380));
        nodes.add(new Jedis("10.9.104.184",6381));
        MyShardedJedis msj=new MyShardedJedis(nodes);
        //使用for循环模拟大量不同key-value数据的生成
        for(int i=0;i<100;i++){
            //每循环一次，模拟系统需要操作redis分布式集群处理的数据一次
            String key= UUIDUtil.getUUID()+i;
            String value="name"+i;
            msj.set(key,value);
            System.out.println(msj.get(key));
        }
    }
    @Test
    public void test03(){
        //创建一个可以实现分片计算的对象ShardedJedis
        //收集节点信息，交给分片对象
        List<JedisShardInfo> nodes=new ArrayList<>();
        //6379,6380 6381节点信息存到nodes中
        nodes.add(new JedisShardInfo("10.9.104.184",6379,500,500,5));
        nodes.add(new JedisShardInfo("10.9.104.184",6380));//weigth=1
        nodes.add(new JedisShardInfo("10.9.104.184",6381));//wegith=1
        //构造一个分片对象ShardedJedis
        ShardedJedis sj=new ShardedJedis(nodes);
        for(int i=0;i<1000;i++){
            String key=UUIDUtil.getUUID();
            String value=""+i;
            sj.set(key,value);
            System.out.println(sj.get(key));
        }
    }
    //jedisPool连接池对象，保管了多个jedis对象，使用时获取资源
    //用完了还回资源
    @Test
    public void test04(){
        //创建连接池对象，先准备一个连接池属性配置对象
        JedisPoolConfig config=new JedisPoolConfig();
        //定义最大连接数 最小空闲，最大空闲，初始化等等
        config.setMaxTotal(200);//最大连接数，当最小空闲不满足时，创建新的连接，上限200
        config.setMaxIdle(8);//最大空闲数，当jedis空闲数量超过最大空闲，会关闭多与的jedis
        config.setMinIdle(3);//最小空闲数,不满足最小空闲说明忙，按照最大空闲的上限，补充jedis
        //使用config对象创建jedisPool
        JedisPool pool=new JedisPool(config,"10.9.104.184",6380);
        //连接池底层就是按照给定属性，创建一批连接6380的jedis
        Jedis resource = pool.getResource();//相当于从连接池拿到一个空闲的jedis对象
        resource.set("name","王老师");
        pool.returnResource(resource);
    }
    @Test
    public void test5(){
        //创建管理维护多个分片对象的分片连接池，需要先收集每个分片对象的底层
        //jedis集群连接信息
        List<JedisShardInfo> info=new ArrayList<>();
        info.add(new JedisShardInfo("10.9.104.184",6379));
        info.add(new JedisShardInfo("10.9.104.184",6380));
        info.add(new JedisShardInfo("10.9.104.184",6381));
        //准备连接池属性配置对象config
        JedisPoolConfig config=new JedisPoolConfig();
        //做属性值
        //创建分片连接池对象
        ShardedJedisPool pool=new ShardedJedisPool(config,info);
        //从池子获取资源
        pool.getResource();
        //使用完毕后，将对象资源返回给连接池
    }


    //测试连接redis-cluster集群
    @Test
    public void test06(){
        //准备创建对象JedisCluster的参数，
        //集群的连接信息的收集，由于集群两两互联，相互可以获取信息，只需要提供一部分节点
        //8000 8001
        Set<HostAndPort> info=new HashSet<>();
        info.add(new HostAndPort("10.9.104.184",8000));
        info.add(new HostAndPort("10.9.104.184",8001));
        //连接池属性
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMinIdle(3);
        config.setMaxTotal(200);
        //创建最终对象
        JedisCluster cluster=new JedisCluster(info,config);//从8000 8001拿到第一个可连接
        //获取整个集群节点信息 cluster nodes封装槽道逻辑
        for(int i=0;i<100;i++){
            cluster.set("key_"+i,"name");
        }

    }
}
