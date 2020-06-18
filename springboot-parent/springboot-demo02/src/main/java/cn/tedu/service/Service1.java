package cn.tedu.service;

import cn.tedu.bean.Bean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service1 {
    //注入使用
    @Autowired
    private Bean1 b1;
    public String getB1Name(){
        return b1.getName();
    }
}
