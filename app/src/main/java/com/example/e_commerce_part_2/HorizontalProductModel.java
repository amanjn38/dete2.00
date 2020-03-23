package com.example.e_commerce_part_2;

public class HorizontalProductModel {
    private int productImage;
    private String product, description, price;

    public HorizontalProductModel(int productImage, String product, String description, String price) {
        this.productImage = productImage;
        this.product = product;
        this.description = description;
        this.price = price;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
