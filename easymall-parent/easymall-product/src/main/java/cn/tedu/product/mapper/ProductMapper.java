package cn.tedu.product.mapper;

import com.jt.common.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    public int selectProductTotal();
    public List<Product> selectProductByPage(@Param("start")Integer start,
                                             @Param("rows")Integer rows);
    public Product selectProductById(String productId);
    public void insertProduct(Product product);
    public void updateProductById(Product product);
}
