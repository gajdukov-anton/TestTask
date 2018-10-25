
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
import com.example.user.api.BaseApi;
import com.example.user.api.ProductApi;
import com.example.user.application.App;
import com.example.user.objects.Product;
import com.example.user.onlineshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        try {
            createProductFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void createProductFromServer() throws IOException {
        OkHttpClient client = BaseApi.getClient();
        ProductApi productApi = App.getProductApi();
        Request request = productApi.createRequestForDownloadProduct(productId);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(GoodActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                GoodActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            getProductFromJson(json);
                            createProductView();
                        } catch (JSONException e) {
                            Toast.makeText(GoodActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void getProductFromJson(JSONObject data) {
        try {
            JSONObject json = data.getJSONObject("data");
            if (json != null) {
                Product product = new Product();
                product.setProductId(json.getInt("productId"));
                product.setTitle(json.getString("title"));
                product.setImageUrl(json.getString("imageUrl"));
                product.setPrice(json.getString("price"));
                product.setRating(json.getString("rating"));
                product.setProductDescription(json.getString("productDescription"));
                this.product = product;

            } else {
                Toast.makeText(GoodActivity.this, "jsonArray is null", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {

            Toast.makeText(GoodActivity.this, data.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void createProductView() {
        ImageView imageView = (ImageView) findViewById(R.id.imageGood);
        TextView textView = (TextView) findViewById(R.id.titleGood);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingGood);
        textView.setText(product.getTitle());
        textView = (TextView) findViewById(R.id.priceGood);
        textView.setText(product.getPrice());
        ratingBar.setRating(Float.parseFloat(product.getRating()));
        textView = (TextView) findViewById(R.id.descriptionGood);
        textView.setText("Описание: " + product.getProductDescription());

//        Glide.with(this)
//                .load(product.getImageUrl())
//                .into(imageView);
        setImageToImageView(imageView, product);

    }

    private void setImageToImageView(ImageView imageView, Product product) {
        if (product.getImageUrl().equals("null")) {
            imageView.setImageResource(R.drawable.no_photo);
        } else {
            Glide.with(this)
                    .load(product.getImageUrl())
                    .into(imageView);
        }
    }

}
