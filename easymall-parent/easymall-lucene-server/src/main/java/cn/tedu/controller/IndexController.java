package cn.tedu.controller;

import cn.tedu.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    //满足REST风格
    //新增索引
    @PutMapping("/{indexName}")
    public String addIndex(@PathVariable String indexName) throws Exception {
        indexService.addIndex(indexName);
        return "add "+indexName+" success";
    }
    //删除索引
    @DeleteMapping("{indexName}")
    public String deleteIndex(@PathVariable String indexName){
        return "delete "+indexName+" success";
    }
    //删除索引
    @PostMapping("{indexName}")
    public String updateIndex(@PathVariable String indexName){
        return "post "+indexName+" success";
    }
    //删除索引
    @GetMapping("{indexName}")
    public String getIndex(@PathVariable String indexName){
        return "get "+indexName+" success";
    }
    //搜索 get fieldName text
    @GetMapping("search/{indexName}")
    public String searchDoc(@PathVariable String indexName,String fieldName,String text) throws IOException {
        return  indexService.search(indexName,fieldName,text);
    }
}
