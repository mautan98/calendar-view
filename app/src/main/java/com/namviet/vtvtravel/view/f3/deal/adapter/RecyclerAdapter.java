package com.namviet.vtvtravel.view.f3.deal.adapter;

import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealHomeChildFragment;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.SimpleDiffCallback;
import com.namviet.vtvtravel.view.f3.deal.view.listdeal.ListDealActivity;
import com.namviet.vtvtravel.view.f3.deal.view.listhotdeal.ListHotDealActivity;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import me.relex.circleindicator.CircleIndicator;

import static android.view.LayoutInflater.from;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder> implements StickyHeaderHandler {

    private final List<Item> data = new ArrayList<>();
    private static final int TYPE_HEADER_1 = 0;
    private static final int TYPE_CONTENT_1 = 1;
    private static final int TYPE_HEADER_2 = 2;
    private static final int TYPE_CONTENT_2 = 3;
    private Context context;
    private DealHomeChildFragment dealHomeChildFragment;

    public class TypeString {
        public static final String SLIDE = "SLIDE";
        public static final String DEAL_HOME = "DEAL_HOME";
        public static final String DEAL_PROCESSING = "DEAL_PROCESSING";
    }

    private ArrayList<Block> blocksMenuHeader1 = new ArrayList<>();
    private ArrayList<Block> blocksMenuHeader2 = new ArrayList<>();

    private ILoadDataDeal mILoadDataDeal;
    private IOnTabHotClick iOnTabHotClick;
    private int positionHeader1 = 0;
    private int positionHeader2 = 0;


    private DealResponse dealResponseForBlockContent2;

    public void setiOnTabHotClick(IOnTabHotClick iOnTabHotClick) {
        this.iOnTabHotClick = iOnTabHotClick;
    }

    public void setILoadDataDeal(ILoadDataDeal mILoadDataDeal) {
        this.mILoadDataDeal = mILoadDataDeal;
    }

    private int positionSelected1 = 0;
    private int positionSelected2 = 0;

    public void setPositionSelected1(int positionSelected1) {
        this.positionSelected1 = positionSelected1;
    }

    public void setPositionSelected2(int positionSelected2) {
        this.positionSelected2 = positionSelected2;
    }


    public void setData(List<Item> items, Context context, ArrayList<Block> blocksMenuHeader1, ArrayList<Block> blocksMenuHeader2, DealHomeChildFragment dealHomeChildFragment) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(data, items));
        data.clear();
        data.addAll(items);
        this.context = context;
        this.blocksMenuHeader1 = blocksMenuHeader1;
        this.blocksMenuHeader2 = blocksMenuHeader2;
        this.dealHomeChildFragment = dealHomeChildFragment;
        diffResult.dispatchUpdatesTo(this);
    }

    private ContentViewHolder2 contentViewHolder2;
    private HeaderViewHolder2 headerViewHolder2;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_HEADER_1) {
            view = from(parent.getContext()).inflate(R.layout.layout_header_1, parent, false);
            return new HeaderViewHolder1(view);
        } else if (viewType == TYPE_CONTENT_1) {
            view = from(parent.getContext()).inflate(R.layout.layout_content_1, parent, false);
            return new ContentViewHolder1(view);
        }
        if (viewType == TYPE_HEADER_2) {
            view = from(parent.getContext()).inflate(R.layout.layout_header_2, parent, false);
            headerViewHolder2 = new HeaderViewHolder2(view);
            return headerViewHolder2;
        } else {
            view = from(parent.getContext()).inflate(R.layout.layout_content_2, parent, false);
            contentViewHolder2 = new ContentViewHolder2(view);
            return contentViewHolder2;
        }

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_HEADER_1) {
                ((HeaderViewHolder1) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_CONTENT_1) {
                ((ContentViewHolder1) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_HEADER_2) {
                ((HeaderViewHolder2) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_CONTENT_2) {
                ((ContentViewHolder2) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER_1;
            case 1:
                return TYPE_CONTENT_1;
            case 2:
                return TYPE_HEADER_2;
            case 3:
                return TYPE_CONTENT_2;
        }
        return TYPE_HEADER_1;
    }

    @Override
    public List<?> getAdapterData() {
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
                    Log.e("xxx", "onClickTabHeader1: " + blocksMenuHeader1.get(position).getName());
                    //  EventBus.getDefault().post(new OnClickTabHeader1(position));
                    positionHeader1 = position;
                    iOnTabHotClick.onTab1Click(position);

                }
            }, true);
            rcvTabHeader1.setAdapter(mF3Header1Adapter);
        }
    }

    public class HeaderViewHolder2 extends BaseViewHolder implements Observer {
        private int position;
        private int positionTab;
        private RecyclerView rcvTabHeader1;
        private F3Header2Adapter mF3Header2Adapter;
        private DealViewModel dealViewModel;

        public HeaderViewHolder2(View itemView) {
            super(itemView);
            rcvTabHeader1 = itemView.findViewById(R.id.rcv_tab_header1);
            dealViewModel = new DealViewModel();
            dealViewModel.addObserver(this);
        }

        public void bindItem(int position) {
            this.position = position;
            mF3Header2Adapter = new F3Header2Adapter(positionSelected2, blocksMenuHeader2, itemView.getContext(), new F3Header2Adapter.ClickTab() {
                @Override
                public void onClickTab(int position) {
                    positionTab = position;
                    contentViewHolder2.showLoading();
                    getData(contentViewHolder2.checkBox.isChecked());
                    iOnTabHotClick.onTab2Click(position);

                }
            }, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            rcvTabHeader1.setLayoutManager(gridLayoutManager);
            rcvTabHeader1.setAdapter(mF3Header2Adapter);
            if(dealResponseForBlockContent2 == null){
                dealViewModel.getDeal(blocksMenuHeader2.get(0).getLink());
            }
        }


        public void getData(boolean isChecked){
            if(isChecked){
                dealViewModel.getDealWithReplaceParam(blocksMenuHeader2.get(positionTab).getLink(), "2");
            }else {
                dealViewModel.getDeal(blocksMenuHeader2.get(positionTab).getLink());
            }
        }

        @Override
        public void update(Observable observable, Object o) {
//            shimmer.setVisibility(View.GONE);
            if (observable instanceof DealViewModel) {
                if (o instanceof DealResponse) {
                    dealResponseForBlockContent2 = (DealResponse) o;
                    contentViewHolder2.bindItem(3);
                    contentViewHolder2.hideLoading();
                }
            }
        }
    }

    public class ContentViewHolder2 extends BaseViewHolder {
        private int position;
        private RecyclerView rclContent;
        private ShimmerFrameLayout shimmerFrameLayout;
        public CheckBox checkBox;
        private LinearLayout btnViewMore;

        public ContentViewHolder2(View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            rclContent = itemView.findViewById(R.id.rclContent);
            checkBox = itemView.findViewById(R.id.check);
            btnViewMore = itemView.findViewById(R.id.lnl_view_more);
        }

        public void bindItem(int position) {
            this.position = position;
            rclContent.setAdapter(new GridDealInDealHomeAdapter(dealResponseForBlockContent2));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 2);
            rclContent.setLayoutManager(gridLayoutManager);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    showLoading();
                    headerViewHolder2.getData(isChecked);
                }
            });

            btnViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListDealActivity.Companion.startScreen(context, blocksMenuHeader2);
                }
            });
        }

        public void showLoading(){
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            rclContent.setVisibility(View.INVISIBLE);
        }
        public void hideLoading(){
            shimmerFrameLayout.setVisibility(View.GONE);
            rclContent.setVisibility(View.VISIBLE);
        }
    }

    public class ContentViewHolder1 extends BaseViewHolder implements NewHomeFragment.IOnClickTabReloadData {
        private int position;
        private RecyclerView rclContent;
        private ShimmerFrameLayout mShimmerFrameLayout;
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
            mShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);


        }

        public void bindItem(int position) {
            this.position = position;
            rclContent.setAdapter(new F3SubDealAdapter(null, blocksMenuHeader1.get(0).getDealResponse(), null));
            if (!blocksMenuHeader1.get(0).isDataLoaded()) {
                rclContent.setVisibility(View.INVISIBLE);
                mShimmerFrameLayout.setVisibility(View.VISIBLE);
                mShimmerFrameLayout.startShimmer();
                dealHomeChildFragment.setmIOnClickTabReloadData(ContentViewHolder1.this);
                mILoadDataDeal.onLoadDataDeal(blocksMenuHeader1.get(positionHeader1).getListChildBlock().get(0).getLink());
                blocksMenuHeader1.get(0).setDataLoaded(true);
            }

            F3TabDealAdapter f3TabDealAdapter = new F3TabDealAdapter(0, blocksMenuHeader1, context, new F3TabDealAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {
                    mShimmerFrameLayout.setVisibility(View.VISIBLE);
                    rclContent.setVisibility(View.INVISIBLE);
                    mShimmerFrameLayout.startShimmer();
                    dealHomeChildFragment.setmIOnClickTabReloadData(ContentViewHolder1.this);
                    mILoadDataDeal.onLoadDataDeal(blocksMenuHeader1.get(positionHeader1).getListChildBlock().get(positionClick).getLink());
                }
            }, false);
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
                    Log.e("xxx", "onPageSelected: " + infiniteCycleViewPager.getRealItem());
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
                    ArrayList<Block> listBlockResult = new ArrayList<>();
                    ArrayList<Block> blocks = blocksMenuHeader1.get(positionHeader1).getListChildBlock();
                    for (int i = 0; i < blocks.size(); i++) {
                        if(blocks.get(i).getCode_type().equals("CTKM_RUNNING")){
                            listBlockResult.add(blocks.get(i));
                        }
                    }
                    for (int i = 0; i < blocks.size(); i++) {
                        if(blocks.get(i).getCode_type().equals("CTKM_UPCOMING")){
                            listBlockResult.add(blocks.get(i));
                        }
                    }
                    ListHotDealActivity.Companion.startScreen(context, listBlockResult);

                }
            });


        }

        @Override
        public void onTabClick(String code) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rclContent.setAdapter(new F3SubDealAdapter(null, blocksMenuHeader1.get(0).getDealResponse(), null));
                    mShimmerFrameLayout.stopShimmer();
                    mShimmerFrameLayout.setVisibility(View.GONE);
                    rclContent.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
    }

    public interface ILoadDataDeal {
        void onLoadDataDeal(String url);
    }

    public interface IOnTabHotClick {
        void onTab1Click(int position);

        void onTab2Click(int position);
    }
}
