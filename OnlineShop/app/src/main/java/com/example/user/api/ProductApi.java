package com.example.user.api;

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

    private String productsApi = "/common/product/list?";
    private String productApi = "/common/product/details?";
    private String productParamProductId = "productId=";
    private String productsParamCategoryId = "categoryId=";

    public Request createRequestForDownloadProducts() {
        return new Request.Builder()
                .url(baseUrl + productsApi + appKey)
                .build();
    }

    public Request createRequestForDownloadProducts(int categoryId) {
        return new Request.Builder()
                .url(baseUrl + productsApi + productsParamCategoryId + String.valueOf(categoryId) + "&" + appKey)
                .build();
    }

    public Request createRequestForDownloadProduct(int productId) {
        return new Request.Builder()
                .url(baseUrl + productApi + productParamProductId + String.valueOf(productId) + "&" + appKey)
                .build();
    }
}
