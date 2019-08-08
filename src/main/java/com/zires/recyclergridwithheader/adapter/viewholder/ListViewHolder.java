package com.zires.recyclergridwithheader.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zires.recyclergridwithheader.R;

public class ListViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView title, oldPrice, newPrice;



    public ListViewHolder(View view) {
        super(view);
        img = view.findViewById(R.id.list_item_image_view);
        title = view.findViewById(R.id.list_item_title);
        oldPrice = view.findViewById(R.id.list_item_old_price);
        newPrice = view.findViewById(R.id.list_item_new_price);
    }
}
