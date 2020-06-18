package cn.tedu.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "easymall.redis")//当前配置类主要配置redis
public class ShardedJedisConfig {
    //@Value("${redis.maxTotal}")
    private Integer maxTotal;//properties文件中 redis.maxTotal
    private Integer maxIdle;//properties文件中 redis.maxIdle;
    private Integer minIdle;
    //ConfigurationProperties支持 属性值以，隔开赋值list类型数据
    private List<String> nodes;//有多少个节点就以10.9.9.9:6379，10.9.9.9：6380,
    //在properties文件中，需要按照对应关系去指定配置key值
    //key=prefix.属性名称
    @Bean
    public ShardedJedisPool initPool(){
        //收集节点信息
        List<JedisShardInfo> info=new ArrayList<>();
        //从nodes数据中解析出来每一个节点ip地址和端口号
        //nodes={"10.9.104.184:6379","10.9.104.184:6380","10.9.104.184:6381"}
        for (String node:nodes) {
            //对list的所有元素进行循环，每次循环拿出一个元素值，赋值给node
            //node循环第一次 node="10.9.104.184:6379"
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);//"6379"-->6379
            info.add(new JedisShardInfo(host,port));
        }
        //配置属性config
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        return new ShardedJedisPool(config,info);
    }
    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }


}
