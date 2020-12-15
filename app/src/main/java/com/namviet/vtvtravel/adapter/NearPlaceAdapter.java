package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.holder.BaseHolder;
import com.namviet.vtvtravel.model.NearItem;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.List;

public class NearPlaceAdapter extends RecyclerView.Adapter<NearPlaceAdapter.NearPlaceHolder> {
    private Context mContext;
    private List<NearItem> listItem;

    public NearPlaceAdapter(Context mContext, List<NearItem> listItem) {
        this.mContext = mContext;
        this.listItem = listItem;
    }

    public NearPlaceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NearPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_near_place, parent, false);
        return new NearPlaceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NearPlaceHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return listItem == null ? 0 : listItem.size();
    }

    public class NearPlaceHolder extends BaseHolder {
        private ImageView ivIcon;
        private TextView tvName;

        public NearPlaceHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);

        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            final NearItem item = listItem.get(position);
            tvName.setText(item.getName() + " (" + item.getCount() + ")");
            setImageUrl(item.getIcon_url(), ivIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity mainActivity = (MainActivity) mContext;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, item);
                    mainActivity.setBundle(bundle);
                    mainActivity.switchFragment(SlideMenu.MenuType.NEAR_PLACE_SCREEN);
                }
            });
        }
    }
}
