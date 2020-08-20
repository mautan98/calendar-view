package com.namviet.vtvtravel.adapter.offline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2callnow.SearchNumberFragment;

import java.util.List;

public class AllContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTACT = 2;
    private Context mContext;
    List<Contact> contacts;
    private ClickInvite clickInvite;
    private ClickItem clickItem;
    private boolean isShowInvite = true;

    public AllContactAdapter(Context mContext, List<Contact> contacts, ClickInvite clickInvite, ClickItem clickItem, boolean isShowInvite) {
        this.mContext = mContext;
        this.contacts = contacts;
        this.clickInvite = clickInvite;
        this.clickItem = clickItem;
        this.isShowInvite = isShowInvite;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_contact_call_now, parent, false);
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
        private TextView btnInvite;
        private int position;
        private TextView tvName;
        private TextView tvPhone;

        public ContactViewHolder(View itemView) {
            super(itemView);
            btnInvite = itemView.findViewById(R.id.btnInvite);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInvite.onClickInvite(position, contacts.get(position).getPhones().get(0), contacts.get(position).getPhoneToShow());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(position, contacts.get(position).getPhones().get(0), contacts.get(position).getPhoneToShow());
                }
            });


        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(contacts.get(position).getContactName());
            tvPhone.setText(contacts.get(position).getPhoneToShow());

            if(isShowInvite){
                btnInvite.setVisibility(View.VISIBLE);

                if(contacts.get(position).isCallNow()){
                    btnInvite.setVisibility(View.GONE);
                }else {
                    btnInvite.setVisibility(View.VISIBLE);
                }
            }else {
                btnInvite.setVisibility(View.GONE);
            }


        }
    }

    public interface ClickInvite {
        void onClickInvite(int position, String phone, String phoneToShow);
    }

    public interface ClickItem{
        void onClickItem(int position, String phone, String phoneToShow);
    }


}
