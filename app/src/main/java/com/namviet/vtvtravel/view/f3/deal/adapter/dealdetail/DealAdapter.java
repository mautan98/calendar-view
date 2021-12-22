package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.CommentInDetailTravelNewsAdapter;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f3.deal.Utils;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3SubDealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealInDealHomeAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealFilterAdapter;
import com.namviet.vtvtravel.view.f3.deal.constant.DiscountDisplayType;
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType;
import com.namviet.vtvtravel.view.f3.deal.model.deal.Content;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.Data;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.RewardStatus;
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DealItemDetailFragment;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.ornach.richtext.RichText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DealCampaignDetail dealCampaignDetail;
    private Context mContext;
    private int HEADER = 0;
    private int USER_OBJECT = 1;
    private int HOT_LINE = 2;
    private int RANKING = 3;
    private int CONTENT = 4;
    private int COMMENT = 5;
    private int SUGGEST = 6;
    private LoadData loadData;
    private DealItemDetailFragment dealItemDetailFragment;
    private ILoadDataDeal mILoadDataDeal;
    private DealResponse dealResponse;

    public void setILoadDataDeal(ILoadDataDeal mILoadDataDeal) {
        this.mILoadDataDeal = mILoadDataDeal;
    }

    public DealAdapter(DealCampaignDetail data, Context mContext, LoadData loadData, DealItemDetailFragment dealItemDetailFragment) {
        this.dealCampaignDetail = data;
        this.mContext = mContext;
        this.loadData = loadData;
        this.dealItemDetailFragment = dealItemDetailFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_deal_detail_item, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == USER_OBJECT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deal_information_2, parent, false);
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deal_chilld_list_in_deal_parent, parent, false);
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
                return SUGGEST;


        }
        return getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dealCampaignDetail != null ? 6 : 0;
    }


    public class MoreViewHolder extends RecyclerView.ViewHolder implements NewHomeFragment.IOnClickTabReloadData, Observer {
        private RecyclerView rclFilterDeal;
        private RecyclerView rclContent;
        private View layoutNoData;
        private View viewWhite;

        private RecyclerView rcvTabHeader1;
        private F3HeaderAdapter mF3HeaderAdapter;
        private ArrayList<String> tabs = new ArrayList<>();
        private ShimmerFrameLayout mShimmerFrameLayout;
        private LinearLayout lnlParent;
        private Button btnHunt;
        private String status = IsProcessingType.DANG_DIEN_RA_TYPE;
        private String filter = "";

        private ArrayList<RewardStatus> listFilter = new ArrayList<>();

        private DealViewModel dealViewModel;
        private TextView tvDay, tvHour, tvMinutes, tvSecond, tvTotalHoldCount;
        private DealFilterAdapter dealFilterAdapter;


        public MoreViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            tabs.add("Đang diễn ra");
            tabs.add("Sắp diễn ra");
            tabs.add("Đã kết thúc");

            initDangDienRa();


            dealViewModel = new DealViewModel();
            dealViewModel.addObserver(this);
        }

        private void initDangDienRa(){
            listFilter.clear();
            listFilter.add(new RewardStatus("", "Tất cả", true));
            listFilter.add(new RewardStatus("2", "Đang săn", false));
            listFilter.add(new RewardStatus("4", "Săn thành công", false));
            listFilter.add(new RewardStatus("5", "Săn không thành công", false));
            filter = "";
        }

        private void initSapDienRa(){
            listFilter.clear();
            listFilter.add(new RewardStatus("", "Tất cả", true));
            filter = "";
        }

        private void initDaKetThuc(){
            listFilter.clear();
            listFilter.add(new RewardStatus("", "Tất cả", true));
            listFilter.add(new RewardStatus("4", "Săn thành công", false));
            listFilter.add(new RewardStatus("5", "Săn không thành công", false));
            filter = "";
        }

        private void initView(View itemView) {
            rclFilterDeal = itemView.findViewById(R.id.rclFilterDeal);
            rclContent = itemView.findViewById(R.id.rclContent);
            mShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            rcvTabHeader1 = itemView.findViewById(R.id.rcv_tab_header1);
            lnlParent = itemView.findViewById(R.id.lnl_parent);
            btnHunt = itemView.findViewById(R.id.btn_hunt);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvHour = itemView.findViewById(R.id.tv_hour);
            tvMinutes = itemView.findViewById(R.id.tv_minutes);
            tvSecond = itemView.findViewById(R.id.tv_second);
            tvTotalHoldCount = itemView.findViewById(R.id.tv_total_hold_count);
            layoutNoData = itemView.findViewById(R.id.layoutNoData);
            viewWhite = itemView.findViewById(R.id.viewWhite);

        }

        public void bindItem(int position) {

            Data data = dealCampaignDetail.getData();
            try {
                long distance = data.getTotalHoldTime() / 1000;
                String days = (int) (distance / 86400) + " ngày ";
                String hours = String.valueOf((int) ((distance % 86400) / 3600));
                String minutes = String.valueOf((int) ((distance % 3600) / 60));
                String seconds = String.valueOf((int) ((distance % 3600) % 60));
                if (days.length() == 1) {
                    days = "0" + days;
                }

                if (hours.length() == 1) {
                    hours = "0" + hours;
                }

                if (minutes.length() == 1) {
                    minutes = "0" + minutes;
                }

                if (seconds.length() == 1) {
                    seconds = "0" + seconds;
                }
                tvDay.setText(days);
                tvHour.setText(hours);
                tvMinutes.setText(minutes);
                tvSecond.setText(seconds);
                tvTotalHoldCount.setText(String.format("Danh sách tích lũy (%d)", dealCampaignDetail.getData().getTotalDeal()));
            } catch (Exception e) {
                e.printStackTrace();
                tvDay.setText("0 ngày");
                tvHour.setText("00");
                tvMinutes.setText("00");
                tvSecond.setText("00");
                tvTotalHoldCount.setText(String.format("Danh sách tích lũy (%d)", 0));
            }

            try {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                rclContent.setLayoutManager(layoutManager);
                if (dealCampaignDetail.getData().isCampaign()) {
                    btnHunt.setVisibility(View.GONE);
                    lnlParent.setVisibility(View.VISIBLE);
//                    if (!dealCampaignDetail.isDataLoaded()) {
//                        rclContent.setVisibility(View.INVISIBLE);
//                        mShimmerFrameLayout.setVisibility(View.VISIBLE);
//                        mShimmerFrameLayout.startShimmer();
//                        dealItemDetailFragment.setIOnClickTabReloadData(MoreViewHolder.this);
//                        mILoadDataDeal.onLoadDataDeal(IsProcessingType.DANG_DIEN_RA_TYPE, filter);
//                        dealCampaignDetail.setDataLoaded(true);
//                    }
//                    rclContent.setAdapter(new GridDealInDealHomeAdapter(dealCampaignDetail.getDealByCampaign()));

                    if (dealResponse == null) {
                        rclContent.setVisibility(View.INVISIBLE);
                        mShimmerFrameLayout.setVisibility(View.VISIBLE);
                        mShimmerFrameLayout.startShimmer();
                        viewWhite.setVisibility(View.VISIBLE);
                        dealViewModel.getDealByCampaign(status, String.valueOf(dealCampaignDetail.getData().getId()), filter);
                    }

                    mF3HeaderAdapter = new F3HeaderAdapter(0, tabs, itemView.getContext(), new F3HeaderAdapter.ClickTab() {

                        @Override
                        public void onClickTab(int position) {
                            mShimmerFrameLayout.setVisibility(View.VISIBLE);
                            rclContent.setVisibility(View.INVISIBLE);
                            mShimmerFrameLayout.startShimmer();
                            viewWhite.setVisibility(View.VISIBLE);
//                            dealItemDetailFragment.setIOnClickTabReloadData(MoreViewHolder.this);

                            if (position == 0) {
                                status = IsProcessingType.DANG_DIEN_RA_TYPE;
                                initDangDienRa();
                            } else if (position == 1) {
                                status = IsProcessingType.SAP_DIEN_RA_TYPE;
                                initSapDienRa();
                            } else if (position == 2) {
                                status = IsProcessingType.KET_THUC_TYPE;
                                initDaKetThuc();
                            }
                            dealFilterAdapter.notifyDataSetChanged();
//                            mILoadDataDeal.onLoadDataDeal(status, filter);

                            dealViewModel.getDealByCampaign(status, String.valueOf(dealCampaignDetail.getData().getId()), filter);
                        }
                    }, false);
                    rcvTabHeader1.setAdapter(mF3HeaderAdapter);


                    dealFilterAdapter = new DealFilterAdapter(mContext, listFilter, new DealFilterAdapter.ClickItem() {
                        @Override
                        public void onClickItem(int position) {
                            filter = listFilter.get(position).getId();

                            mShimmerFrameLayout.setVisibility(View.VISIBLE);
                            rclContent.setVisibility(View.INVISIBLE);
                            mShimmerFrameLayout.startShimmer();
                            viewWhite.setVisibility(View.VISIBLE);
                            dealViewModel.getDealByCampaign(status, String.valueOf(dealCampaignDetail.getData().getId()), filter);
                        }
                    });
                    rclFilterDeal.setAdapter(dealFilterAdapter);


//                    ; = DealFilterAdapter(mActivity, listFilter) { position ->
//                            listDeal.clear()
//                        dealSubscribeParentAdapter?.notifyDataSetChanged()
//                        filter = listFilter[position].id
//                        dealViewModel?.getDealFollow(
//                                "https://core-testing.vtvtravel.vn/api/v1/deals/campaigns/follows",
//                                filter
//                        )
//                    }
                } else {
                    btnHunt.setVisibility(View.VISIBLE);
                    lnlParent.setVisibility(View.GONE);
                    String status = data.getIsProcessing();
                    if (status.equals(IsProcessingType.KET_THUC_TYPE)) {
                        if (data.getRanking() == 1) {
                            btnHunt.setText("Đồng ý tích luỹ");
                            btnHunt.setVisibility(View.VISIBLE);
                        } else {
                            btnHunt.setVisibility(View.GONE);
                        }
//                        btnHunt.setText("Đã hết hạn");
//                        btnHunt.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_hunt_disable));
                    } else if (status.equals(IsProcessingType.DANG_DIEN_RA_TYPE)) {
                        btnHunt.setText("Săn ngay");
                        btnHunt.setBackground(mContext.getResources().getDrawable(R.drawable.f3_btn_agree));
                    } else if (status.equals(IsProcessingType.SAP_DIEN_RA_TYPE)) {
                        btnHunt.setText(Utils.CalendarUtils.getDayStart(dealCampaignDetail.getData().getEndAt()));
                        btnHunt.setBackground(mContext.getResources().getDrawable(R.drawable.f3_btn_agree));
                    }

                    btnHunt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (status.equals(IsProcessingType.DANG_DIEN_RA_TYPE)) {
                                F2Util.startSendMessIntent(mContext, "1039", "D " + dealCampaignDetail.getData().getCode());
                            }else if(status.equals(IsProcessingType.KET_THUC_TYPE)){
                                if (data.getRanking() == 1) {
                                    F2Util.startSendMessIntent(mContext, "1039", "YD " + dealCampaignDetail.getData().getCode());
                                }
                            }
                        }
                    });


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Observable observable, Object o) {
            viewWhite.setVisibility(View.GONE);
            mShimmerFrameLayout.setVisibility(View.GONE);
            layoutNoData.setVisibility(View.VISIBLE);
            if (observable instanceof DealViewModel) {
                if (o instanceof DealResponse) {
                    dealResponse = (DealResponse) o;
                    rclContent.setAdapter(new GridDealInDealHomeAdapter((ArrayList<Content>) dealResponse.getData().getContent(), true));
                    rclContent.setVisibility(View.VISIBLE);

                    try {
                        if (dealResponse.getData().getContent().size() > 0) {
                            layoutNoData.setVisibility(View.GONE);
                            rclContent.setVisibility(View.VISIBLE);
                        } else {
                            layoutNoData.setVisibility(View.VISIBLE);
                            rclContent.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        layoutNoData.setVisibility(View.VISIBLE);
                        rclContent.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onTabClick(String code) {
            viewWhite.setVisibility(View.GONE);
            mShimmerFrameLayout.setVisibility(View.GONE);
            layoutNoData.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rclContent.setAdapter(new GridDealInDealHomeAdapter((ArrayList<Content>) dealCampaignDetail.getDealByCampaign().getData().getContent(), true));

                    try {
                        if (dealCampaignDetail.getDealByCampaign().getData().getContent().size() > 0) {
                            layoutNoData.setVisibility(View.GONE);
                            rclContent.setVisibility(View.VISIBLE);
                        } else {
                            layoutNoData.setVisibility(View.VISIBLE);
                            rclContent.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        layoutNoData.setVisibility(View.VISIBLE);
                        rclContent.setVisibility(View.GONE);
                    }
                }
            }, 300);
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
//            if(mListData.get(position) instanceof  CommentResponse){
//                CommentResponse response = (CommentResponse) mListData.get(position);
//                    List<CommentResponse.Data.Comment> comments = response.getData().getContent();
//                    if (comments == null) {
//                        mLayoutViewAllComment.setVisibility(View.GONE);
//                    } else if (comments.size() >= 3) {
//                        mLayoutViewAllComment.setVisibility(View.VISIBLE);
//                        mTvCommentLeft.setText("Xem tất cả " + comments.size() + " bình luận");
//                    } else {
//                        mLayoutViewAllComment.setVisibility(View.GONE);
//                    }
//                    commentInDetailTravelNewsAdapter = new CommentInDetailTravelNewsAdapter(mContext, response.getData().getContent(), new CommentInDetailTravelNewsAdapter.ClickItem() {
//                        @Override
//                        public void onClickItem(CommentResponse.Data.Comment comment) {
//
//                        }
//
//                        @Override
//                        public void onClickReply(CommentResponse.Data.Comment comment) {
//                            //   CommentActivity.startScreen(mContext, detailTravelNewsResponse, comment.getId());
//                        }
//                    });
//                    mRclComment.setAdapter(commentInDetailTravelNewsAdapter);
//
//            }
//            else {
//                loadData.onLoadDataComment("");
//            }

        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private ViewPager mPagerContent;
        boolean isCollapse;
        private WebView webView;
        private Button mBtnCollapse;
        private InfomationDealAdapter adapter;
        private List<String> urls;
        private TabLayout mTabLayout;
        private TextView tvTab1, tvTab2, tvTab3;


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
            webView = itemView.findViewById(R.id.web_view);
            urls = new ArrayList<>();
            // mTabLayout.setupWithViewPager(mPagerContent);

        }

        private void resetTabColor() {
            tvTab1.setTextColor(mContext.getResources().getColor(R.color.gray_99));
            tvTab2.setTextColor(mContext.getResources().getColor(R.color.gray_99));
            tvTab3.setTextColor(mContext.getResources().getColor(R.color.gray_99));
        }

        public void bindItem(int position) {
            resetTabColor();
            tvTab1.setTextColor(mContext.getResources().getColor(R.color.black));
            webView.loadDataWithBaseURL("", dealCampaignDetail.getData().getDescription(), "text/html", "UTF-8", null);
            tvTab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabSelected(0);
                    webView.loadDataWithBaseURL("", dealCampaignDetail.getData().getDescription(), "text/html", "UTF-8", null);
                }
            });
            tvTab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabSelected(1);
                    webView.loadDataWithBaseURL("", dealCampaignDetail.getData().getRule(), "text/html", "UTF-8", null);
                }
            });
            tvTab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabSelected(2);
                    webView.loadDataWithBaseURL("", dealCampaignDetail.getData().getGuide(), "text/html", "UTF-8", null);
                }
            });
            mBtnCollapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup.LayoutParams params = webView.getLayoutParams();
                    if (!isCollapse) {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        webView.setLayoutParams(params);
                        mBtnCollapse.setText("Ẩn bớt");
                        isCollapse = true;
                    } else {
                        params.height = com.brucetoo.videoplayer.utils.Utils.dp2px(mContext, 120);
                        webView.setLayoutParams(params);
                        mBtnCollapse.setText("Xem thêm");
                        isCollapse = false;
                    }
                }
            });
        }

        private void onTabSelected(int position) {
            resetTabColor();
            if (position == 0) {
                tvTab1.setTextColor(mContext.getResources().getColor(R.color.black));
            } else if (position == 1) {
                tvTab2.setTextColor(mContext.getResources().getColor(R.color.black));
            } else if (position == 2) {
                tvTab3.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgStar;
        private ImageView mImgShowHide;
        private LinearLayout mLnlRankContent;
        private RecyclerView mRcvRankItem;
        private SubRankingItemAdapter adapter;
        private TextView tvEmptyRank;

        public RankingViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mImgStar = (ImageView) itemView.findViewById(R.id.img_star);
            mImgShowHide = (ImageView) itemView.findViewById(R.id.img_show_hide);
            mLnlRankContent = (LinearLayout) itemView.findViewById(R.id.lnl_rank_content);
            mRcvRankItem = (RecyclerView) itemView.findViewById(R.id.rcv_rank_item);
            adapter = new SubRankingItemAdapter(mContext, dealCampaignDetail);
            mRcvRankItem.setAdapter(adapter);
            tvEmptyRank = itemView.findViewById(R.id.tv_empty_rank);
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
            try {
                if (dealCampaignDetail.getData().getDealCampaignScores().size() > 0) {
                    tvEmptyRank.setVisibility(View.GONE);
                } else tvEmptyRank.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                tvEmptyRank.setVisibility(View.VISIBLE);
            }
        }
    }

    public class HotLineViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCode;
        private View btnCopyDealCode;
        private ImageView imgShare;
        private RelativeLayout rllHunt;

        public HotLineViewHolder(View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_code);
            btnCopyDealCode = itemView.findViewById(R.id.btnCopyDealCode);
            imgShare = itemView.findViewById(R.id.img_share);
            rllHunt = itemView.findViewById(R.id.rll_hunt);
        }

        public void bindItem(int position) {
            if (dealCampaignDetail.getData().isCampaign()) {
                rllHunt.setVisibility(View.GONE);
            }else {
                if(dealCampaignDetail.getData().getIsProcessing().equals(IsProcessingType.SAP_DIEN_RA_TYPE)){
                    rllHunt.setVisibility(View.GONE);
                }else {
                    rllHunt.setVisibility(View.VISIBLE);
                    if(dealCampaignDetail.getData().getIsProcessing().equals(IsProcessingType.DANG_DIEN_RA_TYPE)){
                        btnCopyDealCode.setVisibility(View.VISIBLE);
                    }else {
                        btnCopyDealCode.setVisibility(View.GONE);
                    }
                }
            }
            tvCode.setText("D " + dealCampaignDetail.getData().getCode());
            btnCopyDealCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copy", tvCode.getText().toString().trim());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(mContext, "Đã coppy vào Clipboard", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneNumber = String.format("tel: 1039");
                    // Create the intent.
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    // Set the data for the intent as the phone number.
                    callIntent.setData(Uri.parse(phoneNumber));
                    // If package resolves to an app, send intent.
                    if (callIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(callIntent);
                    } else {
                        Log.e("xxx", "Can't resolve app for ACTION_DIAL Intent.");
                    }
                }
            });
        }
    }

    public class UserObjectViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvCode;
        private TextView mHuntingCount;
        private TextView mTvTimeKeepDeal;
        private TextView mTvRank;
        private ImageView imgNo1;

        public UserObjectViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvCode = (TextView) itemView.findViewById(R.id.tv_code);
            mHuntingCount = (TextView) itemView.findViewById(R.id.hunting_count);
            mTvTimeKeepDeal = (TextView) itemView.findViewById(R.id.tv_time_keep_deal);
            mTvRank = (TextView) itemView.findViewById(R.id.tv_rank);
            imgNo1 = itemView.findViewById(R.id.img_no1);

        }


        public void bindItem(int position) {
            try {
                try {
                    if(dealCampaignDetail.getData().getIsProcessing().equals(IsProcessingType.SAP_DIEN_RA_TYPE)){
                        mTvCode.setText("Chưa bắt đầu");
                    }else {
                        mTvCode.setText(dealCampaignDetail.getData().getCode());
                    }
                } catch (Exception e) {
                    mTvCode.setText("Chưa bắt đầu");
                    e.printStackTrace();
                }

                mHuntingCount.setText(F3SubDealAdapter.getHuntingUserCount(dealCampaignDetail.getData().getUserHuntingCount()));
                String status = dealCampaignDetail.getData().getIsProcessing();
                if (dealCampaignDetail.getData().getRanking() == 0) {
                    mTvRank.setText("Bạn chưa tích lũy");
                } else if (dealCampaignDetail.getData().getRanking() == 1) {
                    mTvRank.setVisibility(View.GONE);
                    imgNo1.setVisibility(View.VISIBLE);
                } else {
                    mTvRank.setText(String.valueOf(dealCampaignDetail.getData().getRanking()));
                }
                if (status.equals(IsProcessingType.SAP_DIEN_RA_TYPE)) {
                    mTvTimeKeepDeal.setText("Chưa bắt đầu");
                    mTvRank.setText("Chưa bắt đầu");
                    return;
                }
                if (!dealCampaignDetail.getData().getIsUserHunting()) {
                    mTvTimeKeepDeal.setText("Bạn chưa tích lũy");
                } else {
                    try {
                        long distance = dealCampaignDetail.getData().getTotalHoldTime() / 1000;
                        setTime(distance, mTvTimeKeepDeal, "", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mTvTimeKeepDeal.setText("0 ngày, 00 : 00 : 00");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mTvTimeKeepDeal.setText("Bạn chưa tích lũy");
                mTvRank.setText("Bạn chưa tích lũy");
            }
        }
    }

    public static void setTime(long s, TextView tv, String isProcessing, boolean isHoldTime) {
        try {
            String days = (int) (s / 86400) + " ngày ";
            String hours = String.valueOf((int) ((s % 86400) / 3600));
            String minutes = String.valueOf((int) ((s % 3600) / 60));
            String seconds = String.valueOf((int) ((s % 3600) % 60));
            if (days.length() == 1) {
                days = "0" + days;
            }

            if (hours.length() == 1) {
                hours = "0" + hours;
            }

            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }

            if (seconds.length() == 1) {
                seconds = "0" + seconds;
            }
            if (isHoldTime) {
                tv.setText(days + hours + ":" + minutes + ":" + seconds);
            } else {
                if (isProcessing.equals(IsProcessingType.SAP_DIEN_RA_TYPE)) {
//                    tv.setText("Bắt đầu sau " + days + hours + ":" + minutes + ":" + seconds);
                    tv.setText("Chưa đầu sau");
                } else {
                    tv.setText("Còn lại " + days + hours + ":" + minutes + ":" + seconds);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            tv.setText("0 ngày, 00 : 00 : 00");
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTilte;


        private TextView mTvNewPrice;
        private TextView mTvDisCount;
        private TextView mTvExpirationDate;
        private ProgressBar mProgressCountDown;
        private TextView mTvTimeCountDown;
        private TextView mTvOldPrice;
        private RichText tvTimeHold;
        private SubDealHeaderItemAdapter adapter;
        private RelativeLayout rllStatusDeal;
        private TextView tvStatusDeal;
        private ImageView imgProgress;
        private ImageView imgWin;
        private RelativeLayout rllProgress;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvTilte = (TextView) itemView.findViewById(R.id.tv_tilte);
            mTvNewPrice = (TextView) itemView.findViewById(R.id.tv_new_price);
            mTvDisCount = (TextView) itemView.findViewById(R.id.tv_dis_count);
            mTvExpirationDate = (TextView) itemView.findViewById(R.id.tv_expiration_date);
            mProgressCountDown = (ProgressBar) itemView.findViewById(R.id.progress_count_down);
            mTvTimeCountDown = (TextView) itemView.findViewById(R.id.tv_time_count_down);
            mTvOldPrice = (TextView) itemView.findViewById(R.id.tv_old_price);
            tvTimeHold = (RichText) itemView.findViewById(R.id.tv_time_hold);
            rllStatusDeal = itemView.findViewById(R.id.rll_status_deal);
            tvStatusDeal = itemView.findViewById(R.id.tv_status_deal);
            imgProgress = itemView.findViewById(R.id.img_progress);
            imgWin = itemView.findViewById(R.id.img_win);
            rllProgress = itemView.findViewById(R.id.rll_progress);
        }

        @SuppressLint("DefaultLocale")
        public void bindItem(int position) {
            Data data = dealCampaignDetail.getData();
            mTvTilte.setText(dealCampaignDetail.getData().getName());
            try {
                if (data.getPriceAfterPromo() != null) {
                    mTvNewPrice.setVisibility(View.VISIBLE);
                    mTvNewPrice.setText(F3SubDealAdapter.convertPrice(String.valueOf(data.getPriceAfterPromo())) + " đ");
                } else {
                    mTvNewPrice.setText("");
                    mTvNewPrice.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                try {
                    mTvNewPrice.setVisibility(View.VISIBLE);
                    mTvNewPrice.setText(data.getPriceAfterPromo() + " đ");
                } catch (Exception ex) {
                    mTvNewPrice.setVisibility(View.GONE);
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }


            try {
                if (data.getPriceBeforePromo() != null) {
                    mTvOldPrice.setVisibility(View.VISIBLE);
                    mTvOldPrice.setText(F3SubDealAdapter.convertPrice(String.valueOf(data.getPriceBeforePromo())) + " đ");
                } else {
                    mTvOldPrice.setText("");
                    mTvOldPrice.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                try {
                    mTvOldPrice.setVisibility(View.VISIBLE);
                    mTvOldPrice.setText(data.getPriceBeforePromo() + " đ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mTvOldPrice.setVisibility(View.GONE);
                }
                e.printStackTrace();
            }

            try {
                mTvOldPrice.setPaintFlags(mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                try {
                    if (data.getDisplayType() != null) {
                        if (data.getDisplayType() == DiscountDisplayType.PERCENT_TYPE) {
                            mTvDisCount.setText(data.getValuePromotion().toString() + "%");
                            mTvDisCount.setVisibility(View.VISIBLE);
                        } else {
                            mTvDisCount.setVisibility(View.GONE);
                        }

                    } else {
                        mTvDisCount.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    mTvDisCount.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                if (data.getExpireDate() != null)
                    mTvExpirationDate.setText("HSD: " + DateUtltils.timeToString18(data.getExpireDate()));
                mProgressCountDown.setProgress(GridDealInDealHomeAdapter.Companion.getPercentProgress(data.getBeginAt(), data.getEndAt()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (data.getIsProcessing() != null) {
                    String status = data.getIsProcessing();
                    if (status.equals(IsProcessingType.KET_THUC_TYPE)) {
                        mProgressCountDown.setProgress(0);
                        if (data.getRanking() == 1) {
                            rllStatusDeal.setBackgroundColor(Color.parseColor("#01B819"));
                            tvStatusDeal.setText("Bạn đã giành được CTKM này!");
                            imgWin.setVisibility(View.VISIBLE);
                            rllProgress.setVisibility(View.GONE);
                        } else {
                            rllStatusDeal.setBackgroundColor(Color.parseColor("#FF0909"));
                            tvStatusDeal.setText("Chương trình đã kết thúc!");
                            mTvTimeCountDown.setText("Đã hết hạn");
                            mProgressCountDown.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.f2_rounded_corners_progress_bar_end));
                        }

                    } else if (status.equals(IsProcessingType.DANG_DIEN_RA_TYPE)) {
                        rllStatusDeal.setBackgroundColor(Color.parseColor("#01B819"));
                        tvStatusDeal.setText("Chương trình đang diễn ra!");
                        long timeStamp = data.getEndAt();
                        long myCurrentTimeMillis = System.currentTimeMillis();
                        if (myCurrentTimeMillis > timeStamp) {
                            mTvTimeCountDown.setText("Còn lại 0 ngày");
                        } else {
                            long distance = (timeStamp - myCurrentTimeMillis) / 1000;
                            setTime(distance, mTvTimeCountDown, IsProcessingType.DANG_DIEN_RA_TYPE, false);

                        }
                    } else if (status.equals(IsProcessingType.SAP_DIEN_RA_TYPE)) {
                        rllStatusDeal.setBackgroundColor(Color.parseColor("#E9BB00"));
                        tvStatusDeal.setText("Chương trình chưa bắt đầu!");
                        long timeStamp = data.getBeginAt();
                        long myCurrentTimeMillis = System.currentTimeMillis();
                        if (myCurrentTimeMillis > timeStamp) {
                            mTvTimeCountDown.setText("Bắt đầu sau 0 ngày");
                        } else {
                            long distance = (timeStamp - myCurrentTimeMillis) / 1000;
                            setTime(distance, mTvTimeCountDown, IsProcessingType.SAP_DIEN_RA_TYPE, false);
                        }

                    }
                } else {
                    mTvTimeCountDown.setText("Đã hết hạn");
                    mProgressCountDown.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.f2_rounded_corners_progress_bar_end));
                }
            } catch (Exception e) {
                e.printStackTrace();
                rllStatusDeal.setBackgroundColor(Color.parseColor("#FF0909"));
                tvStatusDeal.setText("Chương trình đã kết thúc!");
                mTvTimeCountDown.setText("Đã hết hạn");
                mProgressCountDown.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.f2_rounded_corners_progress_bar_end));
            }

            // set time total hold time
            try {
                long distance = data.getTotalHoldTime() / 1000;
                setTime(distance, tvTimeHold, "", true);
            } catch (Exception e) {
                e.printStackTrace();
                tvTimeHold.setText("0 ngày, 00 : 00 : 00");
            }
        }
    }

    public interface ILoadDataDeal {
        void onLoadDataDeal(String url, String rewardStatus);
    }

    public interface LoadData {
        void onLoadDataComment(String id);

        void onTabSubDealClick(int position);
    }

}
