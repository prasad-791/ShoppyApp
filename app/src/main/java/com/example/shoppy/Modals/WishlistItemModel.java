package com.example.shoppy.Modals;

public class WishlistItemModel {

    private String productID;
    private String productImg;
    private String productTitle;
    private long freeCoupons;
    private String ratings;
    private long totalRatings;
    private String productPrice;
    private String cuttedPrice;
    private boolean COD;

    public WishlistItemModel(String productID,String productImg, String productTitle, long freeCoupons, String ratings, long totalRatings, String productPrice, String cuttedPrice, boolean COD) {
        this.productID = productID;
        this.productImg = productImg;
        this.productTitle = productTitle;
        this.freeCoupons = freeCoupons;
        this.ratings = ratings;
        this.totalRatings = totalRatings;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.COD = COD;
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
    public long getFreeCoupons() {
        return freeCoupons;
    }
    public void setFreeCoupons(long freeCoupons) {
        this.freeCoupons = freeCoupons;
    }
    public String getRatings() {
        return ratings;
    }
    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
    public long getTotalRatings() {
        return totalRatings;
    }
    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }
    public String getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
    public String getCuttedPrice() {
        return cuttedPrice;
    }
    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }
    public boolean isCOD() {
        return COD;
    }
    public void setCOD(boolean COD) {
        this.COD = COD;
    }
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
}
