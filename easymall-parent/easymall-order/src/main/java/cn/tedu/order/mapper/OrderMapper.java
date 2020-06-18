package cn.tedu.order.mapper;

import com.jt.common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    public List<Order> selectOrderByUserId(String userId);
    public void insertOrder(Order order);
    public void deleteOrderByOrderId(String orderId);
}
