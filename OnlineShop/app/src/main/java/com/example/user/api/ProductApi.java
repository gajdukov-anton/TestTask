package com.example.user.api;

import android.widget.Toast;

import com.example.user.activity.AllGoods.AllGoodsActivity;
import com.example.user.activity.Good.GoodActivity;
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
    private String productsApi = "/common/product/list?";
    private String productApi = "/common/product/details?";
    private String productParamProductId = "productId=";
    private String productsParamCategoryId = "categoryId=";

    public ProductApi() {
    }

    public interface Callback {
        void onAllGoodsDownloaded(List<Product> products);

        void onFailure(String request);

        void onGoodDownloaded(Product product);
    }

    public void downloadProductList(final AllGoodsActivity allGoodsActivity, int categoryId, final Callback callback) {
        sendRequest(baseUrl + productsApi + productsParamCategoryId + String.valueOf(categoryId) + "&" + appKey, allGoodsActivity, new Listener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
            }

            @Override
            public void onSuccess(JSONArray jsonArray) {
                List<Product> products = getProductsFromJson(jsonArray);
                callback.onAllGoodsDownloaded(products);
            }

            @Override
            public void onFailure(String request) {
                callback.onFailure(request);
            }
        });
    }

    private List<Product> getProductsFromJson(JSONArray jsonArray) {
        List<Product> products = new ArrayList<>();
        try {
            Gson gson = new Gson();
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

    public void downloadProduct(final GoodActivity goodActivity, int productId, final Callback callback) {
        sendRequest(baseUrl + productApi + productParamProductId + String.valueOf(productId) + "&" + appKey, goodActivity, new Listener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Product product = getProductFromJson(jsonObject);
                callback.onGoodDownloaded(product);
            }

            @Override
            public void onSuccess(JSONArray jsonArray) {

            }

            @Override
            public void onFailure(String request) {
                callback.onFailure(request);
            }
        });
    }

    private Product getProductFromJson(JSONObject data) {
        Gson gson = new Gson();
        return gson.fromJson(data.toString(), Product.class);
    }
}
