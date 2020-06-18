package cn.tedu.order.controller;

import cn.tedu.order.service.OrderService;
import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order/manage")
public class OrderController {
    @Autowired
    private OrderService os;
    //查询我的订单
    @RequestMapping("query/{userId}")
    public List<Order> queryMyOrders(@PathVariable String userId){
        return os.queryMyOrders(userId);
    }
    //提交订单
    @RequestMapping("save")
    public SysResult addOrder(Order order){
        try{
            os.addOrder(order);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,
                    "失败了",null);
        }
    }
    //订单删除
    @RequestMapping("delete/{orderId}")
    public SysResult deleteOrder(@PathVariable String orderId) {
        try{
            os.deleteOrder(orderId);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"删除失败",null);
        }
    }
}
