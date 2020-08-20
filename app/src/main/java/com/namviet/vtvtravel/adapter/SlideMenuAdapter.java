package com.namviet.vtvtravel.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.ultils.FirebaseAnalytic;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ImagePartActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;

import java.util.ArrayList;
import java.util.List;


public class SlideMenuAdapter extends RecyclerView.Adapter<SlideMenuAdapter.HeaderHolder> {
    private Context mContext;
    private List<ItemMenu> menuList;
    private LayoutInflater mInflater;
    private MainActivity mainActivity;
    private boolean isChild;

    public SlideMenuAdapter(Context mContext) {
        this.mContext = mContext;
        this.menuList = new ArrayList<>();
        mainActivity = (MainActivity) mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public SlideMenuAdapter(Context mContext, List<ItemMenu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
        mainActivity = (MainActivity) mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public SlideMenuAdapter(Context mContext, List<ItemMenu> menuList, boolean isChild) {
        this.mContext = mContext;
        this.menuList = menuList;
        this.isChild = isChild;
        mainActivity = (MainActivity) mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public HeaderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu_home, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderHolder holder, int position) {
        holder.binItem(position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    class HeaderHolder extends RecyclerView.ViewHolder {
        private ImageView ivMenu;
        private TextView tvMenu;
        private RecyclerView rvChildMenu;
        private View vLine;

        public HeaderHolder(View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.ivMenu);
            tvMenu = itemView.findViewById(R.id.tvMenu);
            rvChildMenu = itemView.findViewById(R.id.rvChildMenu);
            vLine = itemView.findViewById(R.id.vLine);
            rvChildMenu.setLayoutManager(new LinearLayoutManager(mContext));
            rvChildMenu.setNestedScrollingEnabled(false);
        }

        public void binItem(int position) {
            final ItemMenu itemMenu = menuList.get(position);
            tvMenu.setText(itemMenu.getName());
//            if (null != itemMenu.getChildren() && itemMenu.getChildren().size() > 0) {
//                SlideMenuAdapter adapter = new SlideMenuAdapter(mContext, itemMenu.getChildren(), true);
//                rvChildMenu.setAdapter(adapter);
//                vLine.setVisibility(View.GONE);
//                tvMenu.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vt_arrow_right, 0);
//            } else {
//                vLine.setVisibility(View.VISIBLE);
//                tvMenu.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            }
            Glide.with(mContext)
                    .load(itemMenu.getIcon_url())
                    .into(ivMenu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (itemMenu.getCode()) {
                        case Constants.TypeLeftMenu.SETTING:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_SETTING);
                            mainActivity.switchFragment(SlideMenu.MenuType.SETTING_SCREEN);
                            break;
                        case Constants.TypeLeftMenu.SUPPORT:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_TD1039);
                            mainActivity.call(mContext.getResources().getString(R.string.calling_address));
                            break;
                        case Constants.TypeLeftMenu.PACKAGE_REG:
//                            FirebaseAnalytic.pushEvent(FirebaseAnalytic.dasd, mContext);
                            mainActivity.showMessage(mContext.getResources().getString(R.string.is_develop));
                            break;
                        case Constants.TypeLeftMenu.MOMENT:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_MOMENT);
                            mainActivity.tabSlideMenuClick(2);
                            break;
                        case Constants.TypeLeftMenu.SUGGESTION:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_SUGGEST);
                            mainActivity.tabSlideMenuClick(1);
                            break;
                        case Constants.TypeLeftMenu.VOUCHER:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_DEAL);
                            mainActivity.switchFragment(SlideMenu.MenuType.VOUCHER_SCREEN);
                            break;
                        case Constants.TypeLeftMenu.TRAVEL_NOTEBOOK:
//                            FirebaseAnalytic.pushEvent(FirebaseAnalytic.CLICK_MENU_NOTEBOOK, mContext);
                            break;
                        case Constants.TypeLeftMenu.GALLERY:
//                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_ALBUM);
//                            mainActivity.switchFragment(SlideMenu.MenuType.BEAUTIFUL_PHOTO_SCREEN);
                            ImagePartActivity.startScreen((Activity) mContext);
                            break;
                        case Constants.TypeLeftMenu.SEARCH:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_SEARCH);
                            mainActivity.switchFragment(SlideMenu.MenuType.SEARCH_SCREEN);
                            break;
                        case Constants.TypeLeftMenu.DETAIL_LIVE_CHANNEL:
//                            FirebaseAnalytic.pushEvent(FirebaseAnalytic.dasd, mContext);
                            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_CHANNEL_LIVE_SCREEN);
                            break;
                        case Constants.TypeLeftMenu.WHERE_GO_VIDEO:
                            mainActivity.tabSlideMenuClick(3);
                            break;
                        case Constants.TypeLeftMenu.WHAT_EAT_VIDEO:
                            mainActivity.tabSlideMenuClick(3);
                            break;
                        case Constants.TypeLeftMenu.WHAT_PLAY_VIDEO:
                            mainActivity.tabSlideMenuClick(3);
                            break;
                        case Constants.TypeLeftMenu.WHERE_STAY_VIDEO:
                            mainActivity.tabSlideMenuClick(3);
                            break;
                        case Constants.TypeLeftMenu.VIDEO:
                            mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_VIDEO);
                            mainActivity.tabSlideMenuClick(3);
                            break;
                        default:
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(Constants.IntentKey.KEY_ITEM_MENU, itemMenu);
                            switch (itemMenu.getName()) {
                                case "Đi đâu":
                                    mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_DEAL);
                                    break;
                                case "Ở đâu":
                                    mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_HOTEL);
                                    break;
                                case "Chơi gì":
                                    mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_EVENT);
                                    break;
                                case "Ăn gì":
                                    mainActivity.pushEvent(FirebaseAnalytic.CLICK_MENU_RESTAURANT);
                                    break;
                                default:
                                    break;


                            }

//                            mainActivity.setBundle(bundle);
//                            mainActivity.switchFragment(SlideMenu.MenuType.LIST_WHERE_GO_NEWS_SCREEN);

                            TravelNewsActivity.openScreen((MainActivity)mContext, false, TravelNewsActivity.OpenType.LIST);

                            break;
                    }
                }
            });
        }
    }


    public void updateMenuList(final List<ItemMenu> menuList) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMenuList(menuList);
            }
        });

    }

    public void setMenuList(List<ItemMenu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }
}