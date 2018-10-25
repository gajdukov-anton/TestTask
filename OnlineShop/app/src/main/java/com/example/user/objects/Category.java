package com.example.user.objects;

import com.example.user.onlineshop.R;

public class Category {
    private int categoryId;
    private String title;
    private String imageUrl;
    private int hasSubCategories;
    private String fullName;
    private String categoryDescription;
    private int imageId;

    public Category() {}

    public Category(int categoryId, String title, String imageUrl, int hasSubCategories,
                    String fullName, String categoryDescription) {
        this.categoryId = categoryId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.hasSubCategories = hasSubCategories;
        this.fullName = fullName;
        this.categoryDescription = categoryDescription;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setHasSubCategories(int hasSubCategories) {
        this.hasSubCategories = hasSubCategories;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getImageId() {
        if (imageUrl == null) {
            return(R.drawable.no_photo);
        }
        return imageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getHasSubCategories() {
        return hasSubCategories;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }
}
