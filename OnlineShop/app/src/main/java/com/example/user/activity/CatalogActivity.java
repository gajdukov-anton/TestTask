package com.example.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.adapter.CatalogAdapter;
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
    OkHttpClient client = new OkHttpClient();
    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private LinearLayoutManager llm;
    private static String url = "http://82.146.53.185:8101/api/common/category/list?appKey=yx-1PU73oUj6gfk0hNyrNUwhWnmBRld7-SfKAU7Kg6Fpp43anR261KDiQ-MY4P2SRwH_cd4Py1OCY5jpPnY_Viyzja-s18njTLc0E7XcZFwwvi32zX-B91Sdwq1KeZ7m";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        try {
            createRecyclerViewWithCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void createRecyclerViewWithCategories() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(CatalogActivity.this, call.toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(CatalogActivity.this, categories.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                            recyclerView = (RecyclerView) findViewById(R.id.categories);
                            adapter = new CatalogAdapter(CatalogActivity.this, categories);
                            llm = new LinearLayoutManager(CatalogActivity.this);
                            recyclerView.setLayoutManager(llm);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void setImageView(String imageUrl) {
        ImageView imageView = (ImageView) findViewById(R.id.categoryImage);

        Glide.with(CatalogActivity.this).load(imageUrl).into(imageView);

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

        }
    }


}
