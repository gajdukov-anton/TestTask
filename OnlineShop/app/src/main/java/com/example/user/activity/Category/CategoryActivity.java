package com.example.user.activity.Category;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.activity.AllGoods.AllGoodsActivity;
import com.example.user.api.CategoryApi;
import com.example.user.application.App;
import com.example.user.model.Category;
import com.example.user.onlineshop.R;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private List<Category> categories;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CatalogAdapter adapter;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        setTitle(R.string.catalog_activity_name);
        swipeRefreshLayout = createSwipeRefreshLayout(R.id.catalogSwipeRefresh);
        createRecyclerViewWithCategories();
    }

    @Override
    public void onRefresh() {
        createRecyclerViewWithCategories();
    }

    private SwipeRefreshLayout createSwipeRefreshLayout(int id) {
        SwipeRefreshLayout newSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(id);
        newSwipeRefreshLayout.setOnRefreshListener(this);
        newSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return newSwipeRefreshLayout;
    }

    void createRecyclerViewWithCategories() {
        swipeRefreshLayout.setRefreshing(true);
        if (isOnline()) {
            App.getInstance().getCategoryApi().downloadCategoriesList(this, new CategoryApi.Callback() {
                @Override
                public void onCategoriesDownloaded(List<Category> categories) {
                    createRecyclerView(categories);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public  void onFailure(String request) {
                    Toast.makeText(CategoryActivity.this, request, Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);

                }
            });
        } else {
            Toast.makeText(this, "Отсутствует подключение к интеренету", Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);

        }
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

    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
