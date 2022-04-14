package com.namviet.vtvtravel.adapter.f2menu;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f2.MyTripActivity;
import com.namviet.vtvtravel.view.f2.SystemInboxActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.SettingActivity;
import com.namviet.vtvtravel.view.f2.landingpage.LandingPageActivity;
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity;
import com.namviet.vtvtravel.view.f3.deal.view.mygift.NewMyGiftActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;

import java.util.ArrayList;
import java.util.List;

public class SubMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_MENU_HEADER = 0;
    private static final int TYPE_ITEM_MENU_NORMAL = 1;
    private Context context;
    private ClickItem clickItem;

    private List<MenuItem> items;

    public SubMenuAdapter(List<MenuItem> items, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getCode().toUpperCase().contains("HEADER")) {
            return TYPE_ITEM_MENU_HEADER;
        } else if (items.get(position).getCode().toUpperCase().contains("FOOTER")) {
            return TYPE_ITEM_MENU_HEADER;
        } else {
            return TYPE_ITEM_MENU_NORMAL;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM_MENU_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_menu_header, parent, false);
            return new HeaderViewHolder(v);
        }
        if (viewType == TYPE_ITEM_MENU_NORMAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_menu_normal, parent, false);
            return new NormalViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM_MENU_HEADER) {
                ((HeaderViewHolder) holder).bindItem(position);
            }
            if (getItemViewType(position) == TYPE_ITEM_MENU_NORMAL) {
                ((NormalViewHolder) holder).bindItem(position);
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
        private LinearLayout rootLayout;
        private ImageView imgAvatar;
        private TextView tvName;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("xxx", "onClick: " +items.get(position).getCode_type());
                    switch (items.get(position).getCode_type()) {
                        case "APP_MAIN_HEADER_MY_GIFT":
                            try {
                                TrackingAnalytic.postEvent(TrackingAnalytic.CLICK_MY_PROMOTION, TrackingAnalytic.getDefault("Menu", "Menu"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        case "APP_MAIN_FOOTER_SUPPORT":
//                            MyGiftActivity.startScreen(context, (ArrayList<MenuItem>) items.get(position).getMenuChildren(), items.get(position).getName());
                            NewMyGiftActivity.startScreen(context);
                            break;
                        case "APP_MAIN_FOOTER_SETTING":
                            SettingActivity.startScreen(context);
                            break;
                        case "APP_MAIN_HEADER_PACKAGE":
//                            Account account = MyApplication.getInstance().getAccount();
//                            if (null != account && account.isLogin()) {
//                                WebviewActivity.startScreen(context);
//                            } else {
//                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
//                            }

                            try {
                                OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
                                oneButtonTitleImageDialog.show(((MainActivity)context).getSupportFragmentManager(), Constants.TAG_DIALOG);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case "APP_MAIN_FOOTER_NOTIFICATION":
                            Account account1 = MyApplication.getInstance().getAccount();
                            if (null != account1 && account1.isLogin()) {
                                SystemInboxActivity.startScreen(context);
                            } else {
                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
                            }

                            break;

                        case "APP_MAIN_HEADER_MY_TRIP":
                            Account account2 = MyApplication.getInstance().getAccount();
                            if (null != account2 && account2.isLogin()) {
                                MyTripActivity.startScreen(context);
                            } else {
                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
                            }

                            break;

                        case "APP_MAIN_HEADER_VIRTUAL_CALL":
                            Account account3 = MyApplication.getInstance().getAccount();
                            if (null != account3 && account3.isLogin()
                                    && "APP_MAIN_HEADER_VIRTUAL_CALL".equals(items.get(position).getCode_type())) {
                                    if(account3.isTravelingSupporter()){
                                        VirtualSwitchBoardActivity.Companion.openActivity(context, VirtualSwitchBoardActivity.Companion.getTRAVEL_TYPE());
                                    }else {
                                        Toast.makeText(context, "Tính năng chỉ dành cho sở du lịch", Toast.LENGTH_SHORT).show();
                                    }

                            } else {
                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
                            }

                            break;

                        case "APP_MAIN_HEADER_TDDL":
                            Account accountTDDL = MyApplication.getInstance().getAccount();
                            if (null != accountTDDL && accountTDDL.isLogin()) {
                                if(accountTDDL.isTravelingSupporter()){
                                    if(accountTDDL.getTravelSupporterType() ==null || accountTDDL.getTravelSupporterType() != null &&(accountTDDL.getTravelSupporterType().equals("") || accountTDDL.getTravelSupporterType().equals("travel"))) {
                                        VirtualSwitchBoardActivity.Companion.openActivity(context, VirtualSwitchBoardActivity.Companion.getTRAVEL_TYPE());
                                    }else {
                                        LandingPageActivity.startScreen(context);
                                    }
                                }else {
                                    LandingPageActivity.startScreen(context);
                                }
                            } else {
                                LandingPageActivity.startScreen(context);
                            }
                            break;

                        case "APP_MAIN_HEADER_CSKH_BOOKING":
                            Account accountBooking = MyApplication.getInstance().getAccount();
                            if (null != accountBooking && accountBooking.isLogin()) {
                                if(accountBooking.isTravelingSupporter()){
                                    if(accountBooking.getTravelSupporterType() != null && accountBooking.getTravelSupporterType().equals("booking")) {
                                        VirtualSwitchBoardActivity.Companion.openActivity(context, VirtualSwitchBoardActivity.Companion.getBOOKING_TYPE());
                                    }else {
                                        Toast.makeText(context, "Tính năng chỉ dành cho CSKH Booking", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(context, "Tính năng chỉ dành cho CSKH Booking", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Tính năng chỉ dành cho CSKH Booking", Toast.LENGTH_SHORT).show();
                            }
                            break;

                    }
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(items.get(position).getName());
            if (!"APP_MAIN_HEADER_VIRTUAL_CALL".equals(items.get(position).getCode_type())) {
                Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            } else {
                Glide.with(context).load(R.drawable.f2_ic_virtual_call).into(imgAvatar);
            }


//            Account accountBooking = MyApplication.getInstance().getAccount();
//            if (null != accountBooking && accountBooking.isLogin()) {
//                if(accountBooking.isBookingSupporter()){
//                    if ("APP_MAIN_HEADER_CSKH_BOOKING".equals(items.get(position).getCode_type())) {
//                        setWrapContentView(rootLayout);
//                    } else {
//                        set1pxView(rootLayout);
//                    }
//                }else {
//                    set1pxView(rootLayout);
//                }
//            } else {
//                set1pxView(rootLayout);
//            }



        }
    }

    private void setWrapContentView(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
    }

    private void set1pxView(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 1;
        view.setLayoutParams(layoutParams);
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private int position;

        public NormalViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(items.get(position));
                }
            });
        }

        public void bindItem(int position) {
            this.position = position;
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            tvName.setText(items.get(position).getName());
        }
    }


    public interface ClickItem {
        void onClickItem(MenuItem menuItem);
    }


}
