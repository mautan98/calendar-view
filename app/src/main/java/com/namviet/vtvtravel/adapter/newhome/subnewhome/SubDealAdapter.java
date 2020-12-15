package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.response.newhome.AppDealResponse;

import java.text.DecimalFormat;
import java.util.List;

public class SubDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<AppDealResponse.Data> itemList;
    private ClickItem clickItem;

    public SubDealAdapter(Context context, List<AppDealResponse.Data> itemList, ClickItem clickItem) {
        this.context = context;
        this.itemList = itemList;
        this.clickItem = clickItem;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.f2_item_deal_hot, parent, false);
            return new HeaderViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvOriginPrice;
        private TextView tvDisplayPrice;
        private TextView tvUserTotal;
        private TextView tvDiscount;
        private TextView tvDayLeft;
        private ProgressBar progressBar;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            progressBar = itemView.findViewById(R.id.progress1);
            tvName = itemView.findViewById(R.id.tvName);
            tvOriginPrice = itemView.findViewById(R.id.tvOriginPrice);
            tvDisplayPrice = itemView.findViewById(R.id.tvDisplayPrice);
            tvUserTotal = itemView.findViewById(R.id.tvUserTotal);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvDayLeft = itemView.findViewById(R.id.tvDayLeft);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(itemList.get(position));
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            Glide.with(context).load(itemList.get(position).getAvatarUri()).into(imgAvatar);


            try {
                tvDisplayPrice.setText(convertPrice(itemList.get(position).getDisplayPrice()) + " đ");
            } catch (Exception e) {
                try {
                    tvDisplayPrice.setText(itemList.get(position).getDisplayPrice() + " đ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }


            try {
                tvOriginPrice.setText(convertPrice(itemList.get(position).getOriginPrice()) + " đ");
            } catch (Exception e) {
                try {
                    tvOriginPrice.setText(itemList.get(position).getOriginPrice() + " đ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }


            tvOriginPrice.setPaintFlags(tvOriginPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);




            tvName.setText(itemList.get(position).getName());
            tvUserTotal.setText(itemList.get(position).getUserTotal() + " người đang săn deal");
            tvDiscount.setText("-"+itemList.get(position).getDiscount() + "%");
            tvDayLeft.setText(getDayLeft(Long.parseLong(itemList.get(position).getEndAt())));
            progressBar.setProgress(getPercentProgress(Long.parseLong(itemList.get(position).getBeginAt()), Long.parseLong(itemList.get(position).getEndAt())));
        }

        @NonNull
        private String getDayLeft(long timeStamp){
            long myCurrentTimeMillis = System.currentTimeMillis();
            if(myCurrentTimeMillis > timeStamp){
                return "0 ngày";
            }else {
                long distance = (timeStamp - myCurrentTimeMillis)/1000;

                String days = (int)(distance / 86400) + " ngày ";
                String hours = String.valueOf((int)((distance % 86400) / 3600));
                String minutes = String.valueOf((int)((distance % 3600) / 60));
                String seconds = String.valueOf((int)((distance % 3600) % 60));

                return "Còn lại " + days + hours+":"+minutes+":"+seconds;

            }
        }

        private int getPercentProgress(long startTime, long endTime){
            try {
                long myCurrentTimeMillis = System.currentTimeMillis();
                if(myCurrentTimeMillis < startTime){
                    return 0;
                }
                if(myCurrentTimeMillis > endTime){
                    return 100;
                }else {
//                    return (int)(((double)(endTime - myCurrentTimeMillis)/(double)(endTime - startTime)) * 100);
                    return (int)(((double)(myCurrentTimeMillis - startTime)/(double)(endTime - startTime)) * 100);
                }
            } catch (Exception e) {
                return 50;
            }
        }
    }

    public interface ClickItem{
        void onClickItem(AppDealResponse.Data  data);
    }

    public static String convertPrice(String string) {
        try {
            DecimalFormat df = new DecimalFormat("###,###,###");
            return df.format(Double.parseDouble(string));
        } catch (Exception e) {
            return string;
        }
    }
}
