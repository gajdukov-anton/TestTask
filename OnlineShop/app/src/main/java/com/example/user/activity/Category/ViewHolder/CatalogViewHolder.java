package com.example.user.activity.Category.ViewHolder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.onlineshop.R;

public class CatalogViewHolder extends RecyclerView.ViewHolder {
    private final TextView categoryTitle, categoryFullName;
    private final ImageView categoryImage;
    private final CardView categoryView;

    public CatalogViewHolder(View view) {
        super(view);
        categoryView = (CardView)itemView.findViewById(R.id.categoryView);
        categoryTitle = (TextView) view.findViewById(R.id.categoryTitle);
        categoryFullName = (TextView) view.findViewById(R.id.categoryFullName);
        categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
    }

    public CardView getCategoryView() {
        return categoryView;
    }

    public ImageView getCategoryImage() {
        return categoryImage;
    }

    public TextView getCategoryFullName() {
        return categoryFullName;
    }

    public TextView getCategoryTitle() {
        return categoryTitle;
    }

}
