package cn.tedu.controller;

import cn.tedu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService = null;

    @ResponseBody
    @RequestMapping("/pay.action")
    public String pay(String orderId){
        try{
            orderService.pay(orderId);
            return "1";
        }catch (Exception e){
            //一般简单处理，需要打印异常信息
            e.printStackTrace();
            return "0";
        }
    }
}
