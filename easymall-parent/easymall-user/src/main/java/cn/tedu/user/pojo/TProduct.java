package cn.tedu.user.pojo;

import javax.persistence.*;

@Table(name = "easydb..t_product")
public class TProduct {
    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_imgurl")
    private String productImgurl;

    @Column(name = "product_num")
    private Integer productNum;

    @Column(name = "product_description")
    private String productDescription;

    /**
     * @return product_id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return product_price
     */
    public Double getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice
     */
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return product_category
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * @param productCategory
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * @return product_imgurl
     */
    public String getProductImgurl() {
        return productImgurl;
    }

    /**
     * @param productImgurl
     */
    public void setProductImgurl(String productImgurl) {
        this.productImgurl = productImgurl;
    }

    /**
     * @return product_num
     */
    public Integer getProductNum() {
        return productNum;
    }

    /**
     * @param productNum
     */
    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    /**
     * @return product_description
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}