package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelnews.CommentInDetailTravelNewsAdapter;
import com.namviet.vtvtravel.view.f3.deal.Utils;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header2Adapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealFilterAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader2;
import com.namviet.vtvtravel.view.f3.deal.model.Rank;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.ornach.richtext.RichText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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

    public DealAdapter(DealCampaignDetail data, Context mContext, LoadData loadData) {
        this.dealCampaignDetail = data;
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


    public class MoreViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclFilterDeal;
        private RecyclerView rclContent;

        private RecyclerView rcvTabHeader1;
        private F3Header2Adapter mF3Header2Adapter;

        public MoreViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            rclFilterDeal = itemView.findViewById(R.id.rclFilterDeal);
            rclContent = itemView.findViewById(R.id.rclContent);

            rcvTabHeader1 = itemView.findViewById(R.id.rcv_tab_header1);
        }

        public void bindItem(int position) {
            rclContent.setAdapter(new GridDealAdapter());
            rclFilterDeal.setAdapter(new DealFilterAdapter(mContext, null, null));

            mF3Header2Adapter = new F3Header2Adapter(0, null, itemView.getContext(), new F3Header2Adapter.ClickTab() {
                @Override
                public void onClickTab(int position) {
                    Toast.makeText(itemView.getContext(), "Tab click: " + position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new OnClickTabHeader2(position));
                }
            }, false);
            rcvTabHeader1.setAdapter(mF3Header2Adapter);
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
        private String url = "<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> " +
                "    <figcaption style=\"text-align: center;padding:5px;\"><em>ảnh lấy trên mạng. </em></figcaption>\n" +
                "    </figure>";
        private String url2 = "<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> " +
                "    <figcaption style=\"text-align: center;padding:5px;\"><em>ảnh lấy trên mạng. </em></figcaption>\n" +
                "    </figure>"+"<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> " +
                "    <figcaption style=\"text-align: center;padding:5px;\"><em>ảnh lấy trên mạng. </em></figcaption>\n" +
                "    </figure>";
        private String url3 = "<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> " +
                "    <figcaption style=\"text-align: center;padding:5px;\"><em>ảnh lấy trên mạng. </em></figcaption>\n" +
                "    </figure>"+"<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> " +
                "    <figcaption style=\"text-align: center;padding:5px;\"><em>ảnh lấy trên mạng. </em></figcaption>\n" +
                "    </figure>"+"<head><style>body{font-family:'roboto','Roboto',sans-serif;}</style></head><body><p style=\"text-align: justify;\"><strong>Top 6 địa điểm du lịch đẹp \"đụng thiên đường\" ở Bình Phước</strong></p>\n" +
                "    <p style=\"text-align: justify;\"><em><span style=\"font-weight: 400;\">Một Bình Phước tuyệt đẹp, hoang sơ luôn là chốn dừng chân lý tưởng cho những tâm hồn yêu khám phá thiên nhiên. Hè rồi xách balo lên và trải nghiệm miền đất đặc biệt này thôi!</span></em></p>\n" +
                "    <figure class=\"image\" style=\"text-align: center;margin:0;padding:0;\"> " +
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
            webView = itemView.findViewById(R.id.web_view);
            urls = new ArrayList<>();
            urls.add(url);
            urls.add(url2);
            urls.add(url3);
            // mTabLayout.setupWithViewPager(mPagerContent);

        }

        private void resetTabColor() {
            tvTab1.setTextColor(mContext.getResources().getColor(R.color.gray_99));
            tvTab2.setTextColor(mContext.getResources().getColor(R.color.gray_99));
            tvTab3.setTextColor(mContext.getResources().getColor(R.color.gray_99));
        }

        public void bindItem(int position) {
//            adapter = new InfomationDealAdapter(mContext, urls);
//            mPagerContent.setAdapter(adapter);
//            mPagerContent.setOffscreenPageLimit(3);
            resetTabColor();
            tvTab1.setTextColor(mContext.getResources().getColor(R.color.black));
//            mPagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
            webView.loadDataWithBaseURL("", urls.get(0), "text/html", "UTF-8", null);
            tvTab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabSelected(0);
                    webView.loadDataWithBaseURL("", urls.get(0), "text/html", "UTF-8", null);
                }
            });
            tvTab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabSelected(1);
                    webView.loadDataWithBaseURL("", urls.get(1), "text/html", "UTF-8", null);
                }
            });
            tvTab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabSelected(2);
                    webView.loadDataWithBaseURL("", urls.get(2), "text/html", "UTF-8", null);
                }
            });
            mBtnCollapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup.LayoutParams params = webView.getLayoutParams();
                    if(!isCollapse){
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        webView.setLayoutParams(params);
                        mBtnCollapse.setText("Ẩn bớt");
                        isCollapse = true;
                    }
                    else {
                        params.height = com.brucetoo.videoplayer.utils.Utils.dp2px(mContext, 150);
                        webView.setLayoutParams(params);
                        mBtnCollapse.setText("Xem thêm");
                        isCollapse = false;
                    }
                }
            });
        }
        private void onTabSelected(int position){
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
        private TextView tvCode;
        private ImageView imgCopy;
        private ImageView imgShare;

        public HotLineViewHolder(View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_code);
            imgCopy = itemView.findViewById(R.id.img_copy);
            imgShare = itemView.findViewById(R.id.img_share);
        }

        public void bindItem(int position) {
            tvCode.setText("D "+ dealCampaignDetail.getData().getCode());
            imgCopy.setOnClickListener(new View.OnClickListener() {
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

        public UserObjectViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvCode = (TextView) itemView.findViewById(R.id.tv_code);
            mHuntingCount = (TextView) itemView.findViewById(R.id.hunting_count);
            mTvTimeKeepDeal = (TextView) itemView.findViewById(R.id.tv_time_keep_deal);
            mTvRank = (TextView) itemView.findViewById(R.id.tv_rank);
        }

        public void bindItem(int position) {
            mTvCode.setText(dealCampaignDetail.getData().getCode());
            mHuntingCount.setText(String.format("%d+", dealCampaignDetail.getData().getUserHuntingCount()));
            mTvTimeKeepDeal.setText(Utils.CalendarUtils.getTimeHold(dealCampaignDetail.getData().getTotalHoldTime()));
            mTvRank.setText(String.valueOf(dealCampaignDetail.getData().getPromptRank()));
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

        }


        @SuppressLint("DefaultLocale")
        public void bindItem(int position) {
            try {
                mTvTilte.setText(dealCampaignDetail.getData().getName());
                mTvOldPrice.setText(String.format("%dđ", dealCampaignDetail.getData().getPriceBeforePromo()));
                mTvNewPrice.setText(String.format("%dđ", dealCampaignDetail.getData().getPriceAfterPromo()));
                mTvOldPrice.setPaintFlags(mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mTvDisCount.setText(String.format("%d%%", dealCampaignDetail.getData().getValuePromotion()));
                String expirationDate = Utils.CalendarUtils.ConvertMilliSecondsToFormattedDate(dealCampaignDetail.getData().getEndAt());
                mTvExpirationDate.setText(expirationDate);
                tvTimeHold.setText(Utils.CalendarUtils.getTimeHold(dealCampaignDetail.getData().getTotalHoldTime()));
                mTvTimeCountDown.setText(Utils.CalendarUtils.getDayLeft(dealCampaignDetail.getData().getEndAt()));
                mProgressCountDown.setProgress(Utils.CalendarUtils.getPercentProgress(dealCampaignDetail.getData().getBeginAt(), dealCampaignDetail.getData().getEndAt()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface LoadData {
        void onLoadDataComment(String id);
    }

}
