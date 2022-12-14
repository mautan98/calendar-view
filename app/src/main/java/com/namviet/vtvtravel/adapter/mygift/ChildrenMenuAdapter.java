package com.namviet.vtvtravel.adapter.mygift;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ChatActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;

import java.util.List;

public class ChildrenMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_MENU_HEADER = 0;
    private Context context;
    private ClickItem clickItem;

    private List<MenuItem> items;

    public ChildrenMenuAdapter(List<MenuItem> items, Context context, ClickItem clickItem) {
        this.context = context;
        this.clickItem = clickItem;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM_MENU_HEADER;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM_MENU_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_menu_children, parent, false);
            return new NormalViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_ITEM_MENU_HEADER) {
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
                    Account account = MyApplication.getInstance().getAccount();
                    switch (items.get(position).getCode_type()) {

                        case "APP_MAIN_VQMM_BOOKING":
                            if (null != account && account.isLogin()) {
                                VQMMWebviewActivity.startScreen(context, "");
                            } else {
                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
                            }
                            break;

                        case "APP_MAIN_VOUCHER_BOOKING":
                            if (null != account && account.isLogin()) {
                                clickItem.onClickItem();
                            } else {
                                LoginAndRegisterActivityNew.startScreen(context, 0, false);
                            }
                            break;

                        case "APP_MAIN_SUPPORT_BOOKING":
                            try {
                                call(context.getString(R.string.calling_address));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case "APP_MAIN_CHATBOT_BOOKING":
                            try {
                                ChatActivity.startScreen((MyGiftActivity) context);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                    }
                }
            });


        }

        public void call(String message) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder
                    .setMessage("" + message)
                    .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String phone = "1039";
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.dimiss, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        public void bindItem(int position) {
            this.position = position;
            Glide.with(context).load(items.get(position).getIcon_url()).into(imgAvatar);
            tvName.setText(items.get(position).getName());
        }
    }


    public interface ClickItem {
        void onClickItem();
    }


}
