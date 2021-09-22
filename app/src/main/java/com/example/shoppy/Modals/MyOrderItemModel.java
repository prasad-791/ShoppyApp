package com.example.shoppy.Modals;

public class MyOrderItemModel {
    private int productImg;
    private int rating;
    private String productTitle;
    private String deliveryStatus;


    public MyOrderItemModel(int productImg,int rating, String productTitle, String deliveryStatus) {
        this.productImg = productImg;
        this.rating = rating;
        this.productTitle = productTitle;
        this.deliveryStatus = deliveryStatus;
    }

    public int getProductImg() {
        return productImg;
    }
    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }
    public String getProductTitle() {
        return productTitle;
    }
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    public String getDeliveryStatus() {
        return deliveryStatus;
    }
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
