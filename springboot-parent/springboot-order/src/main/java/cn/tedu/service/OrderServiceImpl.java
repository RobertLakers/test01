package cn.tedu.service;

import cn.tedu.domain.Order;
import cn.tedu.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper=null;
    //用户积分新增不在由订单系统管理
    //在订单发送请求通知用户系统进行积分更新
    @Autowired
    private RestTemplate template;
    @Override
    public void pay(String orderId) {
        //1.支付订单将订单状态从未支付改为已支付
        System.out.println("支付订单.."+orderId);
        //2.获取订单金额信息，计算该订单对应的积分
        Order order = orderMapper.selcOrderById(orderId);
        int money=order.getOrder_money();
        //RestTemplate实现http请求的发送，按照接口文件
        //将money放到请求参数，发送给用户系统接收请求
        //发送请求到用户系统

        //url第一应该做到nginx的负载均衡，域名80端口访问
        String url="http://test-user/user/update.action?money="+money;
        template.getForObject(url,String.class);
    }
}
