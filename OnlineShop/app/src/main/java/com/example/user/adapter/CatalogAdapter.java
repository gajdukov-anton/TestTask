package com.example.user.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.activity.CatalogActivity;
import com.example.user.objects.Category;
import com.example.user.onlineshop.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    private List<Category> categories;
    private LayoutInflater inflater;
    private Context context;

    public CatalogAdapter(Context context, List<Category> categories) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_catalog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        ImageView imageView = holder.categoryImage;

        Glide.with(context)
                .load(category.getImageUrl())
                .into(imageView);
        holder.categoryTitle.setText(category.getTitle());
        holder.categoryFullName.setText(category.getFullName());
        //holder.categoryDescription.setText(category.getCategoryDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryTitle, categoryFullName;
                //categoryDescription;
        final ImageView categoryImage;
        final CardView categoryView;

        ViewHolder(View view) {
            super(view);
            categoryView = (CardView)itemView.findViewById(R.id.categoryView);
            categoryTitle = (TextView) view.findViewById(R.id.categoryTitle);
            categoryFullName = (TextView) view.findViewById(R.id.categoryFullName);
            //categoryDescription = (TextView) view.findViewById(R.id.categoryDescription);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
        }
    }
}
