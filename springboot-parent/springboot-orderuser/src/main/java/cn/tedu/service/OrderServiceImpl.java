package cn.tedu.service;

import cn.tedu.domain.Order;
import cn.tedu.mapper.OrderMapper;
import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper = null;

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public void pay(String orderId) {
        //1.支付订单将订单状态从未支付改为已支付
        System.out.println("支付订单.."+orderId);
        //2.获取订单金额信息，计算该订单对应的积分
        Order order = orderMapper.selcOrderById(orderId);
        int point = order.getOrder_money() * 1;
        //3.将积分更新到用户信息中
        userMapper.addPoint(order.getUser_id(),point);
    }
}
