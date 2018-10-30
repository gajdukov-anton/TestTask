package com.example.user.api;

import android.widget.Toast;

import com.example.user.activity.AllGoodsActivity;
import com.example.user.activity.GoodActivity;
import com.example.user.model.Product;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductApi extends BaseApi {

    private Request request;
    private Callback callback;
    private String productsApi = "/common/product/list?";
    private String productApi = "/common/product/details?";
    private String productParamProductId = "productId=";
    private String productsParamCategoryId = "categoryId=";

    private ProductApi() {
    }

    private static class ProductApiHolder {
        private final static ProductApi instance = new ProductApi();
    }

    public static ProductApi getInstance() {
        return ProductApiHolder.instance;
    }

    public interface Callback {
        void onAllGoodsDownloaded(List<Product> products);

        void onGoodDownloaded(Product product);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private Request createRequestForDownloadProducts(int categoryId) {
        return new Request.Builder()
                .url(baseUrl + productsApi + productsParamCategoryId + String.valueOf(categoryId) + "&" + appKey)
                .build();
    }

    public Request createRequestForDownloadProduct(int productId) {
        return new Request.Builder()
                .url(baseUrl + productApi + productParamProductId + String.valueOf(productId) + "&" + appKey)
                .build();
    }

    public void downloadProductList(final AllGoodsActivity allGoodsActivity, int categoryId) {
        OkHttpClient client = BaseApi.getClient();
        Request request = createRequestForDownloadProducts(categoryId);

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                allGoodsActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            List<Product> products = getProductsFromJson(json);
                            callback.onAllGoodsDownloaded(products);
                        } catch (JSONException e) {
                            Toast.makeText(allGoodsActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // TODO: 25.10.2018 для маппинга данных использовать библиотеку GSON или LoganSquare
    private List<Product> getProductsFromJson(JSONObject data) {
        List<Product> products = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JSONArray jsonArray = data.getJSONArray("data");
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    Product product = gson.fromJson(jsonArray.get(i).toString(), Product.class);
                    products.add(product);
                }
            }
            return products;
        } catch (JSONException e) {
            e.printStackTrace();
            return products;
        }
    }

    public void downloadProduct(final GoodActivity goodActivity, int productId) {
        OkHttpClient client = BaseApi.getClient();
        Request request = createRequestForDownloadProduct(productId);

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                goodActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            Product product = getProductFromJson(json.getJSONObject("data"));
                            callback.onGoodDownloaded(product);
                        } catch (JSONException e) {
                            Toast.makeText(goodActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // TODO: 25.10.2018 для маппинга данных использовать библиотеку GSON или LoganSquare
    private Product getProductFromJson(JSONObject data) {
        Gson gson = new Gson();
        return gson.fromJson(data.toString(), Product.class);
    }
}
