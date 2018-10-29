package com.example.user.api;

import android.widget.Toast;

import com.example.user.activity.CategoryActivity;
import com.example.user.application.App;
import com.example.user.model.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CategoryApi extends BaseApi {

    private String categoriesParam = "/common/category/list?";
    private Callback callback = null;

    private CategoryApi() {
    }

    private static class CategoryApiHolder {
        private final static CategoryApi instance = new CategoryApi();
    }

    public static CategoryApi getInstance() {
        return CategoryApiHolder.instance;
    }

    private Request createRequestFroDownloadCategories() {
        return new Request.Builder()
                .url(baseUrl + categoriesParam + appKey)
                .build();
    }

    // TODO: 25.10.2018 добавить метод getCategories(Listener) 

    public interface Callback {
        void onCategoriesLoaded(List<Category> categories);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void createRecyclerViewWithCategories(final CategoryActivity categoryActivity) throws IOException { //нужен ли эксепшен

        OkHttpClient client = BaseApi.getClient();
        Request request = createRequestFroDownloadCategories();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(categoryActivity, "Failure", Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                categoryActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            List<Category> categories = getCategoriesFromJson(json.getJSONObject("data"));
                            callback.onCategoriesLoaded(categories);
                        } catch (JSONException e) {
                            Toast.makeText(categoryActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // TODO: 25.10.2018 для маппинга данных использовать библиотеку GSON или LoganSquare
    private List<Category> getCategoriesFromJson(JSONObject data) { //переименовать переменную
        List<Category> categories = new ArrayList<>();
        Gson gson = new Gson();
        try {
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
