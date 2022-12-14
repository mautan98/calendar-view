package com.namviet.vtvtravel.adapter.newhome;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubAppVoucherAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubDealAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubDiscoverAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubFavoriteDestinationAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubHeaderAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubLiveTvAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubNearByAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubPromotionPartnerAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubRecentViewAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubSlideImageInHeaderAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubSuggestionLocationAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubVideoAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubVoucherNowAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.tab.TabDiscoverAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.tab.TabSuggestionLocationAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.tab.TabVoucherNowAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnClickVideoInMenu;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.response.newhome.AppFavoriteDestinationResponse;
import com.namviet.vtvtravel.response.newhome.AppPromotionPartnerResponse;
import com.namviet.vtvtravel.response.newhome.AppVideoResponse;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;
import com.namviet.vtvtravel.response.newhome.BaseResponseSpecialNewHome;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppDiscoverResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppVoucherNowResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.BigLocationActivity;
import com.namviet.vtvtravel.view.f2.CreateTripActivity;
import com.namviet.vtvtravel.view.f2.FullVideoActivity;
import com.namviet.vtvtravel.view.f2.LiveTVActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.NearbyExperienceActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TopExperienceActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3SubDealAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3TabDealInHomeAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealHomeActivity;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.Item;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.ItemGenerator;
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;
import com.zhpan.indicator.IndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator2;

