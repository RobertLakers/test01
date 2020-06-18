package cn.tedu.order.service;

import cn.tedu.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import com.jt.common.pojo.OrderItem;
import com.jt.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper om;

    public List<Order> queryMyOrders(String userId){
        //先查父表数据 select * from t_order where user_id
        List<Order> oList=om.selectOrderByUserId(userId);//拿一次连接，还一次连接
        /*//挨个封装
        for(Order order:oList){
            //每循环一个 对应使用orderId查询子表
            //select * from t_order_item where order_id=
            List<OrderItem> oiList=om.selectOrderItem(order.getOrderId());//拿一次连接，还一次连接
            order.setOrderItems(oiList);
        }*/
        return oList;
    }
    //提交订单新增入库
    public void addOrder(Order order){
        //缺少orderId
        order.setOrderId(UUIDUtil.getUUID());
        order.setOrderPaystate(0);
        order.setOrderTime(new Date());
        om.insertOrder(order);
        /*//order对象中的orderItems 元素对象orderItem也需要
        //orderId属性值。
        om.insertOrder(order);//写主表
        for(OrderItem oi:order.getOrderItems()){
            //oi对应t_order_item中的一个行数据
            //每个oi对象都缺少orderId
            oi.setOrderId(order.getOrderId());
            //新增oi到t_order_item
            om.insertOrderItem(oi);
        }*/
    }
    //删除订单
    public void deleteOrder(String orderId){
        om.deleteOrderByOrderId(orderId);
    }
}
