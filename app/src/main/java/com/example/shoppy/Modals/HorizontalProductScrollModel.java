package com.example.shoppy.Modals;

public class HorizontalProductScrollModel {

    private String productID;
    private String productImg;
    private String productTitle;
    private String productDescription;
    private String productPrice;

    public HorizontalProductScrollModel(){}

    public HorizontalProductScrollModel(String productID,String productImg, String productTitle, String productDescription, String productPrice) {
        this.productID = productID;
        this.productImg = productImg;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
