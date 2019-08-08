package com.zires.recyclergridwithheader.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zires.recyclergridwithheader.R;
import com.zires.recyclergridwithheader.RecyclerHeaderModel;
import com.zires.recyclergridwithheader.RecyclerItemModel;
import com.zires.recyclergridwithheader.adapter.viewholder.GridViewHolder;
import com.zires.recyclergridwithheader.adapter.viewholder.HeaderViewHolder;
import com.zires.recyclergridwithheader.adapter.viewholder.ListViewHolder;
import com.zires.recyclergridwithheader.adapter.viewholder.LoadingViewHolder;
import com.zires.recyclergridwithheader.recyclerView.OnLoadMoreListener;
import com.zires.recyclergridwithheader.recyclerView.RecyclerViewItemClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.zires.recyclergridwithheader.MainActivity.VIEW_TYPE_GRID;
import static com.zires.recyclergridwithheader.MainActivity.VIEW_TYPE_HEADER;
import static com.zires.recyclergridwithheader.MainActivity.VIEW_TYPE_LIST;
import static com.zires.recyclergridwithheader.MainActivity.VIEW_TYPE_LOADING;

/**
 * Created by ClassicZires on 8/8/2019.
 **/

public class CustomListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecyclerHeaderModel headerModel;
    private List<Object> items;
    private OnLoadMoreListener mOnLoadMoreListener;
    private RecyclerViewItemClickListener mClickListener;
    private int sortType = 0;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public CustomListAdapter(RecyclerView recyclerView, int sortType,
                             RecyclerViewItemClickListener clickListener) {
        this.mClickListener = clickListener;

        items = new ArrayList<>();
        this.sortType = sortType;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    totalItemCount = gridLayoutManager.getItemCount();
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                    if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (totalItemCount > visibleThreshold) {
                            if (mOnLoadMoreListener != null) {
                                mOnLoadMoreListener.onLoadMore();
                                isLoading = true;
                            }
                        }
                    }
                }
            }
        });
    }

    public void setItems(List<RecyclerItemModel> items, RecyclerHeaderModel headerModel) {
        if (headerModel != null) {
            this.items.add(headerModel);
            this.headerModel = headerModel;
        }

        this.items.addAll(items);

        notifyItemRangeInserted(this.items.size() - items.size(), items.size());
        notifyDataSetChanged();
    }

    public List<Object> getItems() {
        return items;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setLoading() {
        isLoading = true;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null)
            return VIEW_TYPE_LOADING;
        else if (items.get(position).getClass() == RecyclerHeaderModel.class)
            return VIEW_TYPE_HEADER;
        else
            return sortType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LIST) {
            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            return new ListViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_GRID) {
            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_grid, parent, false);
            return new GridViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading_layout, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, position);
            }
        });


        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            RecyclerHeaderModel headerModel = (RecyclerHeaderModel) items.get(position);

            viewHolder.title.setText(headerModel.getName());
            viewHolder.info.setText(Html.fromHtml(headerModel.getDescription()));

            viewHolder.img.setBackgroundColor(Color.parseColor(headerModel.getImageUrl()));

        } else if (holder instanceof ListViewHolder) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            RecyclerItemModel itemModel = (RecyclerItemModel) items.get(position);

            listViewHolder.newPrice.setText(nf.format(itemModel.getPrice()));
            listViewHolder.oldPrice.setText(nf.format(itemModel.getOldPrice()));
            listViewHolder.oldPrice.setPaintFlags(listViewHolder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            listViewHolder.img.setImageBitmap(null);
            listViewHolder.title.setText(itemModel.getName());


            listViewHolder.img.setBackgroundColor(Color.parseColor(itemModel.getImageUrl()));

        } else if (holder instanceof GridViewHolder) {
            GridViewHolder gridViewHolder = (GridViewHolder) holder;
            RecyclerItemModel itemModel = (RecyclerItemModel) items.get(position);

            gridViewHolder.newPrice.setText(nf.format(itemModel.getPrice()));
            gridViewHolder.oldPrice.setText(nf.format(itemModel.getOldPrice()));
            gridViewHolder.oldPrice.setPaintFlags(gridViewHolder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            gridViewHolder.img.setImageBitmap(null);

            gridViewHolder.title.setText(itemModel.getName());

            gridViewHolder.img.setBackgroundColor(Color.parseColor(itemModel.getImageUrl()));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(Object item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        items.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public void clearList() {
        items.clear();
        notifyDataSetChanged();
    }


    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

}
