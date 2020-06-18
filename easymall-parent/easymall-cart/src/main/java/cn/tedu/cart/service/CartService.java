package cn.tedu.cart.service;

import cn.tedu.cart.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
    //注入mapper
    @Autowired
    private CartMapper cm;
    public List<Cart> queryMyCarts(String userId){
        return cm.selectCartsByUserId(userId);
    }
    //新增购物车数据
    @Autowired
    private RestTemplate restTemplate;
    public void addCart(Cart cart){
        //看当前add是insert还是updatenum
        //利用传递的参数 userId productId num select exist from t_cart userid productid
        Cart existCart=cm.selectExistByUIdAndProdId(cart);
        //不存在
        if(existCart==null){
            //当前要添加到购物车数据不存在的
            String url="http://productservice/product/manage/item/"+cart.getProductId();
            Product product = restTemplate.getForObject(url, Product.class);
            //开始封装cart完整
            cart.setProductName(product.getProductName());
            cart.setProductImage(product.getProductImgurl());
            cart.setProductPrice(product.getProductPrice());
            //新增购物车数据。
            cm.insertCart(cart);
        }else{
            //传递的购物车数据可以在数据库查到，将旧num和新num整合更新数据
            Integer newNum=existCart.getNum()+cart.getNum();
            cart.setNum(newNum);//cart中保存了合并的num值
            //update t_cart set num=#{num} where user_id=#{} and product_id=#{}
            cm.updateNumByUIdAndProdId(cart);
        }
    }
    //更新
    public void updateNum(Cart cart){
        cm.updateNumByUIdAndProdId(cart);
    }

    //删除
    public void deleteCart(Cart cart){
        cm.deleteCartByUIdAndProdId(cart);
    }






}
