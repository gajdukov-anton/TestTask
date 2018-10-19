package com.example.user.api;

        import okhttp3.Request;

public class CategoryApi extends BaseApi {

    private CategoryApi() {}
    private static class CategoryApiHolder {
        private final static CategoryApi instance = new CategoryApi();
    }

    public static CategoryApi getInstance() {
        return CategoryApiHolder.instance;
    }

    private Request request;
    private String categoriesParam = "/common/category/list";

    public Request createRequestFroDowloadCategories() {
        return new Request.Builder()
                .url(baseUrl + categoriesParam + appKey)
                .build();
    }
}
