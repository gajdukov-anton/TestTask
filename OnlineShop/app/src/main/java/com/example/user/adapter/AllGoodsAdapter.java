package com.example.user.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.user.holder.AllGoodssViewHolder;
import com.example.user.model.Product;
import com.example.user.onlineshop.R;

import java.util.List;

public class AllGoodsAdapter extends RecyclerView.Adapter<AllGoodssViewHolder> {
    private List<Product> products;
    private LayoutInflater inflater;
    private AllGoodsAdapter.Callback callback;
    private Context context;

    public AllGoodsAdapter(Context context, List<Product> products, Callback callback) {
        this.products = products;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.callback = callback;
    }

    public interface Callback {
        void onClickOnGoods(int position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public AllGoodssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.all_goods_cardview, parent, false);
        return new AllGoodssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllGoodssViewHolder holder, final int position) {
        Product product = products.get(position);
        ImageView imageView = holder.getGoodsImage();
        setImageToImageView(imageView, product);
        holder.getGoodsTitle().setText(product.getTitle());
        holder.getGoodsPrice().setText(product.getPrice());
        holder.getGoodsView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onClickOnGoods(position);
                }
            }
        });
    }


    private void setImageToImageView(ImageView imageView, Product product) {
        if (product.getImageUrl() == null) {
           imageView.setImageResource(R.drawable.no_photo);
        } else {
            Glide.with(context)
                    .load(product.getImageUrl())
                    .into(imageView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
