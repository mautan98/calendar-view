package com.namviet.vtvtravel.adapter.f2share;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baseapp.activity.BaseActivity;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.f2.Contact;

import java.util.HashMap;
import java.util.List;

public class ContactShareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTACT = 2;
    private Context mContext;
    List<Contact> contacts;
    private ClickItem clickItem;
    private HashMap<Integer, Boolean> hashMapSelected = new HashMap<>();

    public ContactShareAdapter(Context mContext, List<Contact> contacts, ClickItem clickItem, HashMap<Integer, Boolean> hashMapSelected) {
        this.mContext = mContext;
        this.contacts = contacts;
        this.clickItem = clickItem;
        this.hashMapSelected = hashMapSelected;
    }

    public List<Contact> getData(){
        return contacts;
    }

    public void setData(List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (contacts.get(position).isHeader()){
            return TYPE_HEADER;
        }else {
        return TYPE_CONTACT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_contact_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_CONTACT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_contact_share, parent, false);
            return new ContactViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        if (getItemViewType(position) == TYPE_HEADER) {
            ((HeaderViewHolder) holder).bindItem(position);
        } else if (getItemViewType(position) == TYPE_CONTACT) {
            ((ContactViewHolder) holder).bindItem(position);
        }

    }


    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);

        }

        public void bindItem(int position) {
            tvHeader.setText(contacts.get(position).getContactName());

        }
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private TextView tvName;
        private TextView tvPhone;
        private ImageView btnUnSelected;
        private ImageView btnSelected;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnSelected = itemView.findViewById(R.id.btnSelect);
            btnUnSelected = itemView.findViewById(R.id.btnUnSelect);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Boolean isSelected = hashMapSelected.get(contacts.get(position).getContactClientId());
                    if(isSelected != null){
                        if(isSelected){
                            hashMapSelected.put(contacts.get(position).getContactClientId(), false);
                            btnSelected.setVisibility(View.GONE);
                            btnUnSelected.setVisibility(View.VISIBLE);
                            clickItem.onClickItem(contacts.get(position), false);
                        }else {
                            hashMapSelected.put(contacts.get(position).getContactClientId(), true);
                            btnSelected.setVisibility(View.VISIBLE);
                            btnUnSelected.setVisibility(View.GONE);
                            clickItem.onClickItem(contacts.get(position), true);
                        }
                    }else {
                        hashMapSelected.put(contacts.get(position).getContactClientId(), true);
                        btnSelected.setVisibility(View.VISIBLE);
                        btnUnSelected.setVisibility(View.GONE);
                        clickItem.onClickItem(contacts.get(position), true);
                    }
                }
            });


        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(contacts.get(position).getContactName());
            tvPhone.setText(contacts.get(position).getPhoneToShow());

            if(hashMapSelected.get(contacts.get(position).getContactClientId())!= null){
                boolean isSelected = hashMapSelected.get(contacts.get(position).getContactClientId());
                if(isSelected){
                    btnSelected.setVisibility(View.VISIBLE);
                    btnUnSelected.setVisibility(View.GONE);
                }else {
                    btnSelected.setVisibility(View.GONE);
                    btnUnSelected.setVisibility(View.VISIBLE);
                }
            }else {
                btnSelected.setVisibility(View.GONE);
                btnUnSelected.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface ClickItem{
        void onClickItem(Contact contact, boolean isAdd);
    }


}
