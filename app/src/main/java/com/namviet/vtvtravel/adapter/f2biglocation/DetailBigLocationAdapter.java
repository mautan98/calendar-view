package com.namviet.vtvtravel.adapter.f2biglocation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.JsonSyntaxException;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.FooterBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.HeaderBigLocation2Adapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.HeaderBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.TravelTipBigLocationAdapter;
import com.namviet.vtvtravel.adapter.f2biglocation.sub.VideoBigLocationAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.view.f2.DetailVideoActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

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

    public DetailBigLocationAdapter(List<BigLocationResponse.Data.Item> items, BigLocationResponse.Data.Region region, Context context, ClickItem clickItem) {
        this.context = context;
        this.items = items;
        this.clickItem = clickItem;
        this.region = region;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_OVERVIEW;
        } else {
            switch (items.get(position - 1).getCode()) {
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
        }
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
            return new HeaderViewHolder2(v);
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
                ((HeaderViewHolder) holder).bindItem(position - 1);
            } else if (getItemViewType(position) == TYPE_HEADER_2) {
                ((HeaderViewHolder2) holder).bindItem(position - 1);
            } else if (getItemViewType(position) == TYPE_FOOTER) {
                ((FooterViewHolder) holder).bindItem(position - 1);
            } else if (getItemViewType(position) == TYPE_APP_TRAVEL_TIP) {
                ((TravelTipViewHolder) holder).bindItem(position - 1);
            } else if (getItemViewType(position) == TYPE_APP_VIDEO_HIGH_LIGHT) {
                ((VideoViewHolder) holder).bindItem(position - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return items.size() + 1;
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
            headerBigLocationAdapter = new HeaderBigLocationAdapter(items.get(position).getItems(), context, null);
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
            headerBigLocationAdapter = new HeaderBigLocation2Adapter(items.get(position).getItems(), context, null);
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
            headerBigLocationAdapter = new FooterBigLocationAdapter(items.get(position).getItems(), context, items.get(position).getCode(), null);
            rclContent.setAdapter(headerBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());

            btnReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        switch (items.get(position).getCode_type()){
                            case "HOTEL":
                                SmallLocationActivity.startScreen(context, items.get(position).getLink(), "APP_WHERE_STAY", SmallLocationActivity.OpenType.LIST);
                                break;
                            case "RESTAURANT":
                                SmallLocationActivity.startScreen(context, items.get(position).getLink(), "APP_WHAT_EAT", SmallLocationActivity.OpenType.LIST);
                                break;
                            case "CENTER":
                                SmallLocationActivity.startScreen(context, items.get(position).getLink(), "APP_WHAT_PLAY", SmallLocationActivity.OpenType.LIST);
                                break;
                            case "PLACE":
                                SmallLocationActivity.startScreen(context, items.get(position).getLink(), "APP_WHERE_GO", SmallLocationActivity.OpenType.LIST);
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

        public VideoViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);


        }

        public void bindItem(int position) {
            this.position = position;
            videoBigLocationAdapter = new VideoBigLocationAdapter(items.get(position).getItems(), context, null);
            rclContent.setAdapter(videoBigLocationAdapter);
            tvTitle.setText(items.get(position).getName());
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


        public OverViewViewHolder(View itemView) {
            super(itemView);
            tvRegionName = itemView.findViewById(R.id.tvRegionName);
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
            bigLocationTopTabAdapter = new BigLocationTopTabAdapter(region.getItems(), context, null);
            rclActivity.setAdapter(bigLocationTopTabAdapter);
            Glide.with(context).load(region.getBanner_url()).into(imgAvatar);
            webView.loadDataWithBaseURL("", region.getDescription(), "text/html", "UTF-8", null);

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

        }
    }


    public interface ClickItem {
        void onClickItem(BigLocationResponse.Data.Item item);

    }


}
