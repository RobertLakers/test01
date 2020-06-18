package cn.tedu.service;

public interface OrderService {
    /**
     * 支付订单
     * @param orderId 要支付的订单编号
     */
    void pay(String orderId);
}
