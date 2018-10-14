package com.example.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.objects.Category;
import com.example.user.onlineshop.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    private List<Category> categories;
    private LayoutInflater inflater;

    public CatalogAdapter(Context context, List<Category> categories) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_catalog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryTitle.setText(category.getTitle());
        holder.categoryFullName.setText(category.getFullName());
        holder.categoryDescription.setText(category.getCategoryDescription());
    }



    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryTitle, categoryFullName,
                categoryDescription;

        ViewHolder(View view) {
            super(view);
            categoryTitle = (TextView) view.findViewById(R.id.categoryTitle);
            categoryFullName = (TextView) view.findViewById(R.id.categoryFullName);
            categoryDescription = (TextView) view.findViewById(R.id.categoryDescription);
        }
    }
}
