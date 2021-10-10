package com.namviet.vtvtravel.adapter.f2menu;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnClickMomentInMenu;
import com.namviet.vtvtravel.model.f2event.OnClickVideoInMenu;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ImagePartActivity;
import com.namviet.vtvtravel.view.f2.LiveTVActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealHomeActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_MENU_NORMAL = 1;
    private static final int TYPE_ITEM_MENU_FOOTER = 2;
    private Context context;
    private ClickItem clickItem;

    private List<MenuItem> itemsNormal;
    private List<MenuItem> itemsFooter;

    public MenuAdapter(List<MenuItem> itemsNormal, List<MenuItem> itemsFooter, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.itemsNormal = itemsNormal;
        this.itemsFooter = itemsFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_MENU_NORMAL;
        } else {
            return TYPE_ITEM_MENU_FOOTER;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM_MENU_FOOTER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_menu_footer, parent, false);
            return new FooterViewHolder(v);
        }
        if (viewType == TYPE_ITEM_MENU_NORMAL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_layout_menu_normal, parent, false);
            return new NormalViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM_MENU_FOOTER) {
                ((FooterViewHolder) holder).bindItem(position);
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
            return 2;
        } catch (Exception e) {
            return 0;
        }
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private RecyclerView rclContent;
        private SubMenuAdapter subMenuAdapter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
        }

        public void bindItem(int position) {
            this.position = position;
            subMenuAdapter = new SubMenuAdapter(itemsFooter, context, new SubMenuAdapter.ClickItem() {
                @Override
                public void onClickItem(MenuItem itemMenu) {

                }
            });
            rclContent.setAdapter(subMenuAdapter);
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rclContent;
        private SubMenuAdapter subMenuAdapter;
        private int position;

        public NormalViewHolder(View itemView) {
            super(itemView);
            rclContent = itemView.findViewById(R.id.rclContent);
        }

        public void bindItem(int position) {
            this.position = position;
            subMenuAdapter = new SubMenuAdapter(itemsNormal, context, new SubMenuAdapter.ClickItem() {
                @Override
                public void onClickItem(MenuItem itemMenu) {
                    switch (itemMenu.getCode_type()) {
                        case "APP_MAIN_TRAVEL_NEWS":
                            TravelNewsActivity.openScreen((MainActivity) context, true, TravelNewsActivity.OpenType.LIST);
                            break;
                        case "APP_MAIN_IMAGES":
                            ImagePartActivity.startScreen((Activity) context);
                            break;
                        case "APP_MAIN_TOURIST_HANDBOOK":
                            TravelNewsActivity.openScreen((MainActivity) context, false, TravelNewsActivity.OpenType.LIST);
                            break;
                        case "APP_MAIN_VIDEO":
                            EventBus.getDefault().post(new OnClickVideoInMenu());
                            break;
                        case "APP_MAIN_MOMENT":
                            EventBus.getDefault().post(new OnClickMomentInMenu());
                            break;
                        case "APP_MAIN_SERVICE_PACK":
                            Account account = MyApplication.getInstance().getAccount();
                            if (null != account && account.isLogin()) {
                                ServiceActivity.startScreen(context);
                            } else {
                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
                            }
                            try {
                                TrackingAnalytic.postEvent(TrackingAnalytic.CLICK_PACKAGE_REG, TrackingAnalytic.getDefault("Menu", "Menu"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        case "APP_MAIN_PROMOTION":
                            TravelVoucherActivity.openScreen(context, true, TravelVoucherActivity.OpenType.LIST, false);
//                            Account account2 = MyApplication.getInstance().getAccount();
//                            if (null != account2 && account2.isLogin()) {
//                                TravelVoucherActivity.openScreen(context, true, TravelVoucherActivity.OpenType.LIST);
//                            } else {
//                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
//                            }

                            break;
                        case "APP_MAIN_LIVETV":
//                            LiveTVActivityOld.startScreen(context);
                            LiveTVActivity.openScreen(context, 0, itemMenu.getLink());
                            break;
                        case "APP_MAIN_DEAL":
//                            try {
//                                DetailDealWebviewActivity.startScreen(context, itemMenu.getLink());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                TrackingAnalytic.postEvent(TrackingAnalytic.CLICK_DEAL_HUNT, TrackingAnalytic.getDefault("Menu", "Menu"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

                            DealHomeActivity.Companion.startScreen(context);

                            break;

                    }
                }
            });
            rclContent.setAdapter(subMenuAdapter);

        }
    }


    public interface ClickItem {
        void onClickItem(MenuItem menuItem);
    }


}
