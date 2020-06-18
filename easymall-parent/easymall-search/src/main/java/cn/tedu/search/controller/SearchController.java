package cn.tedu.search.controller;

import cn.tedu.search.service.SearchService;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;
    //调用一个方法
    @RequestMapping("search/manage/query")
    public List<Product> search(String query,Integer page,Integer rows){
        return searchService.search(query,page,rows);
    }
}
