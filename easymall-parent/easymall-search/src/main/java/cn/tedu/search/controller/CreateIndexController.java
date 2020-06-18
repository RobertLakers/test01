package cn.tedu.search.controller;

import cn.tedu.search.mapper.CreateIndexMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.UUIDUtil;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateIndexController {
    @Autowired
    private CreateIndexMapper createIndexMapper;
    /*
    注入一个mapper对象，获取list的商品数据
    按照新增文档的测试代码将商品数据转化成json
    存入到es集群中。通过外部的一次调用实现这个过程
     */
    @Autowired
    private TransportClient client;
    @RequestMapping("create")
    public String createIndex(String indexName,
          String typeName){
        //读取数据库 商品数据
        List<Product> pList= createIndexMapper.selectProducts();
        //封装成json字符串client发起请求写入es索引
        try{
            //判断索引是否存在，不存在则创建
            AdminClient admin = client.admin();
            IndicesAdminClient indices = admin.indices();
            IndicesExistsRequestBuilder request = indices.prepareExists(indexName);
            IndicesExistsResponse response = request.get();
            if(!response.isExists()){
                //索引不存在，创建索引
                indices.prepareCreate(indexName).get();
            }
            for (Product p:pList){
                //转化对象为json
                String pJson = MapperUtil.MP.writeValueAsString(p);
                //调用client的api生成request 将json包装
                IndexRequestBuilder request1 = client.prepareIndex(indexName, typeName, p.getProductId());
                //pJson放到request的source数据中
                request1.setSource(pJson);
                request1.get();
            }
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
}
