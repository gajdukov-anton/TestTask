package com.example.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.adapter.CatalogAdapter;
import com.example.user.api.CategoryApi;
import com.example.user.application.App;
import com.example.user.model.Category;
import com.example.user.onlineshop.R;

import java.io.IOException;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
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
        CategoryApi categoryApi = App.getCategoryApi();
        CategoryApi.Callback categoryApiListener = new CategoryApi.Callback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                createRecyclerView(categories);
            }
        };
        categoryApi.setCallback(categoryApiListener);
        categoryApi.createRecyclerViewWithCategories(this);
    }

    private void createRecyclerView(List<Category> categories) {
        this.categories = categories;
        recyclerView = (RecyclerView)findViewById(R.id.categories);
        adapter = new CatalogAdapter(CategoryActivity.this, categories, null);
        CatalogAdapter.Callback adapterListener = new CatalogAdapter.Callback() {
            @Override
            public void onClickOnCategory(int position) {
                loadAllGoodsActivity(position);
            }
        };
        adapter.setCallback(adapterListener);
        llm = new LinearLayoutManager(CategoryActivity.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void loadAllGoodsActivity(int position) {
        Intent intent = new Intent(this, AllGoodsActivity.class);
        intent.putExtra("categoryId", categories.get(position).getCategoryId());
        intent.putExtra("title", categories.get(position).getTitle());
        startActivity(intent);
    }
}
