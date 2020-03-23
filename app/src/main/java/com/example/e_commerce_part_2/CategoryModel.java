package com.example.e_commerce_part_2;

public class CategoryModel {

    private String link;
    private String categoryName;

    public CategoryModel(String link, String categoryName) {
        this.link = link;
        this.categoryName = categoryName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
