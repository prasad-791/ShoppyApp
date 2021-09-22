package com.example.shoppy.Modals;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    ////////// Cart Item
    private int product_img;
    private int free_coupons;
    private int product_quantity;
    private int offers_applied;
    private int coupons_applied;
    private String product_title;
    private String product_price;
    private String cutted_price;

    public CartItemModel(int type, int product_img, int free_coupons, int product_quantity, int offers_applied, int coupons_applied, String product_title, String product_price, String cutted_price) {
        this.type = type;
        this.product_img = product_img;
        this.free_coupons = free_coupons;
        this.product_quantity = product_quantity;
        this.offers_applied = offers_applied;
        this.coupons_applied = coupons_applied;
        this.product_title = product_title;
        this.product_price = product_price;
        this.cutted_price = cutted_price;
    }

    public int getProduct_img() {
        return product_img;
    }
    public void setProduct_img(int product_img) {
        this.product_img = product_img;
    }
    public int getFree_coupons() {
        return free_coupons;
    }
    public void setFree_coupons(int free_coupons) {
        this.free_coupons = free_coupons;
    }
    public int getProduct_quantity() {
        return product_quantity;
    }
    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
    public int getOffers_applied() {
        return offers_applied;
    }
    public void setOffers_applied(int offers_applied) {
        this.offers_applied = offers_applied;
    }
    public int getCoupons_applied() {
        return coupons_applied;
    }
    public void setCoupons_applied(int coupons_applied) {
        this.coupons_applied = coupons_applied;
    }
    public String getProduct_title() {
        return product_title;
    }
    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }
    public String getProduct_price() {
        return product_price;
    }
    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
    public String getCutted_price() {
        return cutted_price;
    }
    public void setCutted_price(String cutted_price) {
        this.cutted_price = cutted_price;
    }
    ////////// Cart Item

    ////////// Cart Total Amount
    private int total_items;
    private String total_items_price;
    private String delivery_price;
    private String saved_price;
    private String totalAmount;

    public CartItemModel(int type, int total_items, String total_items_price, String delivery_price,String totalAmount, String saved_price) {
        this.type = type;
        this.total_items = total_items;
        this.total_items_price = total_items_price;
        this.delivery_price = delivery_price;
        this.totalAmount = totalAmount;
        this.saved_price = saved_price;
    }

    public int getTotal_items() {
        return total_items;
    }
    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }
    public String getTotal_items_price() {
        return total_items_price;
    }
    public void setTotal_items_price(String total_items_price) {
        this.total_items_price = total_items_price;
    }
    public String getDelivery_price() {
        return delivery_price;
    }
    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }
    public String getSaved_price() {
        return saved_price;
    }
    public void setSaved_price(String saved_price) {
        this.saved_price = saved_price;
    }
    public String getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    ////////// Cart Total Amount
}
