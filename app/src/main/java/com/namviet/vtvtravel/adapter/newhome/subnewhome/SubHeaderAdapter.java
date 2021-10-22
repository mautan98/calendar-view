package com.namviet.vtvtravel.adapter.newhome.subnewhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.baseapp.activity.BaseActivity;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnClickBookingTopMenu;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.CreateTripActivity;
import com.namviet.vtvtravel.view.f2.LiveTVActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SubHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<ItemHomeService.Item> items = new ArrayList<>();
    private Context context;
    private MainActivity mActivity;
    private String mUrlDeal;


    public SubHeaderAdapter(List<ItemHomeService.Item> items, String urlDeal, Context context) {
        this.items = items;
        this.context = context;
        this.mUrlDeal = urlDeal;
        mActivity = (MainActivity) context;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_home_header_2, parent, false);
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
        try {
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    try {
//                        OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
//                        oneButtonTitleImageDialog.show(((MainActivity) context).getSupportFragmentManager(), Constants.TAG_DIALOG);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    try {
                     //   ComingSoonActivity.Companion.openActivity(context, items.get(getAdapterPosition()).getName(),mUrlDeal);
                        String code = items.get(getAdapterPosition()).getCode();
                        String link = items.get(getAdapterPosition()).getLink();
                        switch (code){
                            case "BOOKING":
                            //    MyFragment.openFragment(context,  R.id.frHome, BookingFragment.class, null, false);
                                EventBus.getDefault().post(new OnClickBookingTopMenu());
                                break;
                            case "CTKM":
//                                try {
//                                    DetailDealWebviewActivity.startScreen(context,link);
//                                } catch ( java.lang.Exception e) {
//                                e.printStackTrace();
//                            }
//                                break;
                            case "VQMM":
//                                try {
//                                    Account account = MyApplication.getInstance().getAccount();
//                                    if (null != account && account.isLogin()) {
//                                        VQMMWebviewActivity.startScreen(context, "");
//                                    } else {
//                                        LoginAndRegisterActivityNew.startScreen(context, 0, false);
//                                    }
//                                } catch ( Exception e) {
//                            }

                                try {
                                    OneButtonTitleImageDialog oneButtonTitleImageDialog = new OneButtonTitleImageDialog();
                                    oneButtonTitleImageDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "LIVETV":
                                LiveTVActivity.openScreen(context, 0,items.get(getAdapterPosition()).getLink() );
                              //  LiveTVActivity.openScreen(context, liveTvResponse, currentPosition);
                                break;
                            case "TOUR":
                                Account account = MyApplication.getInstance().getAccount();
                                if (null != account && account.isLogin()) {
                                    CreateTripActivity.startScreen(context);
                                } else {
                                    LoginAndRegisterActivityNew.startScreen(context, 0, false);
                                }
                                break;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void addFragment(Fragment fragment) {
            try {
                BaseActivity mActivity = (BaseActivity) context;
//                try {
//                    if(mActivity instanceof  LoginAndRegisterActivityNew){
//                        ((LoginAndRegisterActivityNew) mActivity).hideWarning();
//                        KeyboardUtils.hideKeyboard(mActivity, imgAvatar);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                FragmentManager frm = mActivity.getSupportFragmentManager();
                frm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .add(R.id.frame, fragment)
                        .addToBackStack(fragment.getClass().getSimpleName()).commit();
            } catch (Exception e) {
            }
        }
        public void bindItem(int position) {
            try {
                tvTitle.setText(items.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
