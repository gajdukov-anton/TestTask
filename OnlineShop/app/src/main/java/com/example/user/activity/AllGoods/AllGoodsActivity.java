package com.example.user.activity.AllGoods;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.activity.Good.GoodActivity;
import com.example.user.api.ProductApi;
import com.example.user.application.App;
import com.example.user.model.Product;
import com.example.user.onlineshop.R;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AllGoodsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private List<Product> products;
    private RecyclerView recyclerView;
    private AllGoodsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        Bundle arguments = getIntent().getExtras();
        setTitle(arguments.getString("title"));
        categoryId = arguments.getInt("categoryId");
        swipeRefreshLayout = createSwipeRefreshLayout(R.id.allGoodsSwipeRefresh);
        createBackButton();

        try { // Возможно вынести в отдельную функцию
            createRecyclerViewWithProducts(arguments.getInt("categoryId"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        try {
            createRecyclerViewWithProducts(categoryId);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void createBackButton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void createRecyclerViewWithProducts(int id) throws IOException {
        swipeRefreshLayout.setRefreshing(true);
        if (isOnline()) {
            App.getInstance().getProductApi().downloadProductList(this, categoryId, new ProductApi.Callback() {
                @Override
                public void onAllGoodsDownloaded(List<Product> products) {
                    createRecyclerView(products);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onGoodDownloaded(Product product) {

                }

                @Override
                public  void onFailure(String request) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(AllGoodsActivity.this, request, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, "Отсутствует подключение к интеренету", Toast.LENGTH_LONG).show();
        }
    }

    private void createRecyclerView(List<Product> products) {
        this.products = products;
        recyclerView = (RecyclerView) findViewById(R.id.products);
        adapter = new AllGoodsAdapter(AllGoodsActivity.this, products, null);
        AllGoodsAdapter.Callback adapterListener = new AllGoodsAdapter.Callback() {
            @Override
            public void onClickOnGoods(int position) {
                loadGoodActivity(position);
            }
        };
        adapter.setCallback(adapterListener);
        linearLayoutManager = new LinearLayoutManager(AllGoodsActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadGoodActivity(int position) {
        Intent intent = new Intent(this, GoodActivity.class);
        intent.putExtra("productId", products.get(position).getProductId());
        intent.putExtra("title", products.get(position).getTitle());
        startActivity(intent);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
