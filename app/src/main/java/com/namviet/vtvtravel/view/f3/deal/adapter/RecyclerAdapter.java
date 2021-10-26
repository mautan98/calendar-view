package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader1;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader2;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.ItemGenerator;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.SimpleDiffCallback;
import com.namviet.vtvtravel.view.f3.deal.view.listdeal.ListDealActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static android.view.LayoutInflater.from;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder> implements StickyHeaderHandler {

    private final List<Item> data = new ArrayList<>();
    private static final int TYPE_HEADER_1 = 0;
    private static final int TYPE_CONTENT_1 = 1;
    private static final int TYPE_HEADER_2 = 2;
    private static final int TYPE_CONTENT_2 = 3;
    private Context context;


    private ArrayList<Block> blocksMenuHeader1 = new ArrayList<>();
    private ArrayList<Block> blocksMenuHeader2 = new ArrayList<>();



    private int positionSelected1 = 0;
    private int positionSelected2 = 0;

    public void setPositionSelected1(int positionSelected1) {
        this.positionSelected1 = positionSelected1;
    }

    public void setPositionSelected2(int positionSelected2) {
        this.positionSelected2 = positionSelected2;
    }


    public void setData(List<Item> items, Context context, ArrayList<Block> blocksMenuHeader1, ArrayList<Block> blocksMenuHeader2) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(data, items));
        data.clear();
        data.addAll(items);
        this.context = context;
        this.blocksMenuHeader1 = blocksMenuHeader1;
        this.blocksMenuHeader2 = blocksMenuHeader2;
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
        private RecyclerView rcvTabHeader1;
        private F3Header1Adapter mF3Header1Adapter;
        public HeaderViewHolder1(View itemView) {
            super(itemView);
            rcvTabHeader1 = itemView.findViewById(R.id.rcv_tab_header1);


        }

        public void bindItem(int position) {
            this.position = position;
            mF3Header1Adapter = new F3Header1Adapter(positionSelected1, blocksMenuHeader1, itemView.getContext(), new F3Header1Adapter.ClickTab() {
                @Override
                public void onClickTab(int position) {
                    Toast.makeText(itemView.getContext(), "Tab click: "+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new OnClickTabHeader1(position));
                }
            },true);
            rcvTabHeader1.setAdapter(mF3Header1Adapter);
        }
    }

    public class HeaderViewHolder2 extends BaseViewHolder {
        private int position;
        private RecyclerView rcvTabHeader1;
        private F3Header2Adapter mF3Header2Adapter;

        public HeaderViewHolder2(View itemView) {
            super(itemView);
            rcvTabHeader1 = itemView.findViewById(R.id.rcv_tab_header1);

        }

        public void bindItem(int position) {
            this.position = position;
            mF3Header2Adapter = new F3Header2Adapter(positionSelected2, blocksMenuHeader2, itemView.getContext(), new F3Header2Adapter.ClickTab() {
                @Override
                public void onClickTab(int position) {
                    Toast.makeText(itemView.getContext(), "Tab click: "+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new OnClickTabHeader2(position));
                }
            },false);
            rcvTabHeader1.setAdapter(mF3Header2Adapter);
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
        private HorizontalInfiniteCycleViewPager infiniteCycleViewPager;
        private CircleIndicator indicator;
        private ViewPager mPagerSlide;
        private View btnSeeMore;


        public ContentViewHolder1(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            rclTab = itemView.findViewById(R.id.rclTab);

            infiniteCycleViewPager = itemView.findViewById(R.id.hicvp);
            indicator = (CircleIndicator) itemView.findViewById(R.id.indicator);
            mPagerSlide = itemView.findViewById(R.id.vp_cache);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);


        }

        public void bindItem(int position) {
            this.position = position;
            rclContent.setAdapter(new F3SubDealAdapter(null, null, null));

            F3TabDealAdapter f3TabDealAdapter = new F3TabDealAdapter(0, ItemGenerator.demoTabDealList2(), context, new F3TabDealAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {

                }
            });
            rclTab.setAdapter(f3TabDealAdapter);
            mPagerSlide.setAdapter(new BannerCacheDealAdapter(itemView.getContext(), false));
            infiniteCycleViewPager.setAdapter(new BannerDealAdapter(itemView.getContext(), false));
            infiniteCycleViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mPagerSlide.setCurrentItem(infiniteCycleViewPager.getRealItem());
                    Log.e("xxx", "onPageSelected: "+infiniteCycleViewPager.getRealItem());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            try {
                indicator.setViewPager(mPagerSlide);

            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListDealActivity.Companion.startScreen(context);
                }
            });


        }
    }
}
