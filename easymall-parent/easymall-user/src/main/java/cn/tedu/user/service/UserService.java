package cn.tedu.user.service;

import cn.tedu.user.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.UUIDUtil;
import com.jt.common.vo.SysResult;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper um;
    public SysResult checkUserName(String userName){
        //从数据库读取数据判断是否可用
        int exist=um.selectCountByUserName(userName);
        //根据返回结果返回SysResult的数据
        if(exist==1){//已经存在 不可用 status=201
            return SysResult.build(201,"",null);
        }else{
            //表示等于0 可用 status=200
            return SysResult.ok();
        }
    }
    //注册用户
    public void saveUser(User user){
        //user对象作为表单数据的接收，缺少userId
        String userId= UUIDUtil.getUUID();
        user.setUserId(userId);
        //user的password是明文，需要进行加密处理，md5
        //md5加盐 例如密码123456 加密e10adc3949ba59abbe56e057f20f883e
        //让用户的密码看起来不想密码 12k93MDK2 1234567+“aljfladsjflsad”
        String pasMd5= MD5Util.md5(user.getUserPassword());
        user.setUserPassword(pasMd5);
        um.insertUser(user);
    }
   /* @Autowired
    private ShardedJedisPool pool;*/
    @Autowired
    private JedisCluster jedis;
    public String doLogin(User user){
        //获取一个连接池资源
        //ShardedJedis jedis = pool.getResource();
        /*
        1 判断合法
        2 存储redis
         */
        // 准备一个返回的字符串，默认值""
        String ticket="";
        //使用user参数 查询数据库，判断是否存在user行数据 验证合法
        //user里明文password加密
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        User exist=um.selectUserByNameAndPw(user);//select * from t_user where name= and pw=
        if(exist==null){
            //说明没有查询到user对象登陆时不合法的
            return ticket;
        }else{
            //一旦进入else说明登录校验成功，先生成本次登录使用的ticket
            ticket="EM_TICKET"+System.currentTimeMillis()+user.getUserName();
            //生成顶替逻辑中的userId的唯一值
            String loginKey="login_"+exist.getUserId();
            //判断是否需要顶替
            if(jedis.exists(loginKey)){
                //进入到if说明有人已经登录，要将它登录时使用的ticket删掉
                String lastTicket=jedis.get(loginKey);
                jedis.del(lastTicket);
                //在这个loginKey覆盖上次登录有效ticket
                jedis.set(loginKey,ticket);
            }
            //如果不存在，就把自己登录时用2个key-value设置在redis
            jedis.set(loginKey,ticket);
            exist.setUserPassword(null);
            ObjectMapper om= MapperUtil.MP;
            try{
                String uJson = om.writeValueAsString(exist);

                jedis.setex(ticket,60*60*2,uJson);//不能set永久数据，假设超时时间2小时
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }/*finally {
                *//*if(jedis!=null){
                    jedis.close();
                }*//*
                if(jedis!=null){
                    pool.returnResource(jedis);
                }
            }*/
            //当上述逻辑全部执行完毕，ticket该赋值，就赋值了
            return ticket;
        }
    }

    //根据ticket查询redis中数据
    public String queryUserData(String ticket){
        //从连接池获取资源
        //ShardedJedis jedis = pool.getResource();
        //创建jedis对象
        //Jedis jedis=new Jedis("10.9.104.184",6380);
        //保证使用完毕，关闭对象
        try{
            //实现续租逻辑，判断剩余时间。如果有个访问超过1.30小时 剩余30分钟 小于30分钟，给超时
            //判断剩余时间
            Long leftTime = jedis.pttl(ticket);
            if(leftTime<1000*60*30){
                //剩余时间不足30分钟，重新刷一次超时新的2小时
                //延长 leftTime+想延长时间
                jedis.pexpire(ticket,1000*60*60*2);
            }
            return jedis.get(ticket);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }/*finally {
            if(jedis!=null){
                //jedis.close();
                pool.returnResource(jedis);
            }
        }*/
    }
}
