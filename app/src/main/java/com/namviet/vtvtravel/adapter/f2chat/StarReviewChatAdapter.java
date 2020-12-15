package com.namviet.vtvtravel.adapter.f2chat;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.chat.Star;

import java.util.List;

public class StarReviewChatAdapter extends RecyclerView.Adapter<StarReviewChatAdapter.ViewHolder> {
    private Context context;
    private List<Star> listStar;
    private ClickItem clickItem;

    public StarReviewChatAdapter(Context context, List<Star> listStar, ClickItem clickItem) {
        this.context = context;
        this.listStar = listStar;
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.f2_item_star, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return listStar == null ? 0 : listStar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgStar;
        public ViewHolder(View itemView) {
            super(itemView);
            imgStar = itemView.findViewById(R.id.imgStar);
        }

        public void onBind(int position){
            if (listStar.get(position).isSelected()){
                imgStar.setImageResource(R.drawable.f2_ic_red_star);
            }else {
                imgStar.setImageResource(R.drawable.f2_ic_gray_star);
            }
            imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.clickStar(position);
                }
            });
        }
    }
    public interface ClickItem{
        void clickStar(int position);
    }
}
