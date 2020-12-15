package com.namviet.vtvtravel.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ItemNearTypeBinding;
import com.namviet.vtvtravel.listener.ItemChangeListener;
import com.namviet.vtvtravel.model.NearType;
import com.namviet.vtvtravel.viewmodel.ItemNearTypeViewModel;

import java.util.List;

public class NearTypeAdapter extends RecyclerView.Adapter<NearTypeAdapter.NearTypeHolder> {

    private Context mContext;
    private List<NearType> nearTypeList;
    private int index=0;
    private ItemChangeListener listener;

    public void setListener(ItemChangeListener listener) {
        this.listener = listener;
    }

    public NearTypeAdapter(Context mContext, List<NearType> nearTypeList) {
        this.mContext = mContext;
        this.nearTypeList = nearTypeList;
    }

    @NonNull
    @Override
    public NearTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNearTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_near_type,
                parent, false);
        return new NearTypeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NearTypeHolder holder, int position) {
        holder.bindNear(position);
    }

    @Override
    public int getItemCount() {
        return nearTypeList == null ? 0 : nearTypeList.size();
    }

    public class NearTypeHolder extends RecyclerView.ViewHolder {
        private ItemNearTypeBinding binding;

        public NearTypeHolder(ItemNearTypeBinding binding) {
            super(binding.itemView);
            this.binding = binding;

        }

        public void bindNear(final int position) {
            NearType nearType = nearTypeList.get(position);
            if (index == position) {
                nearType.setCheck(true);
            } else {
                nearType.setCheck(false);
            }
            binding.ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(index!=position){
                        index = position;
                        notifyDataSetChanged();
                        if(listener!=null){
                            listener.onChange();
                        }
                    }
                }
            });
            if (binding.getItemNearType() == null) {
                binding.setItemNearType(new ItemNearTypeViewModel(itemView.getContext(), nearType));
            } else {
                binding.getItemNearType().setNearType(nearType);
            }
//            this.binding.setVariable(BR.itemNearType, binding.getItemNearType());
        }
    }

    public int getIndexType() {
        return index;
    }
}
