
package com.example.user.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class GoodActivity extends AppCompatActivity {

    private int productId;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        Bundle arguments = getIntent().getExtras();
        setTitle(arguments.getString("title"));
        productId = arguments.getInt("productId");
        createBackButton();
        createProductView();
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
        ProductApi productApi = App.getProductApi();
        ProductApi.Callback productApiListener = new ProductApi.Callback() {
            @Override
            public void onAllGoodsDownloaded(List<Product> products) {
            }

            @Override
            public void onGoodDownloaded(Product product) {
                createProductView(product);
            }
        };
        productApi.setCallback(productApiListener);
        productApi.downloadProduct(this, productId);
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
        textView.setText("Описание: " + product.getProductDescription());
        setImageToImageView(imageView, product);
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
}
