package com.example.user.model;

import java.util.List;

public class Product {
    private int productId;
    private String title;
    private String productDescription;
    private String price = "0";
    private String rating;
    private String imageUrl;
    // TODO: 25.10.2018 вместо массивов лучше использовать списки
    private List<String> images;

    public Product() {

    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getPrice() {
        if (price == null) {
            return "нет в продаже";
        } else {
            return price + " руб";

        }
    }

    public String getRating() {
        if (rating == null)
            return "0";
        else {
            return rating;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getImages() {
        return images;
    }
}
