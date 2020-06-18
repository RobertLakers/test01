package cn.tedu.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "redis.cluster")
public class ClusterConfig {
    //准备几个为bean对象创建做的属性
    private List<String> nodes;//redis.cluster.nodes
    private Integer maxTotal;//redis.cluster.maxTotal
    private Integer maxIdle;//redis.cluster.maxIdle
    private Integer minIdle;//redis.cluster.minIdle
    //初始化对象的方法
    @Bean
    public JedisCluster initCluster(){
        //收集节点信息。解析nodes属性
        Set<HostAndPort> info=new HashSet<>();
        for (String node:nodes){
            //node="10.9.104.184:800*";
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);
            info.add(new HostAndPort(host,port));
        }
        //连接池的属性JedisPoolConfig
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        return new JedisCluster(info,config);
    }
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
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
}
