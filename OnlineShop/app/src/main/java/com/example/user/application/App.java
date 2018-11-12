package com.example.user.application;

import com.example.user.api.BaseApi;
import com.example.user.api.CategoryApi;
import com.example.user.api.ProductApi;

public class App {

    private static App instance;
    private  CategoryApi categoryApi;
    private ProductApi productApi;
    private  BaseApi baseApi;

    private App() {
        categoryApi = new CategoryApi();
        productApi = new ProductApi();

    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public  BaseApi getBaseApi() {
        return baseApi;
    }

    public CategoryApi getCategoryApi() {
        return categoryApi;
    }

    public ProductApi getProductApi() {
        return productApi;
    }
}
