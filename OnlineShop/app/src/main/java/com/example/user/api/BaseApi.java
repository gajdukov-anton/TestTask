package com.example.user.api;

import okhttp3.OkHttpClient;

public class BaseApi {

    private static class BaseApiHolder {
        private final static BaseApi instance = new BaseApi();
    }

    public static BaseApi getInstance() {
        return BaseApiHolder.instance;
    }

    protected BaseApi() {
    }

    protected String appKey = "appKey=yx-1PU73oUj6gfk0hNyrNUwhWnmBRld7-SfKAU7Kg6Fpp43anR261KDiQ-MY4P2SRwH_cd4Py1OCY5jpPnY_Viyzja-s18njTLc0E7XcZFwwvi32zX-B91Sdwq1KeZ7m";
    protected String baseUrl = "http://onlinestore.whitetigersoft.ru/api";
    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client != null) {
            return client;
        } else {
            client = new OkHttpClient();
            return client;
        }
    }
}
