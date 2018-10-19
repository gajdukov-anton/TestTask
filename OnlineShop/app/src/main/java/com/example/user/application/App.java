package com.example.user.application;

import com.example.user.api.BaseApi;
import com.example.user.api.CategoryApi;
import com.example.user.api.ProductApi;

public class App {
    private static CategoryApi categoryApi;
    private static BaseApi baseApi;

    public static BaseApi getBaseApi() {
        return BaseApi.getInstance();
    }

    public static CategoryApi getCategoryApi() {
        return CategoryApi.getInstance();
    }

    public static ProductApi getProductApi() {
        return ProductApi.getInstance();
    }
}
