package com.zires.recyclergridwithheader.adapter.viewholder;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zires.recyclergridwithheader.R;

public class GridViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView title, oldPrice, newPrice;


    public GridViewHolder(View view) {
        super(view);
        img = view.findViewById(R.id.grid_item_image_view);
        title = view.findViewById(R.id.grid_item_title);
        oldPrice = view.findViewById(R.id.grid_item_old_price);
        newPrice = view.findViewById(R.id.grid_item_new_price);
    }
}
