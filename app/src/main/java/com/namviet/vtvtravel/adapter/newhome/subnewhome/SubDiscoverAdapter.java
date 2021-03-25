package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.ItemAppDiscoverResponse;
import com.namviet.vtvtravel.ultils.TextJustification;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;

import java.util.List;

public class SubDiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_SECOND = 1;

    private Context context;
    private List<ItemAppDiscoverResponse.Item> itemList;

    public SubDiscoverAdapter(Context context, List<ItemAppDiscoverResponse.Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_ITEM;else return TYPE_ITEM_SECOND;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_discover, parent, false);
            return new HeaderViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_discover, parent, false);
            return new SecondViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_ITEM_SECOND) {
                ((SecondViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
//        return itemList != null ? itemList.size() : 0;
        return 2;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1;
        private TextView tv1;
        private ImageView img3;
        private TextView tv3;
        private ImageView img2;
        private TextView tv2;
        private ImageView img4;
        private TextView tv4;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            tv1 = itemView.findViewById(R.id.tv1);
            img3 = itemView.findViewById(R.id.img3);
            tv3 = itemView.findViewById(R.id.tv3);
            img2 = itemView.findViewById(R.id.img2);
            tv2 = itemView.findViewById(R.id.tv2);
            img4 = itemView.findViewById(R.id.img4);
            tv4 = itemView.findViewById(R.id.tv4);
        }

        public void bindItem(int position) {

            try {
                tv1.setText(itemList.get(0).getName());
                setImage(itemList.get(0).getLogo_url(), img1);
                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(0).getDetail_linkV2() != null && !itemList.get(0).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(0).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(0).getDetail_link());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tv2.setText(itemList.get(1).getName());
                setImage(itemList.get(1).getLogo_url(), img2);
                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(1).getDetail_linkV2() != null && !itemList.get(1).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(1).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(1).getDetail_link());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                tv3.setText(itemList.get(2).getName());
                setImage(itemList.get(2).getLogo_url(), img3);
                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(2).getDetail_linkV2() != null && !itemList.get(2).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(2).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(2).getDetail_link());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tv4.setText(itemList.get(3).getName());
                setImage(itemList.get(3).getLogo_url(), img4);
                img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(3).getDetail_linkV2() != null && !itemList.get(3).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(3).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(3).getDetail_link());
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

        public void setImage(String url, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).thumbnail(0.2f).into(image);
        }
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1;
        private TextView tv1;
        private ImageView img3;
        private TextView tv3;
        private ImageView img2;
        private TextView tv2;
        private ImageView img4;
        private TextView tv4;

        public SecondViewHolder(View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            tv1 = itemView.findViewById(R.id.tv1);
            img3 = itemView.findViewById(R.id.img3);
            tv3 = itemView.findViewById(R.id.tv3);
            img2 = itemView.findViewById(R.id.img2);
            tv2 = itemView.findViewById(R.id.tv2);
            img4 = itemView.findViewById(R.id.img4);
            tv4 = itemView.findViewById(R.id.tv4);
        }

        public void bindItem(int position) {

            try {
                tv1.setText(itemList.get(4).getName());
                setImage(itemList.get(4).getLogo_url(), img1);
                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(4).getDetail_linkV2() != null && !itemList.get(4).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(4).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(4).getDetail_link());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tv2.setText(itemList.get(5).getName());
                setImage(itemList.get(5).getLogo_url(), img2);
                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(5).getDetail_linkV2() != null && !itemList.get(5).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(5).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(5).getDetail_link());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                tv3.setText(itemList.get(6).getName());
                setImage(itemList.get(6).getLogo_url(), img3);
                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(6).getDetail_linkV2() != null && !itemList.get(6).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(6).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(6).getDetail_link());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tv4.setText(itemList.get(7).getName());
                setImage(itemList.get(7).getLogo_url(), img4);
                img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (itemList.get(7).getDetail_linkV2() != null && !itemList.get(7).getDetail_linkV2().isEmpty()) {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(7).getDetail_linkV2());
                            } else {
                                TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, itemList.get(7).getDetail_link());
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

        public void setImage(String url, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).thumbnail(0.2f).into(image);
        }
    }


}
