package com.example.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.adapter.CatalogAdapter;
import com.example.user.api.BaseApi;
import com.example.user.api.CategoryApi;
import com.example.user.application.App;
import com.example.user.objects.Category;
import com.example.user.onlineshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CatalogActivity extends AppCompatActivity {
    private List<Category> categories;
    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        setTitle(R.string.catalog_activity_name);
        try {
            createRecyclerViewWithCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void createRecyclerViewWithCategories() throws IOException {

        OkHttpClient client = BaseApi.getClient();
        CategoryApi categoryApi = App.getCategoryApi();
        Request request = categoryApi.createRequestFroDowloadCategories();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(CatalogActivity.this, "", Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                CatalogActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            getCategoriesFromJson(json.getJSONObject("data"));
                            createRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void createRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.categories);
        adapter = new CatalogAdapter(CatalogActivity.this, categories, null);
        CatalogAdapter.Callback adapterListener = new CatalogAdapter.Callback() {
            @Override
            public void onClickOnCategory(int position) {
                loadAllGoodsActivity(position);
            }
        };
        adapter.setCallback(adapterListener);
        llm = new LinearLayoutManager(CatalogActivity.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void loadAllGoodsActivity(int position) {
        Intent intent = new Intent(this, AllGoodsActivity.class);
        intent.putExtra("categoryId", categories.get(position).getCategoryId());
        intent.putExtra("title", categories.get(position).getTitle());
        startActivity(intent);
    }

    private void getCategoriesFromJson(JSONObject data) {
        categories = new ArrayList<>();
        try {
            JSONArray jsonArray = data.getJSONArray("categories");
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject jsonCategory = new JSONObject(jsonArray.get(i).toString());
                    Category category = new Category(jsonCategory.getInt("categoryId"),
                            jsonCategory.getString("title"),
                            jsonCategory.getString("imageUrl"),
                            jsonCategory.getInt("hasSubcategories"),
                            jsonCategory.getString("fullName"),
                            jsonCategory.getString("categoryDescription"));

                    categories.add(category);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
