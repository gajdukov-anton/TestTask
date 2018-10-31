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
import com.example.user.holder.CatalogViewHolder;
import com.example.user.model.Category;
import com.example.user.onlineshop.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogViewHolder> {
    private List<Category> categories;
    private LayoutInflater inflater;
    private Callback callback;
    private Context context;

    public CatalogAdapter(Context context, List<Category> categories, Callback callback) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.callback = callback;
    }

    public interface Callback {
        void onClickOnCategory(int position);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    @Override
    public CatalogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_cardview, parent, false);
        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogViewHolder holder, final int position) {
        Category category = categories.get(position);
        ImageView imageView = holder.getCategoryImage();

        setImageToImageView(imageView, category);
        holder.getCategoryTitle().setText(category.getTitle());
        holder.getCategoryFullName().setText(category.getFullName());
        holder.getCategoryView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onClickOnCategory(position);
                }
            }
        });
    }

    private void setImageToImageView(ImageView imageView, Category category) {
        if (category.getImageUrl() == null) {
            imageView.setImageResource(R.drawable.no_photo);
        } else {
            Glide.with(context)
                    .load(category.getImageUrl())
                    .into(imageView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
