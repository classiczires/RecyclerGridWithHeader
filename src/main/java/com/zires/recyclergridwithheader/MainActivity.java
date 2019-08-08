package com.zires.recyclergridwithheader;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;

import com.zires.recyclergridwithheader.adapter.CustomListAdapter;
import com.zires.recyclergridwithheader.recyclerView.OnLoadMoreListener;
import com.zires.recyclergridwithheader.recyclerView.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int VIEW_TYPE_LIST = 0;
    public static final int VIEW_TYPE_GRID = 1;
    public static final int VIEW_TYPE_LOADING = 2;
    public static final int VIEW_TYPE_HEADER = 4;

    private RecyclerView recyclerView;
    private CustomListAdapter adapter;
    private int selectedSortType;
    private boolean hasHeader;
    private ImageButton gridListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomListAdapter(recyclerView, VIEW_TYPE_LIST, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        RecyclerHeaderModel headerModel = new RecyclerHeaderModel("Google", getString(R.string.description), randomColorString());
        hasHeader = true;
        adapter.setItems(getItems(), headerModel);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (adapter.getItems().get(adapter.getItemCount() - 1) != null) {
                    adapter.add(null);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.getItems().get(adapter.getItemCount() - 1) == null) {
                            adapter.remove(adapter.getItemCount() - 1);
                        }
                        adapter.setItems(getItems(), null);
                        adapter.setLoaded();
                    }
                }, 2000);
            }
        });

        recyclerView.setAdapter(adapter);

        gridListButton = findViewById(R.id.list_view_type);
        gridListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    if (adapter.getSortType() == VIEW_TYPE_GRID) {
                        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(1);
                        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                return 1;
                            }
                        });
                        adapter.notifyItemRangeChanged(0, adapter.getItemCount());

                        adapter.setSortType(VIEW_TYPE_LIST);
                        gridListButton.setImageResource(R.drawable.ic_grid_view);
                    } else {
                        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(2);
                        if (hasHeader) {
                            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    return position == 0 ? 2 : 1;
                                }
                            });
                        }
                        adapter.notifyItemRangeChanged(0, adapter.getItemCount());

                        adapter.setSortType(VIEW_TYPE_GRID);
                        gridListButton.setImageResource(R.drawable.ic_list_view);
                    }
                    runLayoutAnimation(recyclerView);
                }
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);

        recyclerView.setLayoutAnimation(controller);
        if (recyclerView.getAdapter() != null)
            recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private List<RecyclerItemModel> getItems() {
        List<RecyclerItemModel> itemModels = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            itemModels.add(new RecyclerItemModel("Android is a mobile operating system developed by Google", 34000, 26000, randomColorString()));
        }
        return itemModels;
    }


    private String randomColorString() {
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);
        return String.format("#%06x", nextInt);
    }
}
