package cn.tedu.cart.mapper;

import com.jt.common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    public List<Cart> selectCartsByUserId(String userId);
    public Cart selectExistByUIdAndProdId(Cart cart);
    public void insertCart(Cart cart);
    public void updateNumByUIdAndProdId(Cart cart);
    public void deleteCartByUIdAndProdId(Cart cart);
}
