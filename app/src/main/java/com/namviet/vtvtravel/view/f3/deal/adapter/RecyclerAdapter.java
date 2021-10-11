package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.ItemGenerator;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.SimpleDiffCallback;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder> implements StickyHeaderHandler {

    private final List<Item> data = new ArrayList<>();
    private static final int TYPE_HEADER_1 = 0;
    private static final int TYPE_CONTENT_1 = 1;
    private static final int TYPE_HEADER_2 = 2;
    private static final int TYPE_CONTENT_2 = 3;
    private Context context;


    public void setData(List<Item> items, Context context) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(data, items));
        data.clear();
        data.addAll(items);
        this.context = context;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_HEADER_1) {
            view = from(parent.getContext()).inflate(R.layout.layout_header_1, parent, false);
            return new HeaderViewHolder1(view);
        } else if (viewType == TYPE_CONTENT_1) {
            view = from(parent.getContext()).inflate(R.layout.layout_content_1, parent, false);
            return new ContentViewHolder1(view);
        }if (viewType == TYPE_HEADER_2) {
            view = from(parent.getContext()).inflate(R.layout.layout_header_2, parent, false);
            return new HeaderViewHolder2(view);
        }else  {
            view = from(parent.getContext()).inflate(R.layout.layout_content_2, parent, false);
            return new ContentViewHolder2(view);
        }

    }

    @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_HEADER_1) {
                ((HeaderViewHolder1) holder).bindItem(position);
            }else if (getItemViewType(position) == TYPE_CONTENT_1) {
                ((ContentViewHolder1) holder).bindItem(position);
            }else if (getItemViewType(position) == TYPE_HEADER_2) {
                ((HeaderViewHolder2) holder).bindItem(position);
            }else if (getItemViewType(position) == TYPE_CONTENT_2)  {
                ((ContentViewHolder2) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public int getItemCount() {
        return data.size();
    }

    @Override public int getItemViewType(int position) {
        switch (position){
            case 0: return TYPE_HEADER_1;
            case 1: return TYPE_CONTENT_1;
            case 2: return TYPE_HEADER_2;
            case 3: return TYPE_CONTENT_2;
        }
        return TYPE_HEADER_1;
    }

    @Override public List<?> getAdapterData() {
        return data;
    }


    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);

        }
    }

    public class HeaderViewHolder1 extends BaseViewHolder {
        private int position;

        public HeaderViewHolder1(View itemView) {
            super(itemView);


        }

        public void bindItem(int position) {
            this.position = position;

        }
    }

    public class HeaderViewHolder2 extends BaseViewHolder {
        private int position;

        public HeaderViewHolder2(View itemView) {
            super(itemView);


        }

        public void bindItem(int position) {
            this.position = position;

        }
    }

    public class ContentViewHolder2 extends BaseViewHolder {
        private int position;
        private RecyclerView rclContent;

        public ContentViewHolder2(View itemView) {
            super(itemView);


        }

        public void bindItem(int position) {
            this.position = position;
            rclContent = itemView.findViewById(R.id.rclContent);
            rclContent.setAdapter(new GridDealAdapter());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 2);
            rclContent.setLayoutManager(gridLayoutManager);
        }
    }

    public class ContentViewHolder1 extends BaseViewHolder {
        private int position;
        private RecyclerView rclContent;
        private RecyclerView rclTab;

        public ContentViewHolder1(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            rclTab = itemView.findViewById(R.id.rclTab);


        }

        public void bindItem(int position) {
            this.position = position;
            rclContent.setAdapter(new F3SubDealAdapter(null, null, null));

            F3TabDealAdapter f3TabDealAdapter = new F3TabDealAdapter(0, ItemGenerator.demoTabDealList(), context, new F3TabDealAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {


                }
            });
            rclTab.setAdapter(f3TabDealAdapter);

        }
    }
}
