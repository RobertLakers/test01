package cn.tedu.user.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "easydb..t_order")
public class TOrder {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_money")
    private Double orderMoney;

    @Column(name = "order_receiverinfo")
    private String orderReceiverinfo;

    @Column(name = "order_paystate")
    private Integer orderPaystate;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "user_id")
    private String userId;

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return order_money
     */
    public Double getOrderMoney() {
        return orderMoney;
    }

    /**
     * @param orderMoney
     */
    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    /**
     * @return order_receiverinfo
     */
    public String getOrderReceiverinfo() {
        return orderReceiverinfo;
    }

    /**
     * @param orderReceiverinfo
     */
    public void setOrderReceiverinfo(String orderReceiverinfo) {
        this.orderReceiverinfo = orderReceiverinfo;
    }

    /**
     * @return order_paystate
     */
    public Integer getOrderPaystate() {
        return orderPaystate;
    }

    /**
     * @param orderPaystate
     */
    public void setOrderPaystate(Integer orderPaystate) {
        this.orderPaystate = orderPaystate;
    }

    /**
     * @return order_time
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}