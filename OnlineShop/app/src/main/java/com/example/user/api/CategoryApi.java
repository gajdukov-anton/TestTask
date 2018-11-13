package com.example.user.api;

import android.widget.Toast;

import com.example.user.activity.Category.CategoryActivity;
import com.example.user.model.Category;
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

public class CategoryApi extends BaseApi {
    // TODO: 07.11.2018 убрать static
    private String categoriesParam = "/common/category/list?";

    public CategoryApi() {
    }

    public interface Callback {
        void onCategoriesDownloaded(List<Category> categories);

        void onFailure(String request);
    }

    // TODO: 07.11.2018 слушатель передавать в метод


    public void downloadCategoriesList(final CategoryActivity categoryActivity, final Callback callback) {
        sendRequest(baseUrl + categoriesParam + appKey, categoryActivity, new Listener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                List<Category> categories = getCategoriesFromJson(jsonObject);
                callback.onCategoriesDownloaded(categories);
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

    private List<Category> getCategoriesFromJson(JSONObject data) {
        List<Category> categories = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JSONArray jsonArray = data.getJSONArray("categories");
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    Category category = gson.fromJson(jsonArray.get(i).toString(), Category.class);
                    categories.add(category);
                }
            }
            return categories;
        } catch (JSONException e) {
            e.printStackTrace();
            return categories;
        }
    }
}
