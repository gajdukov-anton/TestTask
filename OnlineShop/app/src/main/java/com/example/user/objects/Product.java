package com.example.user.objects;

public class Product {
    private int productId;
    private String title;
    private String productDescription;
    private String price = "0";
    private String rating;
    private String imageUrl;
    private String[] images;

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

    public void setImages(String[] images) {
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
        if (price.equals("null")) {
            return "нет в продаже";
        } else {
            return price + " руб";

        }
    }

    public String getRating() {
        if (rating.equals("null"))
            return "0";
        else {
            return rating;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String[] getImages() {
        return images;
    }
}
