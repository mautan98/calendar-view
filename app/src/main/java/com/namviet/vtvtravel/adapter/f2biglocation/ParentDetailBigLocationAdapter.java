package com.namviet.vtvtravel.adapter.f2biglocation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brucetoo.videoplayer.utils.Utils;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.FooterBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.HeaderBigLocation2Adapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.HeaderBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.TravelTipBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.VideoBigLocationAdapter;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.HighLightSeeMoreVideoActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParentDetailBigLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_OVERVIEW = -1;
    private static final int TYPE_WHERE = 0;
    private static final int TYPE_STAY = 1;
    private static final int TYPE_EAT = 2;
    private static final int TYPE_PLAY = 3;
    private static final int TYPE_APP_TRAVEL_TIP = 4;
    private static final int TYPE_APP_VIDEO_HIGH_LIGHT = 5;
    private Context context;
    private List<GetReviewResponse.Data.Content> reviews;
    private BaseViewModel viewModel;

    private List<BigLocationResponse.Data.Item> dataListWhere = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListStay = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListEat = new ArrayList<>();
    private List<BigLocationResponse.Data.Item> dataListPlay = new ArrayList<>();

    private BigLocationResponse.Data.Item travelTip;
    private BigLocationResponse.Data.Item video;

    private List<BigLocationResponse.Data.Item> items;
    private BigLocationResponse.Data.Region region;

    private CallRequest callRequest;

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        try {
            this.weatherResponse = weatherResponse;
            notifyItemChanged(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WeatherResponse weatherResponse;

    public ParentDetailBigLocationAdapter(List<BigLocationResponse.Data.Item> items
            , BigLocationResponse.Data.Region region
            , Context context
            , BaseViewModel viewModel
            , List<BigLocationResponse.Data.Item> dataListWhere
            , List<BigLocationResponse.Data.Item> dataListStay
            , List<BigLocationResponse.Data.Item> dataListEat
            , List<BigLocationResponse.Data.Item> dataListPlay
            , BigLocationResponse.Data.Item travelTip
            , BigLocationResponse.Data.Item video
            , CallRequest callRequest) {
        this.context = context;
        this.items = items;
        this.region = region;
        this.viewModel = viewModel;
        this.dataListEat = dataListEat;
        this.dataListPlay = dataListPlay;
        this.dataListStay = dataListStay;
        this.dataListWhere = dataListWhere;
        this.travelTip = travelTip;
        this.video = video;
        this.callRequest = callRequest;
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_OVERVIEW;
            case 1:
                return TYPE_WHERE;
            case 2:
                return TYPE_STAY;
            case 3:
                return TYPE_EAT;
            case 4:
                return TYPE_PLAY;
            case 5:
                return TYPE_APP_TRAVEL_TIP;
            case 6:
                return TYPE_APP_VIDEO_HIGH_LIGHT;


        }
        return TYPE_APP_VIDEO_HIGH_LIGHT;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_OVERVIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_pg_introduct_location_header, parent, false);
            return new OverViewViewHolder(v);
        }
        if (viewType == TYPE_WHERE || viewType == TYPE_STAY || viewType == TYPE_EAT || viewType == TYPE_PLAY) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_have_list, parent, false);
            return new DetailBigLocationViewHolder(v);
        }
        if (viewType == TYPE_APP_TRAVEL_TIP) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_big_location_travel_tip, parent, false);
            return new TravelTipViewHolder(v);
        }
        if (viewType == TYPE_APP_VIDEO_HIGH_LIGHT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_big_location_layout_app_video, parent, false);
            return new VideoViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_OVERVIEW) {
                ((OverViewViewHolder) holder).bindItem();
            } else if (getItemViewType(position) == TYPE_WHERE) {
                ((DetailBigLocationViewHolder) holder).bindItem(dataListWhere, position);
            } else if (getItemViewType(position) == TYPE_STAY) {
                ((DetailBigLocationViewHolder) holder).bindItem(dataListStay, position);
            } else if (getItemViewType(position) == TYPE_EAT) {
                ((DetailBigLocationViewHolder) holder).bindItem(dataListEat, position);
            } else if (getItemViewType(position) == TYPE_PLAY) {
                ((DetailBigLocationViewHolder) holder).bindItem(dataListPlay, position);
            } else if (getItemViewType(position) == TYPE_APP_TRAVEL_TIP) {
                ((TravelTipViewHolder) holder).bindItem();
            } else if (getItemViewType(position) == TYPE_APP_VIDEO_HIGH_LIGHT) {
                ((VideoViewHolder) holder).bindItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    public class TravelTipViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclContent;
        private TextView tvTitle;
        private TravelTipBigLocationAdapter travelTipBigLocationAdapter;
        private TextView btnSeeMore;

        public TravelTipViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
        }

        public void bindItem() {
            travelTipBigLocationAdapter = new TravelTipBigLocationAdapter(travelTip.getItems(), context, null);
            rclContent.setAdapter(travelTipBigLocationAdapter);
            tvTitle.setText(travelTip.getName());


            try {
                if (travelTip.getItems() == null) {
                    callRequest.onCallRequest(travelTip.getApi_items(), travelTip.getCode());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TravelNewsActivity.openScreen((Activity) context, true, TravelNewsActivity.OpenType.LIST);
                }
            });

        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclContent;
        private TextView tvTitle;
        private VideoBigLocationAdapter videoBigLocationAdapter;
        private TextView btnSeeMore;

        public VideoViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);


        }

        public void bindItem() {
            videoBigLocationAdapter = new VideoBigLocationAdapter(video.getItems(), context, null);
            rclContent.setAdapter(videoBigLocationAdapter);
            tvTitle.setText(video.getName());


            try {
                if (video.getItems() == null) {
                    callRequest.onCallRequest(video.getApi_items(), video.getCode());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HighLightSeeMoreVideoActivity.startScreen(context, region.getName(), region.getId(), video.getLink());
                }
            });
        }
    }

    public class OverViewViewHolder extends RecyclerView.ViewHolder {

        private BigLocationTopTabAdapter bigLocationTopTabAdapter;
        private RecyclerView rclActivity;
        private ImageView imgAvatar;
        private TextView btnShowMoreAndShowLess;
        private WebView webView;


        private TextView tvRegionName;
        private ImageView btnShare;
        private TextView tvSmallIntro;

        private ImageView imgWeather;
        private TextView tvWeather;


        public OverViewViewHolder(View itemView) {
            super(itemView);
            tvRegionName = itemView.findViewById(R.id.tvRegionName);
            imgWeather = itemView.findViewById(R.id.imgWeather);
            tvWeather = itemView.findViewById(R.id.tvWeather);
            btnShare = itemView.findViewById(R.id.btnShare);
            tvSmallIntro = itemView.findViewById(R.id.tvSmallIntro);
            rclActivity = itemView.findViewById(R.id.rclActivity);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnShowMoreAndShowLess = itemView.findViewById(R.id.btnShowMoreAndShowLess);
            webView = itemView.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
        }

        public void bindItem() {
            bigLocationTopTabAdapter = new BigLocationTopTabAdapter(region.getItems(), context, region.getId(), null);
            rclActivity.setAdapter(bigLocationTopTabAdapter);
            Glide.with(context).load(region.getBanner_url()).into(imgAvatar);
            webView.loadDataWithBaseURL("", region.getDescription(), "text/html", "UTF-8", null);

            if (region.isShow()) {
                ViewGroup.LayoutParams params = webView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                webView.setLayoutParams(params);
                btnShowMoreAndShowLess.setText("Ẩn bớt");
            } else {
                ViewGroup.LayoutParams params = webView.getLayoutParams();
                params.height = Utils.dp2px(context, 120);
                webView.setLayoutParams(params);
                btnShowMoreAndShowLess.setText("Xem thêm");
            }

            btnShowMoreAndShowLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (region.isShow()) {
                        ViewGroup.LayoutParams params = webView.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        webView.setLayoutParams(params);
                        region.setShow(false);
                        btnShowMoreAndShowLess.setText("Ẩn bớt");
                    } else {
                        ViewGroup.LayoutParams params = webView.getLayoutParams();
                        params.height = Utils.dp2px(context, 120);
                        webView.setLayoutParams(params);
                        region.setShow(true);
                        btnShowMoreAndShowLess.setText("Xem thêm");
                    }

                }
            });
            tvRegionName.setText(region.getName());

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            });

            try {
                tvWeather.setText(weatherResponse.getData().get(0).getWeather().getDescription() + ", " + Math.round(weatherResponse.getData().get(0).getTamp()) + "°C");
                Glide.with(context).load(weatherResponse.getData().get(0).getWeather().getIcon_url()).placeholder(R.drawable.f2_ic_weather).error(R.drawable.f2_ic_weather).into(imgWeather);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public class DetailBigLocationViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclContent;
        private ImageView imgBackground;
        private DetailBigLocationAdapter detailBigLocationAdapter;
        private int position;
        private RelativeLayout layoutProgressBar;

        public DetailBigLocationViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            layoutProgressBar = itemView.findViewById(R.id.layoutProgressBar);
        }

        public void bindItem(List<BigLocationResponse.Data.Item> dataList, int position) {
            this.position = position;
            detailBigLocationAdapter = new DetailBigLocationAdapter(dataList, region, context, null, viewModel);
            rclContent.setAdapter(detailBigLocationAdapter);

            Glide.with(context).load(dataList.get(0).getBanner_url()).into(imgBackground);


            try {
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getItems() == null) {
                        dataList.get(i).getApi_items();
                        callRequest.onCallRequest(dataList.get(i).getApi_items(), dataList.get(i).getCode());
                    }else {
                        layoutProgressBar.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    public interface ClickItem {
        void onClickItem(BigLocationResponse.Data.Item item);

    }

    public interface CallRequest {
        void onCallRequest(String link, String code);
    }


}
