package com.namviet.vtvtravel.adapter.f2callnow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.model.f2callnow.CallNowHistory;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2callnow.SearchNumberFragment;

import java.util.List;

public class MissingCallHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CALL_HISTORY = 1;
    private static final int TYPE_MISSING_CALL = 2;
    private Context mContext;
    List<Contact> contacts;
    private boolean isEnableDelete;
    List<CallNowHistory> callNowHistories;
    private ClickItem clickItem;

    public MissingCallHistoryAdapter(Context mContext, List<Contact> contacts, ClickItem clickItem) {
        this.mContext = mContext;
        this.contacts = contacts;
        this.clickItem = clickItem;
    }

    public void setData(List<CallNowHistory> callNowHistories) {
        this.callNowHistories = callNowHistories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
//        if (contacts.get(position).isHeader()){
        return TYPE_MISSING_CALL;
//        }else {
//            return TYPE_MISSING_CALL;
//        }
    }

    public void setEnableDelete(boolean isEnableDelete) {
        this.isEnableDelete = isEnableDelete;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_CALL_HISTORY) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_history_call_now, parent, false);
            return new CallHistoryViewHolder(v);
        } else if (viewType == TYPE_MISSING_CALL) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_history_call_now_red, parent, false);
            return new MissingCallViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == TYPE_CALL_HISTORY) {
                ((CallHistoryViewHolder) holder).bindItem(position);
            } else if (getItemViewType(position) == TYPE_MISSING_CALL) {
                ((MissingCallViewHolder) holder).bindItem(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return callNowHistories == null ? 0 : callNowHistories.size();
    }


    public class CallHistoryViewHolder extends RecyclerView.ViewHolder {
        private View btnDelete;

        public CallHistoryViewHolder(View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }

        public void bindItem(int position) {
            if (isEnableDelete) {
                btnDelete.setVisibility(View.VISIBLE);
            } else {
                btnDelete.setVisibility(View.GONE);
            }

        }
    }

    public class MissingCallViewHolder extends RecyclerView.ViewHolder {
        private View btnDelete;
        private TextView tvName;
        private TextView tvPhone;
        private int position;
        private TextView tvTimeMakeCall;

        public MissingCallViewHolder(View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvTimeMakeCall = itemView.findViewById(R.id.tvTimeMakeCall);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Account account = MyApplication.getInstance().getAccount();
                    if (callNowHistories.get(position).getReceiver().equals("84" + account.getMobile().substring(2))) {
                        clickItem.onClickItem(position, callNowHistories.get(position).getMobile());
                    } else {
                        clickItem.onClickItem(position, callNowHistories.get(position).getReceiver());
                    }
                }
            });

        }

        public void bindItem(int position) {
            this.position = position;
            if (isEnableDelete) {
                btnDelete.setVisibility(View.VISIBLE);
            } else {
                btnDelete.setVisibility(View.GONE);
            }

            try {
                long created = Long.parseLong(callNowHistories.get(position).getBeginTime());
                if (DateUtltils.isToday(created)) {
                    tvTimeMakeCall.setText(DateUtltils.timeToString6(created));
                } else {
                    tvTimeMakeCall.setText(DateUtltils.timeToString5(created));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                tvName.setText(callNowHistories.get(position).getReceiver());
                tvPhone.setText(callNowHistories.get(position).getReceiver());
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.onClickDelete(position, callNowHistories.get(position).getId());
                }
            });

            Account account = MyApplication.getInstance().getAccount();
            if (callNowHistories.get(position).getReceiver().equals("84" + account.getMobile().substring(2))) {

                try {
                    String name = ((MainActivity) mContext).contactHashMap.get(callNowHistories.get(position).getMobile()).getContactName();
                    tvName.setText(name);
                } catch (Exception e) {
                    tvName.setText(callNowHistories.get(position).getMobile());
                    e.printStackTrace();
                }
                tvPhone.setText(callNowHistories.get(position).getMobile());
            } else {

                try {
                    String name = ((MainActivity) mContext).contactHashMap.get(callNowHistories.get(position).getReceiver()).getContactName();
                    tvName.setText(name);
                } catch (Exception e) {
                    tvName.setText(callNowHistories.get(position).getReceiver());
                    e.printStackTrace();
                }
                tvPhone.setText(callNowHistories.get(position).getReceiver());
            }

        }
    }

    public interface ClickItem {
        void onClickItem(int position, String phone);

        void onClickDelete(int position, String id);
    }


}
