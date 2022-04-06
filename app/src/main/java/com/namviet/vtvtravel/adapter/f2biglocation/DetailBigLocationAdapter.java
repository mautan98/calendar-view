package com.namviet.vtvtravel.adapter.f2biglocation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brucetoo.videoplayer.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.FooterBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.HeaderBigLocation2Adapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.HeaderBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.TravelTipBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.VideoBigLocationAdapter;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.HighLightSeeMoreVideoActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailBigLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_OVERVIEW = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_HEADER_2 = 4;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_APP_TRAVEL_TIP = 2;
    private static final int TYPE_APP_VIDEO_HIGH_LIGHT = 3;
    private Context context;
    private ClickItem clickItem;
    private List<GetReviewResponse.Data.Content> reviews;
    private BaseViewModel viewModel;


    public class TypeString {
        public static final String TYPE_APP_PLACE_HIGH_LIGHT = "APP_PLACE_HIGH_LIGHT";
        public static final String APP_WHERE_GO = "APP_WHERE_GO";
        public static final String APP_TOP_HOTEL = "APP_TOP_HOTEL";
        public static final String APP_WHERE_STAY = "APP_WHERE_STAY";
        public static final String APP_TOP_RESTAURANT = "APP_TOP_RESTAURANT";
        public static final String APP_WHAT_EAT = "APP_WHAT_EAT";
        public static final String APP_TOP_PLAY = "APP_TOP_PLAY";
        public static final String APP_WHAT_PLAY = "APP_WHAT_PLAY";
        public static final String TYPE_APP_TRAVEL_TIP = "APP_TRAVEL_TIP";
        public static final String TYPE_APP_VIDEO_HIGH_LIGHT = "APP_VIDEO_HIGH_LIGHT";
    }

    private List<BigLocationResponse.Data.Item> items;
    private BigLocationResponse.Data.Region region;

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        try {
            this.weatherResponse = weatherResponse;
            notifyItemChanged(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WeatherResponse weatherResponse;

    public DetailBigLocationAdapter(List<BigLocationResponse.Data.Item> items, BigLocationResponse.Data.Region region, Context context, ClickItem clickItem, BaseViewModel viewModel) {
        this.context = context;
        this.items = items;
        this.clickItem = clickItem;
        this.region = region;
        this.viewModel = viewModel;
    }


    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_OVERVIEW;
//        } else {
            switch (items.get(position).getCode()) {
                case TypeString.TYPE_APP_PLACE_HIGH_LIGHT:
                case TypeString.APP_TOP_PLAY:
                    return TYPE_HEADER;
                case TypeString.APP_TOP_HOTEL:
                case TypeString.APP_TOP_RESTAURANT:
                    return TYPE_HEADER_2;
                case TypeString.APP_WHERE_GO:
                case TypeString.APP_WHERE_STAY:
                case TypeString.APP_WHAT_EAT:
                case TypeString.APP_WHAT_PLAY:
                    return TYPE_FOOTER;
                case TypeString.TYPE_APP_TRAVEL_TIP:
                    return TYPE_APP_TRAVEL_TIP;
                case TypeString.TYPE_APP_VIDEO_HIGH_LIGHT:
                    return TYPE_APP_VIDEO_HIGH_LIGHT;
            }
//        }
        return TYPE_HEADER;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_OVERVIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_pg_introduct_location_header, parent, false);
            return new OverViewViewHolder(v);
        }
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_main, parent, false);
            return new HeaderViewHolder(v);
        }
        if (viewType == TYPE_HEADER_2) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_main, parent, false);
            return new HeaderViewHolder(v);
        }
        if (viewType == TYPE_FOOTER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_big_location_main_2, parent, false);
            return new FooterViewHolder(v);
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
                ((OverViewViewHolder) holder).bindItem(position);
            }
            if (getItemViewType(position) == TYPE_HEADER) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_HEADER_2) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_FOOTER) {
                ((FooterViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_APP_TRAVEL_TIP) {
                ((TravelTipViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_APP_VIDEO_HIGH_LIGHT) {
                ((VideoViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return items.size();
        } catch (Exception e) {
            return 0;
        }

    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private HeaderBigLocationAdapter headerBigLocationAdapter;
        private RecyclerView rclContent;
        private TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void bindItem(int position) {
            this.position = position;
            headerBigLocationAdapter = new HeaderBigLocationAdapter(items.get(position).getItems(), context, new HeaderBigLocationAdapter.ClickItem() {
                @Override
                public void onClickItem(Travel travel) {

                }

                @Override
                public void likeEvent(int i) {
                    try {
                        Travel travel = items.get(position).getItems().get(i);

                        viewModel.likeEvent(travel.getId(), travel.getContent_type());


                        try {
                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.BIG_LOCATION, TrackingAnalytic.ScreenTitle.BIG_LOCATION)
                                    .setContent_id(travel.getId())
                                    .setContent_type(travel.getContent_type())
                                    .setScreen_class(this.getClass().getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (travel.isLiked()) {
                            travel.setLiked(false);
                        } else {
                            travel.setLiked(true);
                        }
                        headerBigLocationAdapter.notifyItemChanged(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rclContent.setAdapter(headerBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());
        }
    }

    public class HeaderViewHolder2 extends RecyclerView.ViewHolder {
        private int position;
        private HeaderBigLocation2Adapter headerBigLocationAdapter;
        private RecyclerView rclContent;
        private TextView tvTitle;

        public HeaderViewHolder2(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void bindItem(int position) {
            this.position = position;
            headerBigLocationAdapter = new HeaderBigLocation2Adapter(items.get(position).getItems(), context, new HeaderBigLocation2Adapter.ClickItem() {
                @Override
                public void onClickItem(Travel travel) {

                }

                @Override
                public void likeEvent(int i) {
                    try {
                        Travel travel = items.get(position).getItems().get(i);

                        viewModel.likeEvent(travel.getId(), travel.getContent_type());


                        try {
                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.BIG_LOCATION, TrackingAnalytic.ScreenTitle.BIG_LOCATION)
                                    .setContent_id(travel.getId())
                                    .setContent_type(travel.getContent_type())
                                    .setScreen_class(this.getClass().getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (travel.isLiked()) {
                            travel.setLiked(false);
                        } else {
                            travel.setLiked(true);
                        }
                        headerBigLocationAdapter.notifyItemChanged(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rclContent.setAdapter(headerBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclContent;
        private TextView tvTitle;
        private LinearLayout btnReadMore;
        private FooterBigLocationAdapter headerBigLocationAdapter;


        public FooterViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnReadMore = itemView.findViewById(R.id.btnReadMore);

        }

        public void bindItem(int position) {
            this.position = position;
            headerBigLocationAdapter = new FooterBigLocationAdapter(items.get(position).getItems(), context, items.get(position).getCode_type(), new FooterBigLocationAdapter.ClickItem() {
                @Override
                public void onClickItem(Travel travel) {

                }

                @Override
                public void likeEvent(int i) {
                    try {
                        Travel travel = items.get(position).getItems().get(i);

                        viewModel.likeEvent(travel.getId(), travel.getContent_type());

                        if (travel.isLiked()) {
                            travel.setLiked(false);
                        } else {
                            travel.setLiked(true);
                        }
                        headerBigLocationAdapter.notifyItemChanged(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rclContent.setAdapter(headerBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());

            btnReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        switch (items.get(position).getCode_type()) {
                            case "HOTEL":
                            case "RESTAURANT":
                            case "CENTER":
                            case "PLACE":
                                int mPosition = 0;
                                for (int i = 0; i < region.getItems().size(); i++) {
                                    if(region.getItems().get(i).getCode().equals(items.get(position).getCode())){
                                        mPosition = i;
                                    }
                                }

                                String json = new Gson().toJson(region.getItems());
                                Type listType = new TypeToken<ArrayList<ItemHomeService.Item>>() {}.getType();
                                ArrayList<ItemHomeService.Item> yourList = new Gson().fromJson(json, listType);
                                SmallLocationActivity.startScreen(context, SmallLocationActivity.OpenType.LIST, yourList, items.get(position).getCode(),mPosition);

//                                SmallLocationActivity.startScreen(context, items.get(position).getLink(), "APP_WHERE_STAY", SmallLocationActivity.OpenType.LIST, region.getId());
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    public class TravelTipViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclContent;
        private TextView tvTitle;
        private TravelTipBigLocationAdapter travelTipBigLocationAdapter;

        public TravelTipViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void bindItem(int position) {
            this.position = position;
            travelTipBigLocationAdapter = new TravelTipBigLocationAdapter(items.get(position).getItems(), context, null);
            rclContent.setAdapter(travelTipBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());

        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private int position;
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

        public void bindItem(int position) {
            this.position = position;
            videoBigLocationAdapter = new VideoBigLocationAdapter(items.get(position).getItems(), context, null);
            rclContent.setAdapter(videoBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());
            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HighLightSeeMoreVideoActivity.startScreen(context, region.getName(), region.getId(), items.get(position).getLink());
                }
            });
        }
    }

    public class OverViewViewHolder extends RecyclerView.ViewHolder {
        private int position;
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

        public void bindItem(int position) {
            this.position = position;
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
            tvSmallIntro.setText("Giới thiệu " + region.getName());

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


    public interface ClickItem {
        void onClickItem(BigLocationResponse.Data.Item item);

    }


}