public class NewHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int APP_SEARCH_BOX = 0;
    private static final int APP_VOUCHER = 1;
    private static final int APP_DEAL = 2;
    private static final int APP_EXPERIENCES_NEARBY = 3;
    private static final int APP_PROMOTION_PARTNER = 4;
    private static final int APP_FAVORITE_DESTINATION = 5;
    private static final int APP_CREATE_TOUR = 6;
    private static final int APP_NEAR_BY = 7;
    private static final int APP_DISCOVER = 8;
    private static final int APP_VIDEO = 9;
    private static final int APP_VOUCHER_NOW = 10;
    private static final int APP_OTHER = 11;
    private static final int APP_TOP_EXPERIENCE = 12;
    private static final int APP_TV = 14;
    private static final int APP_RECENTLY_VIEWED = 15;

    private final Context context;
    private final HomeServiceResponse homeServiceResponse;
    private final LoadData loadData;
    private final ClickUserView clickUserView;
    private final ClickButtonRegisterNow clickButtonRegisterNow;
    private final ClickItemSmallLocation clickItemSmallLocation;
    private final ClickSearch clickSearch;
    private final NewHomeFragment newHomeFragment;
    private final BaseViewModel viewModel;
    private Timer timer;
    private int pageNumber;


    private ArrayList<String> idsViewed = new ArrayList<>();

    public ViewPager getViewPagerVoucher() {
        return pager;
    }

    private ViewPager pager;

    public Timer getTimer() {
        return timer;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public class TypeString {
        public static final String APP_SEARCH_BOX = "APP_SEARCH_BOX";
        public static final String APP_VOUCHER = "APP_VOUCHER";
        public static final String APP_DEAL = "APP_DEAL";
        public static final String APP_TOP_EXPERIENCE = "APP_TOP_EXPERIENCE";
        public static final String APP_EXPERIENCES_NEARBY = "APP_EXPERIENCES_NEARBY";
        public static final String APP_PROMOTION_PARTNER = "APP_PROMOTION_PARTNER";
        public static final String APP_FAVORITE_DESTINATION = "APP_FAVORITE_DESTINATION";
        public static final String APP_CREATE_TOUR = "APP_CREATE_TOUR";
        public static final String APP_NEAR_BY = "APP_NEAR_BY";
        public static final String APP_DISCOVER = "APP_DISCOVER";
        public static final String APP_VIDEO = "APP_VIDEO";
        public static final String APP_VOUCHER_NOW = "APP_VOUCHER_NOW";
        public static final String APP_TV = "APP_TV";
        public static final String APP_RECENTLY_VIEWED = "APP_RECENTLY_VIEWED";
        public static final String APP_HOME = "APP_HOME";
    }

    public NewHomeAdapter(Context context, HomeServiceResponse homeServiceResponse, LoadData loadData, ClickUserView clickUserView, ClickButtonRegisterNow clickButtonRegisterNow,
                          ClickItemSmallLocation clickItemSmallLocation, ClickSearch clickSearch, NewHomeFragment newHomeFragment, BaseViewModel viewModel) {
        this.context = context;
        this.homeServiceResponse = homeServiceResponse;
        this.loadData = loadData;
        this.clickUserView = clickUserView;
        this.clickButtonRegisterNow = clickButtonRegisterNow;
        this.clickItemSmallLocation = clickItemSmallLocation;
        this.clickSearch = clickSearch;
        this.newHomeFragment = newHomeFragment;
        this.viewModel = viewModel;
    }

    @Override
    public int getItemViewType(int position) {
        List<ItemHomeService> itemHomeServices = homeServiceResponse.getData();
        if (itemHomeServices != null) {
            String code = itemHomeServices.get(position).getCode();
            switch (code) {
                case "APP_SEARCH_BOX":
                    return APP_SEARCH_BOX;
                case "APP_VOUCHER":
                    return APP_VOUCHER;
                case "APP_DEAL":
                    return APP_DEAL;
                case "APP_TOP_EXPERIENCE":
                    return APP_TOP_EXPERIENCE;
                case "APP_EXPERIENCES_NEARBY":
                    return APP_EXPERIENCES_NEARBY;
                case "APP_PROMOTION_PARTNER":
                    return APP_PROMOTION_PARTNER;
                case "APP_FAVORITE_DESTINATION":
                    return APP_FAVORITE_DESTINATION;
                case "APP_CREATE_TOUR":
                    return APP_CREATE_TOUR;
                case "APP_NEAR_BY":
                    return APP_NEAR_BY;
                case "APP_DISCOVER":
                    return APP_DISCOVER;
                case "APP_VIDEO":
                    return APP_VIDEO;
                case "APP_VOUCHER_NOW":
                    return APP_VOUCHER_NOW;
                case "APP_TV":
                    return APP_TV;
                case "APP_RECENTLY_VIEWED":
                    return APP_RECENTLY_VIEWED;

            }
        }
        return APP_OTHER;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == APP_SEARCH_BOX) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_header_2, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == APP_VOUCHER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_voucher, parent, false);
            return new VoucherViewHolder(v);
        } else if (viewType == APP_DEAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_deal_of_users, parent, false);
            return new DealViewHolder(v);
        } else if (viewType == APP_TOP_EXPERIENCE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_top_experience, parent, false);
            return new TopExperienceViewHolder(v);
        } else if (viewType == APP_EXPERIENCES_NEARBY) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_suggestion_location, parent, false);
            return new SuggestionLocationViewHolder(v);
        } else if (viewType == APP_PROMOTION_PARTNER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_partner_link, parent, false);
            return new PromotionPartner(v);
        } else if (viewType == APP_FAVORITE_DESTINATION) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_favor_place, parent, false);
            return new FavoriteDestination(v);
        } else if (viewType == APP_CREATE_TOUR) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_app_create_tour, parent, false);
            return new CreateTourViewHolder(v);
        } else if (viewType == APP_NEAR_BY) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_near_place, parent, false);
            return new NearByViewHolder(v);
        } else if (viewType == APP_DISCOVER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_discover, parent, false);
            return new DiscoverViewHolder(v);
        } else if (viewType == APP_VIDEO) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_app_video, parent, false);
            return new VideoViewHolder(v);
        } else if (viewType == APP_VOUCHER_NOW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_voucher_now, parent, false);
            return new VoucherNowViewHolder(v);
        } else if (viewType == APP_TV) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_home_live_tv, parent, false);
            return new LiveTVViewHolder(v);
        } else if (viewType == APP_RECENTLY_VIEWED) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_recent_view, parent, false);
            return new RecentViewViewHolder(v);
        } else if (viewType == APP_OTHER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_home_layout_other, parent, false);
            return new OtherViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == APP_SEARCH_BOX) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_VOUCHER) {
                ((VoucherViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_DEAL) {
                ((DealViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_TOP_EXPERIENCE) {
                ((TopExperienceViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_EXPERIENCES_NEARBY) {
                ((SuggestionLocationViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_PROMOTION_PARTNER) {
                ((PromotionPartner) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_FAVORITE_DESTINATION) {
                ((FavoriteDestination) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_CREATE_TOUR) {
                ((CreateTourViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_NEAR_BY) {
                ((NearByViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_DISCOVER) {
                ((DiscoverViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_VIDEO) {
                ((VideoViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_VOUCHER_NOW) {
                ((VoucherNowViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_TV) {
                ((LiveTVViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_RECENTLY_VIEWED) {
                ((RecentViewViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == APP_OTHER) {
                ((OtherViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return homeServiceResponse.getData().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private SubHeaderAdapter subHeaderAdapter;
        private final RecyclerView rclHeader;
        private final ViewPager vpPromotion;
        private final LinearLayout layoutSearch;
        private SubSlideImageInHeaderAdapter subSlideImageInHeaderAdapter;
        private final IndicatorView indicatorView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rclHeader = itemView.findViewById(R.id.rclHeader);
            layoutSearch = itemView.findViewById(R.id.layoutSearch);
            vpPromotion = itemView.findViewById(R.id.vpPromotionSlide);
            indicatorView = itemView.findViewById(R.id.indicator_view);
            layoutSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickSearch.onClickSearch();
                }
            });

        }

        public void bindItem(int position) {
            try {
                subSlideImageInHeaderAdapter = new SubSlideImageInHeaderAdapter(context, homeServiceResponse.getData().get(position).getBackground_urls(), new SubSlideImageInHeaderAdapter.IOnSlideImageHeaderClick() {
                    @Override
                    public void onSlideImageHeaderClick(int position) {
//                        Toast.makeText(context, "Banner clicked: "+position, Toast.LENGTH_SHORT).show();
                    }
                });
                vpPromotion.setAdapter(subSlideImageInHeaderAdapter);
                OverScrollDecoratorHelper.setUpOverScroll(vpPromotion);
                indicatorView.setPageSize(vpPromotion.getAdapter().getCount());
                indicatorView.setSliderHeight(context.getResources().getDimension(R.dimen.dp_1));
                indicatorView.setSliderWidth(context.getResources().getDimension(R.dimen.dp_9));
                vpPromotion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        indicatorView.onPageSelected(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            String url_deal = homeServiceResponse.getData().get(2).getLink_home_deal();
            subHeaderAdapter = new SubHeaderAdapter(homeServiceResponse.getData().get(position).getMenus(), url_deal, context);
            rclHeader.setAdapter(subHeaderAdapter);

        }
    }


    public class TopExperienceViewHolder extends RecyclerView.ViewHolder {
        private SubSuggestionLocationAdapter subSuggestionLocationAdapter;
        private final RecyclerView rclContent;
        private final TextView btnSeeMore;
        private final TextView tvTitle;

        public TopExperienceViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            try {
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(rclContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void bindItem(int position) {
            ItemAppExperienceResponse itemAppExperienceResponse = (ItemAppExperienceResponse) homeServiceResponse.getData().get(position).getDataExtra();
            if (itemAppExperienceResponse == null) {
                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_TOP_EXPERIENCE);
            }

            subSuggestionLocationAdapter = new SubSuggestionLocationAdapter(context, itemAppExperienceResponse.getItems(), viewModel);
            rclContent.setAdapter(subSuggestionLocationAdapter);


            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TopExperienceActivity.startScreen(context, homeServiceResponse.getData().get(position), TopExperienceActivity.Type.TOP_EXPERIENCE);
                }
            });

            try {
                tvTitle.setText(homeServiceResponse.getData().get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class SuggestionLocationViewHolder extends RecyclerView.ViewHolder implements NewHomeFragment.IOnClickTabReloadData {
        private SubSuggestionLocationAdapter subSuggestionLocationAdapter;
        private TabSuggestionLocationAdapter tabSuggestionLocationAdapter;
        private final RecyclerView rclContent;
        private final RecyclerView rclTab;
        private final TextView tvTitle;
        private final TextView btnSeeMore;
        private final ShimmerFrameLayout mShimmerFrameLayout;
        private int mPosition;

        public SuggestionLocationViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            rclTab = itemView.findViewById(R.id.rclTab);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
            mShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            mShimmerFrameLayout.setVisibility(View.VISIBLE);
            mShimmerFrameLayout.startShimmer();

            try {
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(rclContent);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        public void bindItem(int position) {
            mPosition = position;

            ItemAppExperienceResponse itemAppExperienceResponse = (ItemAppExperienceResponse) homeServiceResponse.getData().get(position).getDataExtraSecond();
            if (itemAppExperienceResponse == null) {
                List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
                loadData.onLoadDataFloorSecond(items.get(0).getContent_link(), TypeString.APP_EXPERIENCES_NEARBY, true);
            }

            subSuggestionLocationAdapter = new SubSuggestionLocationAdapter(context, itemAppExperienceResponse.getItems(), viewModel);
            rclContent.setAdapter(subSuggestionLocationAdapter);

            tabSuggestionLocationAdapter = new TabSuggestionLocationAdapter(homeServiceResponse.getData().get(position).getPositionClick(), homeServiceResponse.getData().get(position).getItems(), context, new TabSuggestionLocationAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {
                    try {
                        newHomeFragment.setmIOnClickTabReloadData(SuggestionLocationViewHolder.this);
                        homeServiceResponse.getData().get(position).setPositionClick(positionClick);
                        List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
                        loadData.onLoadDataFloorSecond(items.get(positionClick).getContent_link(), TypeString.APP_EXPERIENCES_NEARBY, true);
                        mShimmerFrameLayout.setVisibility(View.VISIBLE);
                        rclContent.setVisibility(View.INVISIBLE);
                        mShimmerFrameLayout.startShimmer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            rclTab.setAdapter(tabSuggestionLocationAdapter);

            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NearbyExperienceActivity.startScreen(context, homeServiceResponse.getData().get(position));
                }
            });
//            tvTitle.setText();


            try {
                tvTitle.setText(homeServiceResponse.getData().get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mShimmerFrameLayout.stopShimmer();
            mShimmerFrameLayout.setVisibility(View.GONE);
            rclContent.setVisibility(View.VISIBLE);

        }

        @Override
        public void onTabClick(String code) {
            try {
                if (code.equals(TypeString.APP_EXPERIENCES_NEARBY)) {
                    ItemAppExperienceResponse itemAppExperienceResponse = (ItemAppExperienceResponse) homeServiceResponse.getData().get(mPosition).getDataExtraSecond();
                    if (itemAppExperienceResponse == null) {
                        List<ItemHomeService.Item> items = homeServiceResponse.getData().get(APP_EXPERIENCES_NEARBY).getItems();
                        loadData.onLoadDataFloorSecond(items.get(0).getContent_link(), TypeString.APP_EXPERIENCES_NEARBY, true);
                    }
                    subSuggestionLocationAdapter = new SubSuggestionLocationAdapter(context, itemAppExperienceResponse.getItems(), viewModel);
                    rclContent.setAdapter(subSuggestionLocationAdapter);
                    mShimmerFrameLayout.stopShimmer();
                    mShimmerFrameLayout.setVisibility(View.GONE);
                    rclContent.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder {
        private final PagerContainer container;

        private final TextView tvTipUser;
        private final TextView btnRegisterNow;
        private final IndefinitePagerIndicator vpIndicator;
        private final CircleIndicator indicator;

        private int pageIndex = 0;
        private ImageView imvNodata;

        public VoucherViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.pager_container);
            tvTipUser = itemView.findViewById(R.id.tvTipUser);
            btnRegisterNow = itemView.findViewById(R.id.btnRegisterNow);
            vpIndicator = itemView.findViewById(R.id.vpIndicator);
            indicator = itemView.findViewById(R.id.indicator);
            imvNodata = itemView.findViewById(R.id.imv_no_voucher);
        }

        public void bindItem(int position) {
            AppVoucherResponse appVoucherResponse = (AppVoucherResponse) homeServiceResponse.getData().get(position).getDataExtra();
            if (appVoucherResponse == null) {
                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_VOUCHER);
            }
            pager = container.getViewPager();
            pager.setAdapter(new SubAppVoucherAdapter(context, appVoucherResponse, new SubAppVoucherAdapter.ClickItem() {
                @Override
                public void onClickItem(AppVoucherResponse.Item item) {
                    try {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLinkVoucher()));
//                        context.startActivity(browserIntent);

                        ListVoucherResponse.Data.Voucher voucher = new Gson().fromJson(new Gson().toJson(item), ListVoucherResponse.Data.Voucher.class);
                        TravelVoucherActivity.startScreenDetail(context, true, TravelVoucherActivity.OpenType.DETAIL, voucher);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new SubAppVoucherAdapter.DataListener() {
                @Override
                public void onHaveData(boolean isShowNoDataView) {
                    if(isShowNoDataView){
                        imvNodata.setVisibility(View.VISIBLE);
                    }else {
                        imvNodata.setVisibility(View.GONE);
                    }
                }
            }));
            if (appVoucherResponse != null) {
                setPageNumber(appVoucherResponse.getItems().size());
            }
            pager.setClipChildren(false);
            pager.setOffscreenPageLimit(15);
            container.setPageItemClickListener(new PageItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });
            new CoverFlow.Builder().with(pager).scale(0.0f).pagerMargin(10f).spaceSize(0f).build();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvTipUser.setText(Html.fromHtml(homeServiceResponse.getData().get(position).getTipUser(), Html.FROM_HTML_MODE_COMPACT));
            }else {
                tvTipUser.setText(Html.fromHtml(homeServiceResponse.getData().get(position).getTipUser()));
            }
            if (context.getString(R.string.tip_user_vip).equals(homeServiceResponse.getData().get(position).getTipUser())) {
                tvTipUser.setText(context.getString(R.string.tip_user_vip));
            }
            if (homeServiceResponse.getData().get(position).isShowBtnRegisterNow()) {
                btnRegisterNow.setVisibility(View.VISIBLE);
            } else {
                btnRegisterNow.setVisibility(View.GONE);
            }

            btnRegisterNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickButtonRegisterNow.onClickButtonRegisterNow();
                }
            });

            try {
                vpIndicator.attachToViewPager(pager)
                ;

                indicator.setViewPager(pager)
                ;

            } catch (Exception e) {
                e.printStackTrace();
            }


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        pager.setCurrentItem(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 500);
            pageSwitcher(10000);
            listenerOnpageChange();

        }

        public void pageSwitcher(long milseconds) {
            try {
                if (timer == null)
                    timer = new Timer();
                if (pageNumber > 1)
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                Log.d("NewHomeAdapter", "runing timer: ");
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (pageIndex == pageNumber - 1) {
                                                pageIndex = 0;
                                            } else {
                                                pageIndex++;
                                            }
                                            pager.setCurrentItem(pageIndex, true);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 10000, milseconds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void listenerOnpageChange() {
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    pageIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }


    public class DealViewHolder extends RecyclerView.ViewHolder implements Observer {
        private final RecyclerView recyclerNearPlace;
        private final ShimmerFrameLayout shimmer;
        private SubDealAdapter subDealAdapter;
        private final TextView btnSeeMore;
        private final TextView tvTitle;
        private final TextView tvDescription;
        private F3SubDealAdapter f3SubDealAdapter;
        private F3TabDealInHomeAdapter f3TabDealAdapter;
        private final RecyclerView rclContent;
        private final RecyclerView rclTab;
        private final DealViewModel dealViewModel;
        private DealResponse dealResponse;
        private final View layoutNoData;
        private final View viewWhite;

        public DealViewHolder(View itemView) {
            super(itemView);
            dealViewModel = new DealViewModel();
            dealViewModel.addObserver(this);
            rclContent = itemView.findViewById(R.id.rclContent);
            rclTab = itemView.findViewById(R.id.rclTab);
            recyclerNearPlace = itemView.findViewById(R.id.recyclerNearPlace);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            shimmer = itemView.findViewById(R.id.shimmer_view_container);
            layoutNoData = itemView.findViewById(R.id.layoutNoData);
            viewWhite = itemView.findViewById(R.id.viewWhite);

            try {
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(recyclerNearPlace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void bindItem(int position) {
//            BaseResponseSpecialNewHome appDealResponse = (BaseResponseSpecialNewHome) homeServiceResponse.getData().get(position).getDataExtra();
//            if (appDealResponse == null) {
//                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_DEAL);
//            }

//            subDealAdapter = new SubDealAdapter(context, appDealResponse.getData(), new SubDealAdapter.ClickItem() {
//                @Override
//                public void onClickItem(AppDealResponse.Data data) {
//                    try {
//                        DetailDealWebviewActivity.startScreen(context, data.getDetailLink());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            recyclerNearPlace.setAdapter(subDealAdapter);

            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //  DetailDealWebviewActivity.startScreen(context, homeServiceResponse.getData().get(position).getLink_home_deal());
                        DealHomeActivity.Companion.startScreen(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


            f3TabDealAdapter = new F3TabDealInHomeAdapter(0,  (ArrayList<Item>) ItemGenerator.demoTabDealList(), context, new F3TabDealInHomeAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {
                    viewWhite.setVisibility(View.VISIBLE);
                    shimmer.setVisibility(View.VISIBLE);
                    dealResponse = null;
                    f3SubDealAdapter = new F3SubDealAdapter(context, dealResponse, viewModel,true);
                    rclContent.setAdapter(f3SubDealAdapter);

                    if (positionClick == 0) {
                        dealViewModel.getDeal(WSConfig.HOST_DEAL_CAMPAIGN_HOME);
                    } else {
                        dealViewModel.getDeal(WSConfig.HOST_DEAL_HOME);
                    }
//                    try {
//                        newHomeFragment.setmIOnClickTabReloadData(SuggestionLocationViewHolder.this);
//                        homeServiceResponse.getData().get(position).setPositionClick(positionClick);
//                        List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
//                        loadData.onLoadDataFloorSecond(items.get(positionClick).getContent_link(), TypeString.APP_EXPERIENCES_NEARBY, true);
//                        mShimmerFrameLayout.setVisibility(View.VISIBLE);
//                        rclContent.setVisibility(View.INVISIBLE);
//                        mShimmerFrameLayout.startShimmer();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }
            });

            rclTab.setAdapter(f3TabDealAdapter);

            if (dealResponse == null) {
                viewWhite.setVisibility(View.VISIBLE);
                shimmer.setVisibility(View.VISIBLE);
                dealViewModel.getDeal(WSConfig.HOST_DEAL_CAMPAIGN_HOME);
            }

            try {
                tvTitle.setText(homeServiceResponse.getData().get(position).getName());
                tvDescription.setText(homeServiceResponse.getData().get(position).getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(Observable observable, Object o) {
            viewWhite.setVisibility(View.GONE);
            shimmer.setVisibility(View.GONE);
            layoutNoData.setVisibility(View.VISIBLE);
            if (observable instanceof DealViewModel) {
                if (o instanceof DealResponse) {
                    try {
                        dealResponse = (DealResponse) o;
                        f3SubDealAdapter = new F3SubDealAdapter(context, dealResponse, viewModel,true);
                        rclContent.setAdapter(f3SubDealAdapter);

                        try {
                            if(dealResponse.getData().getContent().size() > 0){
                                layoutNoData.setVisibility(View.GONE);
                            }else {
                                layoutNoData.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            layoutNoData.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class PromotionPartner extends RecyclerView.ViewHolder {
        private final RecyclerView recyclerPartnerLink;
        private SubPromotionPartnerAdapter subPromotionPartnerAdapter;
        private final IndefinitePagerIndicator vpIndicator;
        private final CircleIndicator2 indicator;

        public PromotionPartner(View itemView) {
            super(itemView);
            recyclerPartnerLink = itemView.findViewById(R.id.recyclerPartnerLink);
            vpIndicator = itemView.findViewById(R.id.vpIndicator);
            indicator = itemView.findViewById(R.id.indicator);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void bindItem(int position) {
//            SnapHelper helper = new LinearSnapHelper();
//            helper.attachToRecyclerView(recyclerPartnerLink);

            AppPromotionPartnerResponse appPromotionPartnerResponse = (AppPromotionPartnerResponse) homeServiceResponse.getData().get(position).getDataExtra();
            if (appPromotionPartnerResponse == null) {
                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_PROMOTION_PARTNER);
            }
            subPromotionPartnerAdapter = new SubPromotionPartnerAdapter(context, appPromotionPartnerResponse.getItems());
            recyclerPartnerLink.setAdapter(subPromotionPartnerAdapter);
            recyclerPartnerLink.setItemViewCacheSize(6);

            try {
                vpIndicator.attachToRecyclerView(recyclerPartnerLink);
                PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                pagerSnapHelper.attachToRecyclerView(recyclerPartnerLink);
                indicator.attachToRecyclerView(recyclerPartnerLink, pagerSnapHelper);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                recyclerPartnerLink.addOnScrollListener(new RecyclerView.OnScrollListener(){
                    @Override
                    public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        try {
                            if(newState == 0){
                                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerPartnerLink.getLayoutManager());
                                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                                if(!checkID(appPromotionPartnerResponse.getItems().get(firstVisiblePosition).getId()) && firstVisiblePosition != 0) {
                                    try {
                                        TrackingAnalytic.postEvent(TrackingAnalytic.VIEW_PARTNER_BANNER_AD, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HOME, TrackingAnalytic.ScreenTitle.HOME).setPartner_banner_ad_id(appPromotionPartnerResponse.getItems().get(firstVisiblePosition).getId()).setScreen_class(this.getClass().getName()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    idsViewed.add(appPromotionPartnerResponse.getItems().get(firstVisiblePosition).getId());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private boolean checkID(String id){
            for (int i = 0; i < idsViewed.size(); i++) {
                if(id.equals(idsViewed.get(i))){
                    return true;
                }
            }
            return false;
        }
    }


    public class FavoriteDestination extends RecyclerView.ViewHolder {
        private SubFavoriteDestinationAdapter subFavoriteDestinationAdapter;
        private final RecyclerView recyclerFavorPlace;

        private final TextView tvTitle;
        private final TextView tvDescription;


        public FavoriteDestination(View itemView) {
            super(itemView);
            recyclerFavorPlace = itemView.findViewById(R.id.recyclerFavorPlace);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);

//            try {
//                SnapHelper helper = new LinearSnapHelper();
//                helper.attachToRecyclerView(recyclerFavorPlace);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BigLocationActivity.startScreen((Activity) context, null, true);
                }
            });

        }

        public void bindItem(int position) {
            AppFavoriteDestinationResponse appFavoriteDestinationResponse = (AppFavoriteDestinationResponse) homeServiceResponse.getData().get(position).getDataExtra();
            if (appFavoriteDestinationResponse == null) {
                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_FAVORITE_DESTINATION);
            }

            subFavoriteDestinationAdapter = new SubFavoriteDestinationAdapter(context, appFavoriteDestinationResponse.getItems(), new SubFavoriteDestinationAdapter.ClickItem() {
                @Override
                public void onClickItem(String regionId) {
                    BigLocationActivity.startScreen((Activity) context, regionId, false);
                }

                @Override
                public void onClickSeeMore() {
                    BigLocationActivity.startScreen((Activity) context, null, true);
                }
            });
            recyclerFavorPlace.setAdapter(subFavoriteDestinationAdapter);


            tvTitle.setText(homeServiceResponse.getData().get(position).getName());
            tvDescription.setText(homeServiceResponse.getData().get(position).getDescription());
        }
    }

    public class CreateTourViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private final TextView txtDescription;
        private final TextView btnStart;

        public CreateTourViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            btnStart = itemView.findViewById(R.id.btnStart);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Account account = MyApplication.getInstance().getAccount();
                    if (null != account && account.isLogin()) {
                        CreateTripActivity.startScreen(context);
                    } else {
                        LoginAndRegisterActivityNew.startScreen(context, 0, false);
                    }
                }
            });

        }

        public void bindItem(int position) {

            txtTitle.setText(homeServiceResponse.getData().get(position).getName());
            txtDescription.setText(homeServiceResponse.getData().get(position).getDescription());
        }
    }

    public class NearByViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recyclerNearPlace;
        private SubNearByAdapter subNearByAdapter;

        private final TextView tvTitle;
        private final TextView tvDescription;

        public NearByViewHolder(View itemView) {
            super(itemView);
            recyclerNearPlace = itemView.findViewById(R.id.recyclerNearPlace);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }

        public void bindItem(int position) {
            subNearByAdapter = new SubNearByAdapter(homeServiceResponse.getData().get(position).getItems(), context, new SubNearByAdapter.ClickItem() {
                @Override
                public void onClickItem(ItemHomeService.Item item, int i) {
//                    clickItemSmallLocation.onClickItemSmallLocation(item);
                    SmallLocationActivity.startScreen(context, SmallLocationActivity.OpenType.LIST, (ArrayList<ItemHomeService.Item>) homeServiceResponse.getData().get(position).getItems(), item.getCode(),i);
                }
            });
            recyclerNearPlace.setAdapter(subNearByAdapter);

            tvTitle.setText(homeServiceResponse.getData().get(position).getName());
            tvDescription.setText(homeServiceResponse.getData().get(position).getDescription());

        }
    }

    public class DiscoverViewHolder extends RecyclerView.ViewHolder implements NewHomeFragment.IOnClickTabReloadData {

        private TabDiscoverAdapter tabDiscoverAdapter;
        private final RecyclerView rclTab;
        private SubDiscoverAdapter subDiscoverAdapter;
        private final RecyclerView rclDiscover;
        private final LinearLayout btnReadMore;
        private final ShimmerFrameLayout mShimmerFrameLayout;
        private int mPosition;

        public DiscoverViewHolder(View itemView) {
            super(itemView);
            rclTab = itemView.findViewById(R.id.rclTab);
            rclDiscover = itemView.findViewById(R.id.rclDiscover);
            btnReadMore = itemView.findViewById(R.id.btnReadMore);
            mShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            mShimmerFrameLayout.setVisibility(View.VISIBLE);
            mShimmerFrameLayout.startShimmer();
            try {
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(rclDiscover);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void bindItem(int position) {
            mPosition = position;

            ItemAppDiscoverResponse itemAppDiscoverResponse = (ItemAppDiscoverResponse) homeServiceResponse.getData().get(position).getDataExtraSecond();
            if (itemAppDiscoverResponse == null) {
                List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
                loadData.onLoadDataFloorSecond(items.get(0).getContent_link(), TypeString.APP_DISCOVER, true);
            }

            subDiscoverAdapter = new SubDiscoverAdapter(context, itemAppDiscoverResponse.getItems());
            rclDiscover.setAdapter(subDiscoverAdapter);


            tabDiscoverAdapter = new TabDiscoverAdapter(homeServiceResponse.getData().get(position).getPositionClick(), homeServiceResponse.getData().get(position).getItems(), context, new TabDiscoverAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {
                    newHomeFragment.setmIOnClickTabReloadData(DiscoverViewHolder.this);
                    homeServiceResponse.getData().get(position).setPositionClick(positionClick);
                    List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
                    loadData.onLoadDataFloorSecond(items.get(positionClick).getContent_link(), TypeString.APP_DISCOVER, true);
                    rclDiscover.setVisibility(View.INVISIBLE);
                    mShimmerFrameLayout.setVisibility(View.VISIBLE);
                    mShimmerFrameLayout.startShimmer();

                }
            });
            rclTab.setAdapter(tabDiscoverAdapter);

            btnReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TravelNewsActivity.openScreen((MainActivity) context, true, TravelNewsActivity.OpenType.LIST);
                }
            });
            mShimmerFrameLayout.stopShimmer();
            rclDiscover.setVisibility(View.VISIBLE);
            mShimmerFrameLayout.setVisibility(View.GONE);
        }

        @Override
        public void onTabClick(String code) {
            try {
                if (code.equals(TypeString.APP_DISCOVER)) {
                    ItemAppDiscoverResponse itemAppDiscoverResponse = (ItemAppDiscoverResponse) homeServiceResponse.getData().get(mPosition).getDataExtraSecond();
                    if (itemAppDiscoverResponse == null) {
                        List<ItemHomeService.Item> items = homeServiceResponse.getData().get(mPosition).getItems();
                        loadData.onLoadDataFloorSecond(items.get(0).getContent_link(), TypeString.APP_DISCOVER, true);
                    }
                    subDiscoverAdapter = new SubDiscoverAdapter(context, itemAppDiscoverResponse.getItems());
                    rclDiscover.setAdapter(subDiscoverAdapter);
                    mShimmerFrameLayout.stopShimmer();
                    mShimmerFrameLayout.setVisibility(View.GONE);
                    rclDiscover.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recyclerAppVideo;
        private SubVideoAdapter subVideoAdapter;
        private final IndefinitePagerIndicator vpIndicator;
        private final TextView tvSeeMore;
        private final CircleIndicator2 indicator;

        public VideoViewHolder(View itemView) {
            super(itemView);
            recyclerAppVideo = itemView.findViewById(R.id.recyclerAppVideo);
            vpIndicator = itemView.findViewById(R.id.vpIndicator);
            tvSeeMore = itemView.findViewById(R.id.tvSeeMore);
            indicator = itemView.findViewById(R.id.indicator);

            try {
//                PagerSnapHelper  helper = new PagerSnapHelper ();
//                helper.attachToRecyclerView(recyclerAppVideo);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void bindItem(int position) {


            AppVideoResponse appVideoResponse = (AppVideoResponse) homeServiceResponse.getData().get(position).getDataExtra();
            if (appVideoResponse == null) {
                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_VIDEO);
            }
            subVideoAdapter = new SubVideoAdapter(context, appVideoResponse.getItems());
            recyclerAppVideo.setAdapter(subVideoAdapter);

            try {
                PagerSnapHelper helper = new PagerSnapHelper();
                helper.attachToRecyclerView(recyclerAppVideo);
                indicator.attachToRecyclerView(recyclerAppVideo, helper);
                vpIndicator.attachToRecyclerView(recyclerAppVideo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tvSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new OnClickVideoInMenu());
//                    HighLightSeeMoreVideoActivity.startScreen(context, "N???i b???t");
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        recyclerAppVideo.smoothScrollToPosition(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 500);

        }
    }

    public class VoucherNowViewHolder extends RecyclerView.ViewHolder implements NewHomeFragment.IOnClickTabReloadData {
        private final RecyclerView rclContent;
        private SubVoucherNowAdapter subVideoAdapter;

        private final RecyclerView rclTab;
        private TabVoucherNowAdapter tabVoucherNowAdapter;

        private final TextView tvTitle;
        private final TextView btnSeeMore;
        private int mPosition;
        private final ShimmerFrameLayout mShimmerFrameLayout;
        private String tabVoucherPosition = "";
        private ImageView imvNoVoucherData;

        public VoucherNowViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
            rclTab = itemView.findViewById(R.id.rclTab);
            mShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container);
            mShimmerFrameLayout.setVisibility(View.VISIBLE);
            mShimmerFrameLayout.startShimmer();
            imvNoVoucherData = itemView.findViewById(R.id.imv_no_voucher_now);
        }

        public void bindItem(int position) {
            mPosition = position;

            ItemAppVoucherNowResponse itemAppVoucherNowResponse = (ItemAppVoucherNowResponse) homeServiceResponse.getData().get(position).getDataExtraSecond();
            if (itemAppVoucherNowResponse == null) {
                List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
                loadData.onLoadDataFloorSecond(items.get(0).getContent_link(), TypeString.APP_VOUCHER_NOW, true);
            }

            subVideoAdapter = new SubVoucherNowAdapter(itemAppVoucherNowResponse, context);
            rclContent.setAdapter(subVideoAdapter);

            tabVoucherNowAdapter = new TabVoucherNowAdapter(homeServiceResponse.getData().get(position).getPositionClick(), homeServiceResponse.getData().get(position).getItems(), context, new TabVoucherNowAdapter.ClickTab() {
                @Override
                public void onClickTab(int positionClick) {
                    tabVoucherPosition = String.valueOf(positionClick);
                    homeServiceResponse.getData().get(position).setPositionClick(positionClick);
                    List<ItemHomeService.Item> items = homeServiceResponse.getData().get(position).getItems();
                    loadData.onLoadDataFloorSecond(items.get(positionClick).getContent_link(), TypeString.APP_VOUCHER_NOW, true);
                    mShimmerFrameLayout.setVisibility(View.VISIBLE);
                    mShimmerFrameLayout.startShimmer();
                    newHomeFragment.setmIOnClickTabReloadData(VoucherNowViewHolder.this);

                }
            });
            rclTab.setAdapter(tabVoucherNowAdapter);


            try {
                tvTitle.setText(homeServiceResponse.getData().get(position).getName());
                btnSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            TravelVoucherActivity.openScreenWithTabPosition(context, true, TravelVoucherActivity.OpenType.LIST, false, tabVoucherPosition);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            mShimmerFrameLayout.stopShimmer();
            mShimmerFrameLayout.setVisibility(View.GONE);
            rclContent.setVisibility(View.VISIBLE);
            List<View> viewList = new ArrayList<>();
            viewList.add(rclContent);
            setupNodataVoucher(itemAppVoucherNowResponse.getItems(),viewList,imvNoVoucherData);
        }

        @Override
        public void onTabClick(String code) {
            try {
                if (TypeString.APP_VOUCHER_NOW.equals(code)) {
                    ItemAppVoucherNowResponse itemAppVoucherNowResponse = (ItemAppVoucherNowResponse) homeServiceResponse.getData().get(mPosition).getDataExtraSecond();
                    if (itemAppVoucherNowResponse == null) {
                        List<ItemHomeService.Item> items = homeServiceResponse.getData().get(mPosition).getItems();
                        loadData.onLoadDataFloorSecond(items.get(0).getContent_link(), TypeString.APP_VOUCHER_NOW, true);
                    }
                    subVideoAdapter = new SubVoucherNowAdapter(itemAppVoucherNowResponse, context);
                    rclContent.setAdapter(subVideoAdapter);
                    mShimmerFrameLayout.stopShimmer();
                    mShimmerFrameLayout.setVisibility(View.GONE);
                    rclContent.setVisibility(View.VISIBLE);
                    List<View> viewList = new ArrayList<>();
                    viewList.add(rclContent);
                    setupNodataVoucher(itemAppVoucherNowResponse.getItems(),viewList,imvNoVoucherData);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class LiveTVViewHolder extends RecyclerView.ViewHolder implements SubLiveTvAdapter.ButtonClick, NewHomeFragment.PauseVideo {
        private SubLiveTvAdapter subLiveTvAdapter;
        private final RecyclerView recycleLiveTv;
        private final JWPlayerView jwplayer;
        private final TextView tvScheduleLiveTv;
        private LiveTvResponse liveTvResponse;
        private final ImageView imgFullScreen;
        private final TextView tvDescription;
        private final TextView tvTitle;

        private int currentPosition = 0;

        public LiveTVViewHolder(View itemView) {
            super(itemView);
            recycleLiveTv = itemView.findViewById(R.id.recycleLiveTv);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            jwplayer = itemView.findViewById(R.id.jwplayer);
            tvScheduleLiveTv = itemView.findViewById(R.id.tvScheduleLiveTv);
            imgFullScreen = itemView.findViewById(R.id.imgFullScreen);
        }

        public void bindItem(int position) {
            liveTvResponse = (LiveTvResponse) homeServiceResponse.getData().get(position).getDataExtra();
            if (liveTvResponse == null) {
                loadData.onLoadData(homeServiceResponse.getData().get(position).getContent_link(), TypeString.APP_TV);
            }
            subLiveTvAdapter = new SubLiveTvAdapter(liveTvResponse.getItems(), context, this);
            recycleLiveTv.setAdapter(subLiveTvAdapter);

            tvScheduleLiveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jwplayer.pause();
                    LiveTVActivity.openScreen(context, liveTvResponse, currentPosition);
                }
            });

            imgFullScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jwplayer.pause();
                    FullVideoActivity.openScreen(context, liveTvResponse.getItems().get(currentPosition).getStreaming_urls().get(0).getUrl());
                }
            });

            newHomeFragment.setPauseVideo(this);


            try {
                PlaylistItem pi = new PlaylistItem.Builder()
                        .file(liveTvResponse.getItems().get(0).getStreaming_urls().get(0).getUrl())
                        .build();
                jwplayer.load(pi);
            } catch (Exception e) {
                Log.e("loadLiveTV", e.getMessage() + "");
                e.printStackTrace();
            }


//            Observable.fromCallable(new Callable<PlaylistItem>() {
//                @Override
//                public PlaylistItem call() throws Exception {
//                    PlaylistItem pi = null;
//                    try {
//                        pi = new PlaylistItem.Builder()
//                                .file(liveTvResponse.getItems().get(0).getStreaming_urls().get(0).getUrl())
//                                .build();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return pi;
//                }
//            }).subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe(new Consumer<PlaylistItem>() {
//                        @Override
//                        public void accept(PlaylistItem playlistItem) throws Exception {
//                            jwplayer.load(playlistItem);
//                        }
//                    });


            try {
                tvDescription.setText(homeServiceResponse.getData().get(position).getDescription());
                tvTitle.setText(homeServiceResponse.getData().get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = context.getAssets().open("livetv.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        public void clickChannel(int position) {
            try {
                currentPosition = position;
                PlaylistItem pi = new PlaylistItem.Builder()
                        .file(liveTvResponse.getItems().get(position).getStreaming_urls().get(0).getUrl())
                        .build();
                jwplayer.load(pi);
                jwplayer.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void pauseVideoListener() {
            jwplayer.pause();
        }
    }

    public class RecentViewViewHolder extends RecyclerView.ViewHolder {
        private SubRecentViewAdapter subRecentViewAdapter;
        private final RecyclerView rclContent;
        private final LinearLayout layoutRoot;
        private TextView tvTitle;
        private int position;


        public RecentViewViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            layoutRoot = itemView.findViewById(R.id.layoutRoot);
            try {
                tvTitle = itemView.findViewById(R.id.tvTitle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(rclContent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void bindItem(int position) {
            this.position = position;
            ArrayList<DetailSmallLocationResponse> detailSmallLocationResponses;
            String json = PreferenceUtil.getInstance(context).getValue(Constants.PrefKey.RECENT_VIEW, "");
            if (json.isEmpty()) {
                detailSmallLocationResponses = new ArrayList<>();
            } else {
                detailSmallLocationResponses = new Gson().fromJson(json,
                        new TypeToken<ArrayList<DetailSmallLocationResponse>>() {
                        }.getType());
            }

            if (detailSmallLocationResponses != null && detailSmallLocationResponses.size() > 0) {
                Collections.reverse(detailSmallLocationResponses);
                layoutRoot.setVisibility(View.VISIBLE);
                subRecentViewAdapter = new SubRecentViewAdapter(context, detailSmallLocationResponses);
                rclContent.setAdapter(subRecentViewAdapter);
            } else {
                layoutRoot.setVisibility(View.GONE);
            }

            try {
                tvTitle.setText(homeServiceResponse.getData().get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class OtherViewHolder extends RecyclerView.ViewHolder {

        public OtherViewHolder(View itemView) {
            super(itemView);


        }

        public void bindItem(int position) {

        }
    }

    public interface LoadData {
        void onLoadData(String link, String code);

        void onLoadDataFloorSecond(String link, String code, boolean isSave);
    }

    public interface ClickUserView {
        void onClickUserView();
    }

    public interface ClickButtonRegisterNow {
        void onClickButtonRegisterNow();
    }

    public interface ClickItemSmallLocation {
        void onClickItemSmallLocation(ItemHomeService.Item item);
    }

    public interface ClickSearch {
        void onClickSearch();
    }

    private <T> void setupNodataVoucher(List<T> itemList,List<View> listViewGone ,ImageView imvNodata){
        try {
            if (itemList != null){
                if (itemList.size() == 0){
                    imvNodata.setVisibility(View.VISIBLE);
                    for (View view:listViewGone) {
                        view.setVisibility(View.GONE);
                    }
                } else {
                    for (View view:listViewGone) {
                        view.setVisibility(View.VISIBLE);
                    }
                    imvNodata.setVisibility(View.GONE);
                }
            } else {
                imvNodata.setVisibility(View.VISIBLE);
                for (View view:listViewGone) {
                    view.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
