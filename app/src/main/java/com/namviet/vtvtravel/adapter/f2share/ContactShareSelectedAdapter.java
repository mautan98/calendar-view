package com.namviet.vtvtravel.adapter.f2share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.model.f2.Contact;

import java.util.List;

public class ContactShareSelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTACT = 2;
    private Context mContext;
    List<Contact> contacts;

    private ClickItem clickItem;
    private boolean isShowInvite = true;
    private ShowHideText showHideText;

    public ContactShareSelectedAdapter(Context mContext, List<Contact> contacts, ClickItem clickItem, ShowHideText showHideText) {
        this.mContext = mContext;
        this.contacts = contacts;
        this.clickItem = clickItem;
        this.showHideText = showHideText;
    }

    public List<Contact> getData() {
        return contacts;
    }

    public void setData(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (contacts.get(position).isHeader()) {
            return TYPE_HEADER;
        } else {
            return TYPE_CONTACT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_CONTACT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_contact_share_selected, parent, false);
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
        if (contacts == null) {
            showHideText.onShowHideText(true, 0);
            return 0;
        } else {
            if (contacts.size() == 0) {
                showHideText.onShowHideText(true, 0);
            } else {
                showHideText.onShowHideText(false, contacts.size());
            }
            return contacts.size();
        }
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

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickItem(contacts.get(position));
                    contacts.remove(position);
                    notifyDataSetChanged();
                }
            });


        }

        public void bindItem(int position) {
            this.position = position;
            tvName.setText(contacts.get(position).getContactName());
            tvPhone.setText(contacts.get(position).getPhoneToShow());

        }
    }

    public interface ShowHideText {
        void onShowHideText(boolean isShow, int count);
    }

    public interface ClickItem {
        void onClickItem(Contact contact);
    }


}
