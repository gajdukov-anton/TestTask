package com.example.user.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.objects.Category;
import com.example.user.onlineshop.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
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
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder holder, final int position) {
        Category category = categories.get(position);
        ImageView imageView = holder.categoryImage;

        Glide.with(context)
                .load(category.getImageUrl())
                .into(imageView);
        holder.categoryTitle.setText(category.getTitle());
        holder.categoryFullName.setText(category.getFullName());
        holder.categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(view.getContext(), "Отслеживается нажатие на карточку", Toast.LENGTH_SHORT);
                if (callback != null) {
                    callback.onClickOnCategory(position);
                }
            }
        });
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
        final ImageView categoryImage;
        final CardView categoryView;

        ViewHolder(View view) {
            super(view);
            categoryView = (CardView)itemView.findViewById(R.id.categoryView);
            categoryTitle = (TextView) view.findViewById(R.id.categoryTitle);
            categoryFullName = (TextView) view.findViewById(R.id.categoryFullName);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);

        }
    }
}
