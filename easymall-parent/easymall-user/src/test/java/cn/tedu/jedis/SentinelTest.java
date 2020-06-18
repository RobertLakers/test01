package cn.tedu.jedis;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class SentinelTest {
    @Test
    public void test(){
        //jedis连接时，最终要想实现读写得连接上主节点，但是必须通过
        //哨兵获取主节点信息，就可以实现故障转移之后，访问哨兵拿到
        //最新的主节点信息
        //提供哨兵的所有节点连接信息 26379 26380 26381，从中选择一个可连接的节点使用
        //只要技术端哨兵集群有效，就可以完成信息发送和获取
        Set<String> sentinelNodes=new HashSet<>();
        sentinelNodes.add(new HostAndPort("10.9.151.60",26379).toString());
        //sentinelNodes.add(new HostAndPort("10.9.104.184",26380).toString());
        //sentinelNodes.add(new HostAndPort("10.9.104.184",26381).toString());
        //创建哨兵的连接池对象
        JedisSentinelPool pool=new JedisSentinelPool("mymaster",sentinelNodes);//指定哨兵集群节点，传递主从代码
        //从连接池中可以获取很多集群信息
        HostAndPort chm = pool.getCurrentHostMaster();
        System.out.println(chm);//通过哨兵，获取当前现役master信息
        //通过哨兵获取master，就可以创建jedisPool对象操作主节点
        //JedisPool jPool=new JedisPool(new JedisPoolConfig(),chm.getHost(),chm.getPort());
        //操作主节点
        Jedis jedis = pool.getResource();//返回值就是当前现役master连接对象。
        jedis.set("name","王老师");
        System.out.println(jedis.get("name"));
    }
    @Test
    public void ceshi(){
        Jedis jedis=new Jedis("10.9.48.69",8000);
        int[] slots=new int[5461];
        for(int i=10923;i<16384;i++){
            System.out.println(i);
            slots[i-10923]=i;
        }
        jedis.clusterAddSlots(slots);
    }
}
