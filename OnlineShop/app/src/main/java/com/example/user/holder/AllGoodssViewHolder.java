package com.example.user.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.onlineshop.R;

public class AllGoodssViewHolder extends RecyclerView.ViewHolder {
    private final TextView goodsTitle, goodsPrice;
    private final ImageView goodsImage;
    private final CardView goodsView;

    public AllGoodssViewHolder(View view) {
        super(view);
        goodsView = (CardView) itemView.findViewById(R.id.goodsView);
        goodsTitle = (TextView) view.findViewById(R.id.goodsTitle);
        goodsPrice = (TextView) view.findViewById(R.id.goodsPrice);
        goodsImage = (ImageView) view.findViewById(R.id.goodsImage);

    }

    public TextView getGoodsPrice() {
        return goodsPrice;
    }

    public CardView getGoodsView() {
        return goodsView;
    }

    public ImageView getGoodsImage() {
        return goodsImage;
    }

    public TextView getGoodsTitle() {
        return goodsTitle;
    }
}
