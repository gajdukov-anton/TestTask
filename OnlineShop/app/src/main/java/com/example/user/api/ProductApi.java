package com.example.user.api;

import com.example.user.objects.Product;

import okhttp3.Request;

public class ProductApi extends BaseApi {

    private Request request;

    private ProductApi() {}

    private static class ProductApiHolder {
        private final static ProductApi instance = new ProductApi();
    }

    public static ProductApi getInstance() {
        return ProductApiHolder.instance;
    }

    private String productParam = "/common/product/list";

    public Request createRequestForDownloadProducts() {
        return new Request.Builder()
                .url(baseUrl + productParam + appKey)
                .build();
    }
}
