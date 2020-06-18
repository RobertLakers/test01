package cn.tedu.jedis;

import com.jt.common.utils.UUIDUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;

public class HASHTEST {
    @Test
    public void test(){
        //生成一个模拟的数据key
        /*String key= UUIDUtil.getUUID();
        System.out.println("key:"+key);
        int hashCode=key.hashCode();
        System.out.println("hashCode:"+hashCode);
        System.out.println("623948c9-4df8-43f5-b87d-d4ced595cd74".hashCode());*/
       /* int a=-192828127;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));*/
       /*String key="name";
       String key1=new String("name");
        System.out.println(key.hashCode()&Integer.MAX_VALUE);
        System.out.println(key1.hashCode()&Integer.MAX_VALUE);*/
       for(int i=0;i<100;i++){
           String key=UUIDUtil.getUUID();
           System.out.println((key.hashCode()&Integer.MAX_VALUE)%3);
       }
    }
}
