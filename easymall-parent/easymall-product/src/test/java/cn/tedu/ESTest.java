package cn.tedu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESTest {
    //@Before将一个连接对象实现初始化，所有Test
    //代码就会before不需要每个方法都重新创建一遍
    private TransportClient client;
    //对client初始化
    @Before
    public void initClient() throws Exception {
        //Settings.EMPTY就是连接elasticsearch这个集群
        /*Settings setting =
                Settings.builder().
                        put("cluster.name",
                        "elasticsearch").build();*/
        client=new PreBuiltTransportClient
                (Settings.EMPTY);//配置连接哪个集群名称
        //连接使用的节点ip和端口
        //将负载均衡节点 3个都是，交给client
        //node1
        InetSocketTransportAddress address1=
            new InetSocketTransportAddress(
                  InetAddress.getByName("10.9.104.184"),
                    9300);
        //node2
        InetSocketTransportAddress address2=
                new InetSocketTransportAddress(
                        InetAddress.getByName("10.9.100.26"),
                        9300);
        //node3
        InetSocketTransportAddress address3=
            new InetSocketTransportAddress(
                  InetAddress.getByName("10.9.48.69"),
                        9300);
        //3个address交给client
        client.addTransportAddresses(address1,address2,address3);
    }

    /*
        索引管理，增加索引，判断存在，删除等
     */
    @Test
    public void indexManage(){
        //通过client拿到索引管理对象
        AdminClient admin = client.admin();
       // ClusterAdminClient cluster = admin.cluster();
        IndicesAdminClient indices = admin.indices();
        //TransportClient中有2套方法，一套直接发送调用
        //一套是预先获取request对象。
        CreateIndexRequestBuilder request1 = indices.prepareCreate("index01");//不存在的索引名称
        IndicesExistsRequestBuilder request2 = indices.prepareExists("index01");
        boolean exists = request2.get().isExists();
        if(exists){
            /*System.out.println("index01已存在");
            return;*/
            indices.prepareDelete("index01").get();
        }
        //发送请求request1到es
        CreateIndexResponse response1 = request1.get();
        //从reponse中解析一些有需要的数据
        System.out.println(response1.isShardsAcked());//json一部分 shards_acknowleged:true
        System.out.println(response1.remoteAddress());
        response1.isAcknowledged();//json acknowledged:true
    }
    /*
    文档管理：新建，删除，获取 indexName typeName docid
     */
    @Test
    public void panduan(){
        System.out.println(indexExist("index01"));
    }
    //方法的二次封装
    public boolean indexExist(String indexName){
        //通过client拿到索引管理对象
        AdminClient admin = client.admin();
        // ClusterAdminClient cluster = admin.cluster();
        IndicesAdminClient indices = admin.indices();
        return indices.prepareExists(indexName).get().isExists();
    }
    /*
        文档管理：向es中操作文档 新增，查询，删除
     */
    @Test
    public void documentManage() throws JsonProcessingException {
        //新增文档，准备文档数据
        //拼接字符串 对象与json的转化工具ObjectMapper
        //String docJson="{\"\"}"
        Product p=new Product();
        p.setProductNum(500);
        p.setProductName("三星手机");
        p.setProductCategory("手机");
        p.setProductDescription("能攻能守，还能爆炸");
        ObjectMapper om=new ObjectMapper();
        String pJson = om.writeValueAsString(p);
        //client 获取request发送获取response
        //curl -XPUT -d {json} http://10.9.104.184:9200/index01/product/1
        IndexRequestBuilder request = client.prepareIndex
                ("index01", "product", "1");
        //source填写成pJson
        request.setSource(pJson);
        request.get();
    }

    @Test
    public void getDocument(){
        //获取一个document只需要index type id坐标
        GetRequestBuilder request = client.prepareGet
                ("index01", "product", "1");
        GetResponse response = request.get();
        //从response中解析数据
        System.out.println(response.getIndex());
        System.out.println(response.getId());
        System.out.println(response.getVersion());
        System.out.println(response.getSourceAsString());
        client.prepareDelete("index01","product","1");
    }

    /*
    搜索功能使用
     */
    @Test
    public void search(){
        //其他查询条件也可以创建
        MatchQueryBuilder query = QueryBuilders.matchQuery("productName",
                "三星手机");
        //封装查询条件，不同逻辑查询条件对象不同
        //TermQueryBuilder query = QueryBuilders.termQuery
               // ("productName", "星");
        //底层使用lucene查询，基于浅查询，可以支持分页
        SearchRequestBuilder request = client.prepareSearch("index01");
        //在request中封装查询条件，分页条件等
        request.setQuery(query);
        request.setFrom(0);//起始位置 类似limit start
        request.setSize(5);
        SearchResponse response = request.get();
        //从response对象中解析搜索的结果
        //解析第一层hits相当于topDocs
        SearchHits searchHits = response.getHits();
        System.out.println("总共查到："+searchHits.totalHits);
        System.out.println("最大评分："+searchHits.getMaxScore());
        //循环遍历的结果
        SearchHit[] hits = searchHits.getHits();//第二层hits
        //hits包含想要的查询结果
        for (SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }
}
