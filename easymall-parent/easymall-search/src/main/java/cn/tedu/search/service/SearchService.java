package cn.tedu.search.service;

import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private TransportClient client;
    public List<Product> search(String text, Integer page, Integer rows) {
        //搜索功能 封装查询条件query matchQuery
        MatchQueryBuilder query = QueryBuilders.matchQuery(
                "productName", text);
        //开始调用查询，解析数据
        SearchRequestBuilder request = client.prepareSearch("easyindex");
        request.setQuery(query).setFrom((page-1)*rows).setSize(rows);
        SearchResponse response = request.get();
        //解析数据 获取第二层的hits对象
        SearchHits topHits = response.getHits();//第一层hits
        SearchHit[] hits = topHits.getHits();//第二层hits是个数组
        try{
            //准备一个list商品空对象
            List<Product> pList=new ArrayList<>();
            for(SearchHit hit:hits){
                //每循环一次，都可以从hit中拿到source字符串pJson
                String pJson = hit.getSourceAsString();
                //从字符串转化到对象过程，
                //传递参数json字符串，和转化对象类反射
                Product p = MapperUtil.MP.readValue(pJson, Product.class);
                //add到返回的list中
                pList.add(p);
            }
            return pList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
