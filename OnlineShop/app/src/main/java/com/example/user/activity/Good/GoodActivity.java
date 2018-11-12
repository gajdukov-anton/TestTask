
package com.example.user.activity.Good;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.api.ProductApi;
import com.example.user.application.App;
import com.example.user.model.Product;
import com.example.user.onlineshop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GoodActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private int productId;
    private Product product;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        Bundle arguments = getIntent().getExtras();
        setTitle(arguments.getString("title"));
        productId = arguments.getInt("productId");
        swipeRefreshLayout = createSwipeRefreshLayout(R.id.goodSwipeRefresh);
        createBackButton();
        createProductView();
    }

    @Override
    public void onRefresh() {
        createProductView();
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

    private void createProductView() {
        swipeRefreshLayout.setRefreshing(true);
        if (isOnline()) {
            App.getInstance().getProductApi().downloadProduct(this, productId, new ProductApi.Callback() {
                @Override
                public void onAllGoodsDownloaded(List<Product> products) {
                }

                @Override
                public void onGoodDownloaded(Product product) {
                    createProductView(product);
                    swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(String request) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            Toast.makeText(this, "Отсутствует подключение к интеренету", Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    private void createProductView(Product product) {
        this.product = product;
        ImageView imageView = (ImageView) findViewById(R.id.imageGood);
        TextView textView = (TextView) findViewById(R.id.titleGood);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingGood);
        textView.setText(product.getTitle());
        textView = (TextView) findViewById(R.id.priceGood);
        textView.setText(product.getPrice());
        ratingBar.setRating(Float.parseFloat(product.getRating()));
        textView = (TextView) findViewById(R.id.descriptionGood);
        textView.setText(product.getProductDescription());
        setImageToImageView(imageView, product);
    }

    public void buyGood(View view) {
        Toast.makeText(this, "Куплено", Toast.LENGTH_SHORT).show();
    }

    private void setImageToImageView(ImageView imageView, Product product) {
        if (product.getImageUrl() == null) {
            imageView.setImageResource(R.drawable.no_photo);
        } else {
            Glide.with(this)
                    .load(product.getImageUrl())
                    .into(imageView);
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
