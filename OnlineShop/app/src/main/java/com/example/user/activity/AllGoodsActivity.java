package com.example.user.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.adapter.AllGoodsAdapter;
import com.example.user.api.BaseApi;
import com.example.user.api.ProductApi;
import com.example.user.application.App;
import com.example.user.model.Product;
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

public class AllGoodsActivity extends AppCompatActivity {
    private List<Product> products;
    private RecyclerView recyclerView;
    private AllGoodsAdapter adapter;
    private LinearLayoutManager llm;
    private int categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        Bundle arguments = getIntent().getExtras();
        setTitle(arguments.getString("title"));
        categoryId = arguments.getInt("categoryId");

        try {
            createRecyclerViewWithProducts(arguments.getInt("categoryId"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        createBackButton();
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
        ProductApi productApi = App.getProductApi();
        ProductApi.Callback productApiListener = new ProductApi.Callback() {
            @Override
            public void onAllGoodsDownloaded(List<Product> products) {
                createRecyclerView(products);
            }

            @Override
            public void onGoodDownloaded(Product product) {

            }
        };
        productApi.setCallback(productApiListener);
        productApi.downloadProductList(this, categoryId);
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
        llm = new LinearLayoutManager(AllGoodsActivity.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void loadGoodActivity(int position) {
        Intent intent = new Intent(this, GoodActivity.class);
        intent.putExtra("productId", products.get(position).getProductId());
        intent.putExtra("title", products.get(position).getTitle());
        startActivity(intent);
    }
}
