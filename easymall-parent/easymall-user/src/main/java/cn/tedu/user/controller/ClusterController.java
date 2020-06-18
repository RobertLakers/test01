package cn.tedu.user.controller;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

/**
 * 测试jedisCluster高可用的代码controller
 */
@RestController
public class ClusterController {
    @Autowired
    private JedisCluster cluster;

    @RequestMapping("cluster")
    public String setAndGet(String key,String value){
        //先set
        cluster.set(key,value);//key值不同对应槽道不同，对应节点可能不同
        //主节点
        return cluster.get(key);//使用这种代码结构测试宕机情况
    }

    //支持REST风格
    @RequestMapping(value="haha/{id}",method = RequestMethod.DELETE)
    public void delete(){ }
    @RequestMapping(value="haha/{id}",method = RequestMethod.PUT)
    public void save(){ }
    @RequestMapping(value="haha/{id}",method = RequestMethod.POST)
    public void update(){ }
}
