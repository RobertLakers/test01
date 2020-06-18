package cn.tedu.product.service;

import cn.tedu.product.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.UUIDUtil;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper pm;
    //商品分页查询
    public EasyUIResult queryProductByPage(Integer page,Integer rows){
        //准备一个空对象
        EasyUIResult result=new EasyUIResult();
        /*
        int total:封装分页查询时对应表格数据的全部商品个数
        List rows:利用持久层从数据库查询分页数据的包装对象
         */
        //total 总数 select count(*) from t_product
        int total=pm.selectProductTotal();//65
        result.setTotal(total);
        //利用page rows做分页查询语句，查询List<Product>
        //计算起始位置
        int start=(page-1)*rows;
        List<Product> pList=pm.selectProductByPage(start,rows);
        result.setRows(pList);
        return result;
    }
    @Autowired
    private JedisCluster cluster;
    public Product queryOneProduct(String productId){
        //调用持久层mapper 发送sql语句封装product对象
        //product数据在redis存储结构 string类型 json key值保证商品数据唯一性 productId
        String productKey="product_"+productId;
        //使用集群判断商品数据是否存在
        try{
            ObjectMapper mp= MapperUtil.MP;
            if(cluster.exists(productKey)){
                //if如果进入，说明能够从redis集群获取商品数据
                //可以通过key值返回json字符串
                String pJson = cluster.get(productKey);
                //将json转化为对象
                Product product= mp.readValue(pJson,Product.class);//pJson源数据，Product.class
                //把json转化回来的类型反射对象
                return product;
            }else{
                //说明缓存未命中，到数据库查询数据
                Product product = pm.selectProductById(productId);
                //不着急返回数据，将product对象转化为json放到redis中
                String pJson = mp.writeValueAsString(product);
                //将缓存存储在redis
                cluster.setex(productKey,60*60*24,pJson);
                //查询结果返回给前端
                return product;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //新增商品
    public void saveProduct(Product product){
        //补齐id值
        String productId= UUIDUtil.getUUID();
        //一台服务器生成的uuid每次一定不一样的
        //服务器集群生成uuid有可能一样（几率极低）
        product.setProductId(productId);
        //新增数据可以看成绝大多数都是热点数据。可以在新增时直接添加缓存逻辑
        try{
            ObjectMapper mp= MapperUtil.MP;
            String productKey="product_"+productId;
            //转化json
            String pJson = mp.writeValueAsString(product);
            cluster.setex(productKey,60*60*24,pJson);
        }catch(Exception e){
            e.printStackTrace();
        }
        pm.insertProduct(product);
    }
    public void updateProduct(Product product){
        //保证更新商品与缓存数据一致
        //更新之前将商品缓存数据删除
        String productKey="product_"+product.getProductId();
        cluster.del(productKey);
        pm.updateProductById(product);
    }
}
