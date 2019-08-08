package com.zires.recyclergridwithheader.adapter.viewholder;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zires.recyclergridwithheader.R;

/**
 * Created by ClassicZires on 6/13/2019.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public AppCompatImageView img;
    public TextView title;
    public TextView info;
    public RelativeLayout more;


    public HeaderViewHolder(View view) {
        super(view);
        img = view.findViewById(R.id.header_img);
        title = view.findViewById(R.id.header_title);
        info = view.findViewById(R.id.header_info);
        more = view.findViewById(R.id.header_more);
    }
}
