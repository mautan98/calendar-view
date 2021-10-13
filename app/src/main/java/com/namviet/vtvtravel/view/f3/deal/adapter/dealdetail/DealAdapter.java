package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.CommentInDetailTravelNewsAdapter;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.view.f3.deal.model.Rank;
import com.namviet.vtvtravel.view.f3.deal.model.UserObj;
import com.ornach.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class DealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<?> mListData;
    private Context mContext;
    private int HEADER = 0;
    private int USER_OBJECT = 1;
    private int HOT_LINE = 2;
    private int RANKING = 3;
    private int CONTENT = 4;
    private int COMMENT = 5;
    private int SUGGEST = 6;
    private LoadData loadData;


    public DealAdapter(List<?> mListData, Context mContext, LoadData loadData) {
        this.mListData = mListData;
        this.mContext = mContext;
        this.loadData = loadData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_deal_detail_item, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == USER_OBJECT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_object_item, parent, false);
            return new UserObjectViewHolder(v);
        } else if (viewType == HOT_LINE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hotline, parent, false);
            return new HotLineViewHolder(v);
        } else if (viewType == RANKING) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ranking_item, parent, false);
            return new RankingViewHolder(v);
        } else if (viewType == CONTENT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_item, parent, false);
            return new ContentViewHolder(v);
        } else if (viewType == COMMENT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment_item, parent, false);
            return new CommentViewHolder(v);
        } else if (viewType == SUGGEST) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_suggest_item, parent, false);
            return new MoreViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEADER) {
            ((HeaderViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == USER_OBJECT) {
            ((UserObjectViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == HOT_LINE) {
            ((HotLineViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == RANKING) {
            ((RankingViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == CONTENT) {
            ((ContentViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == COMMENT) {
            ((CommentViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == SUGGEST) {
            ((MoreViewHolder) holder).bindItem(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return HEADER;
            case 1:
                return USER_OBJECT;

            case 2:
                return HOT_LINE;

            case 3:
                return RANKING;

            case 4:
                return CONTENT;

            case 5:
                return COMMENT;

            case 6:
                return SUGGEST;

        }
        return getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }




    public class MoreViewHolder extends RecyclerView.ViewHolder {
        private View mViewTop;
        private TextView mTvTitle;
        private TextView mTvDes;
        private RecyclerView mRcvSuggest;
        private List<String> data;
        private SubSuggestItemAdapter adapter;

        public MoreViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            mViewTop = (View) itemView.findViewById(R.id.view_top);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvDes = (TextView) itemView.findViewById(R.id.tv_des);
            mRcvSuggest = (RecyclerView) itemView.findViewById(R.id.rcv_suggest);
            data = new ArrayList<>();
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            data.add("");
            adapter = new SubSuggestItemAdapter(mContext, data);
            mRcvSuggest.setAdapter(adapter);
        }

        public void bindItem(int position) {

        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CommentInDetailTravelNewsAdapter commentInDetailTravelNewsAdapter;
        private View mViewTop;
        private RecyclerView mRclComment;
        private LinearLayout mLayoutViewAllComment;
        private LinearLayout mBtnViewMoreComment;
        private TextView mTvCommentLeft;
        private RelativeLayout mLayoutWriteComment;
        private TextView mTvWriteComment;
        public CommentViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }
        private void initView(View itemView) {
            mViewTop = (View) itemView.findViewById(R.id.view_top);
            mRclComment = (RecyclerView) itemView.findViewById(R.id.rclComment);
            mLayoutViewAllComment = (LinearLayout) itemView.findViewById(R.id.layoutViewAllComment);
            mBtnViewMoreComment = (LinearLayout) itemView.findViewById(R.id.btnViewMoreComment);
            mTvCommentLeft = (TextView) itemView.findViewById(R.id.tvCommentLeft);
            mLayoutWriteComment = (RelativeLayout) itemView.findViewById(R.id.layoutWriteComment);
            mTvWriteComment = (TextView) itemView.findViewById(R.id.tvWriteComment);
        }
        public void bindItem(int position) {
            if(mListData.get(position) instanceof  CommentResponse){
                CommentResponse response = (CommentResponse) mListData.get(position);
                    List<CommentResponse.Data.Comment> comments = response.getData().getContent();
                    if (comments == null) {
                        mLayoutViewAllComment.setVisibility(View.GONE);
                    } else if (comments.size() >= 3) {
                        mLayoutViewAllComment.setVisibility(View.VISIBLE);
                        mTvCommentLeft.setText("Xem tất cả " + comments.size() + " bình luận");
                    } else {
                        mLayoutViewAllComment.setVisibility(View.GONE);
                    }
                    commentInDetailTravelNewsAdapter = new CommentInDetailTravelNewsAdapter(mContext, response.getData().getContent(), new CommentInDetailTravelNewsAdapter.ClickItem() {
                        @Override
                        public void onClickItem(CommentResponse.Data.Comment comment) {

                        }

                        @Override
                        public void onClickReply(CommentResponse.Data.Comment comment) {
                            //   CommentActivity.startScreen(mContext, detailTravelNewsResponse, comment.getId());
                        }
                    });
                    mRclComment.setAdapter(commentInDetailTravelNewsAdapter);

            }
            else {
                loadData.onLoadDataComment("");
            }

        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private ViewPager mPagerContent;
        private Button mBtnCollapse;
        private InfomationDealAdapter adapter;
        private List<String> urls;
        private TabLayout mTabLayout;
        private TextView tvTab1, tvTab2, tvTab3;
        private String url = "<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> "+
                "    <figcaption style=\"text-align: center;padding:5px;\"><em>ảnh lấy trên mạng. </em></figcaption>\n" +
                "    </figure>";

        public ContentViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mPagerContent = (ViewPager) itemView.findViewById(R.id.pager_content);
            mBtnCollapse = (Button) itemView.findViewById(R.id.btn_collapse);
            mTabLayout = (TabLayout) itemView.findViewById(R.id.tab_content);
            tvTab1 = itemView.findViewById(R.id.tv_tab1);
            tvTab2 = itemView.findViewById(R.id.tv_tab2);
            tvTab3 = itemView.findViewById(R.id.tv_tab3);
            urls = new ArrayList<>();
            urls.add(url);
            urls.add(url);
            urls.add(url);
            // mTabLayout.setupWithViewPager(mPagerContent);

        }

        private void resetTabColor() {
            tvTab1.setTextColor(mContext.getResources().getColor(R.color.gray_99));
            tvTab2.setTextColor(mContext.getResources().getColor(R.color.gray_99));
            tvTab3.setTextColor(mContext.getResources().getColor(R.color.gray_99));
        }

        public void bindItem(int position) {
            adapter = new InfomationDealAdapter(mContext, urls);
            mPagerContent.setAdapter(adapter);
            mPagerContent.setOffscreenPageLimit(3);
            resetTabColor();
            tvTab1.setTextColor(mContext.getResources().getColor(R.color.black));
            mPagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    resetTabColor();
                    if (position == 0) {
                        tvTab1.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (position == 1) {
                        tvTab2.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else if (position == 2) {
                        tvTab3.setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            tvTab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPagerContent.setCurrentItem(0);
                }
            });
            tvTab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPagerContent.setCurrentItem(1);
                }
            });
            tvTab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPagerContent.setCurrentItem(2);
                }
            });
        }
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgStar;
        private ImageView mImgShowHide;
        private LinearLayout mLnlRankContent;
        private RecyclerView mRcvRankItem;
        private List<Rank> listData;
        private SubRankingItemAdapter adapter;

        public RankingViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mImgStar = (ImageView) itemView.findViewById(R.id.img_star);
            mImgShowHide = (ImageView) itemView.findViewById(R.id.img_show_hide);
            mLnlRankContent = (LinearLayout) itemView.findViewById(R.id.lnl_rank_content);
            mRcvRankItem = (RecyclerView) itemView.findViewById(R.id.rcv_rank_item);
            listData = new ArrayList<>();
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            listData.add(new Rank("09842324112", "77:22:00"));
            adapter = new SubRankingItemAdapter(mContext, listData);
            mRcvRankItem.setAdapter(adapter);
        }

        public void bindItem(int position) {
            mImgShowHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLnlRankContent.getVisibility() == View.GONE) {
                        mLnlRankContent.setVisibility(View.VISIBLE);
                        mImgShowHide.setImageResource(R.drawable.ic_hide);
                    } else {
                        mLnlRankContent.setVisibility(View.GONE);
                        mImgShowHide.setImageResource(R.drawable.ic_show);
                    }
                }
            });
        }
    }

    public class HotLineViewHolder extends RecyclerView.ViewHolder {

        public HotLineViewHolder(View itemView) {
            super(itemView);


        }

        public void bindItem(int position) {

        }
    }

    public class UserObjectViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvUserObj;
        private RecyclerView mRcvRanking;
        private List<UserObj> userObjs;
        private SubUserObjItemAdapter adapter;
        private GridLayoutManager manager;

        public UserObjectViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvUserObj = (TextView) itemView.findViewById(R.id.tv_user_obj);
            mRcvRanking = (RecyclerView) itemView.findViewById(R.id.rcv_ranking);
            userObjs = new ArrayList<>();
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_silver, "Friend", true));
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_bronze_a, "Bronze A", true));
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_bronze_b, "Bronze B", true));
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_silver, "Silver", true));
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_gold, "Gold", true));
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_diamond, "Diamond", true));
            userObjs.add(new UserObj(R.drawable.f2_ic_filter_rank_platinium, "PLatinium", true));
            adapter = new SubUserObjItemAdapter(mContext, userObjs);
            manager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
            manager.setSpanSizeLookup(
                    new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            // 3 column size for first row
                            return (position == 0 ? 3 : 1);
                        }
                    });
            mRcvRanking.setLayoutManager(manager);

        }

        public void bindItem(int position) {
            mRcvRanking.setAdapter(adapter);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerBanner;
        private CircleIndicator2 mIndicator;
        private TextView mTvTilte;
        private TextView mTvShowOnMap;
        private TextView mCodeDeal;
        private TextView mTvNewPrice;
        private RichText mTvDisCount;
        private TextView mTvUserTotal;
        private ProgressBar mProgressCountDown;
        private TextView mTvTimeCountDown;
        private List<Integer> data;
        private SubDealHeaderItemAdapter adapter;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            data = new ArrayList<>();
            data.add(R.drawable.bg_register_service_success);
            data.add(R.drawable.f2_banner_intro_offline);
            data.add(R.drawable.bg_register_service_success);
            data.add(R.drawable.f2_banner_intro_offline);
            mRecyclerBanner = (RecyclerView) itemView.findViewById(R.id.recycler_banner);
            mIndicator = (CircleIndicator2) itemView.findViewById(R.id.indicator);
            mTvTilte = (TextView) itemView.findViewById(R.id.tv_tilte);
            mTvShowOnMap = (TextView) itemView.findViewById(R.id.tv_show_on_map);
            mCodeDeal = (TextView) itemView.findViewById(R.id.code_deal);
            mTvNewPrice = (TextView) itemView.findViewById(R.id.tv_new_price);
            mTvDisCount = (RichText) itemView.findViewById(R.id.tv_dis_count);
            mTvUserTotal = (TextView) itemView.findViewById(R.id.tvUserTotal);
            mProgressCountDown = (ProgressBar) itemView.findViewById(R.id.progress_count_down);
            mTvTimeCountDown = (TextView) itemView.findViewById(R.id.tv_time_count_down);
            adapter = new SubDealHeaderItemAdapter(mContext, data);
            mRecyclerBanner.setAdapter(adapter);
            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(mRecyclerBanner);
            mIndicator.attachToRecyclerView(mRecyclerBanner, pagerSnapHelper);
        }


        public void bindItem(int position) {

        }
    }
    public interface LoadData {
        void onLoadDataComment(String id);
    }

}
