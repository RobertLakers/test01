package cn.tedu.mapper;

import cn.tedu.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    /**
     * 根据订单编号查询订单信息
     * @param order_id 要查询的订单编号
     * @return 查到的订单信息bean
     */
    public Order selcOrderById(@Param("order_id") String order_id);
}
