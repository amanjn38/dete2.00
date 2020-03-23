package com.example.e_commerce_part_2;

public class SliderModel {

    private int banner;
    private String background_color;

    public SliderModel(int banner, String background_color) {
        this.banner = banner;
        this.background_color = background_color;
    }

    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }
}
