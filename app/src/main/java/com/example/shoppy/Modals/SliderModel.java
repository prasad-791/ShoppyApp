package com.example.shoppy.Modals;

import android.widget.ImageView;

public class SliderModel {
    private String banner;
    private String bg_color;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public SliderModel(){}

    public SliderModel(String banner, String bg_color) {
        this.banner = banner;
        this.bg_color = bg_color;
    }
}
