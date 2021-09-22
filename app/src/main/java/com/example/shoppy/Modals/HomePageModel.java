package com.example.shoppy.Modals;

import java.util.List;

public class HomePageModel {

    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;

    private int type;
    private String bgColor;
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getBgColor() {
        return bgColor;
    }
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    //////// Banner Slider
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //////// Banner Slider

    //////// Strip Ad
    private String resource;

    public HomePageModel(int type, String resource, String bgColor) {
        this.type = type;
        this.resource = resource;
        this.bgColor = bgColor;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    //////// Strip Ad


    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    //////// Grid Layout
    public HomePageModel(int type, String title,String bgColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.bgColor = bgColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    //////// Grid Layout

    //////// Horizontal Product
    private List<WishlistItemModel> viewAllProductList;

    public HomePageModel(int type, String title,String bgColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList,List<WishlistItemModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.bgColor = bgColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductList = viewAllProductList;
    }
    public List<WishlistItemModel> getViewAllProductList() {
        return viewAllProductList;
    }
    public void setViewAllProductList(List<WishlistItemModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }
    //////// Horizontal Product

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
}
