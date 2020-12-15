package com.namviet.vtvtravel.adapter.f2systeminbox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.response.f2systeminbox.DataSystemInbox;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.f2.DetailVideoActivity;
import com.namviet.vtvtravel.view.f2.ImagePartActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel;

import java.util.List;

public class SystemInboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private Context context;
    private List<SystemInbox.Data.InboxItem> inboxItems;
    private ClickItem clickItem;
    private SystemInboxViewModel systemInboxViewModel;


    public SystemInboxAdapter(List<SystemInbox.Data.InboxItem> inboxItems, Context context, ClickItem clickItem, SystemInboxViewModel systemInboxViewModel) {
        this.context = context;
        this.inboxItems = inboxItems;
        this.clickItem = clickItem;
        this.systemInboxViewModel = systemInboxViewModel;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.f2_item_system_inbox, parent, false);
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
            return inboxItems.size();
        } catch (Exception e) {
            return 0;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private TextView tvTitle;
        private CardView cardView;
        private int position;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inboxItems.get(position).getTitle() != null && inboxItems.get(position).getTitle().equals("INVITE_SCHEDULE")) {
                        DataSystemInbox dataSystemInbox = null;
                        try {
                            dataSystemInbox = inboxItems.get(position).getContent();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (dataSystemInbox != null) {
                                clickItem.onClickInvite(inboxItems.get(position), dataSystemInbox);
                            }

                            inboxItems.get(position).setStatus("1");
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (inboxItems.get(position).getTitle() != null && inboxItems.get(position).getTitle().equals("SHARE")) {
                        DataSystemInbox dataSystemInbox = null;
                        try {
                            dataSystemInbox = inboxItems.get(position).getContent();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if (dataSystemInbox != null) {
                                String cateId = "";
                                String link = "";
                                cateId = dataSystemInbox.getContentType();
                                link = dataSystemInbox.getDetailLink();
                                if (cateId == null) {
                                    return;
                                }

                                try {

                                    switch (cateId) {
                                        case Constants.ShareLinkType.IMAGES:
                                            ImagePartActivity.startScreen((Activity) context, ImagePartActivity.Type.DETAIL, link);
                                            break;
                                        case Constants.ShareLinkType.NEWS:
                                            TravelNewsActivity.openScreenDetail((Activity) context, TravelNewsActivity.OpenType.DETAIL, link);
                                            break;
                                        case Constants.ShareLinkType.VIDEO:
                                            DetailVideoActivity.startScreen(context, link);
                                            break;
                                        case Constants.ShareLinkType.PLACE:
                                        case Constants.ShareLinkType.CENTERS:
                                        case Constants.ShareLinkType.RESTAURANTS:
                                        case Constants.ShareLinkType.HOTELS:
                                            SmallLocationActivity.startScreenDetail(context, SmallLocationActivity.OpenType.DETAIL, link);
                                            break;

                                    }

                                } catch (Exception e) {

                                }
                            }

                            inboxItems.get(position).setStatus("1");
                            systemInboxViewModel.updateSystemInbox(inboxItems.get(position).getId());
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (inboxItems.get(position).getTitle() != null && inboxItems.get(position).getTitle().equals("TICKET")) {
                        DataSystemInbox dataSystemInbox = null;
                        try {
                            dataSystemInbox = inboxItems.get(position).getContent();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (dataSystemInbox != null) {
                                clickItem.onClickTicket(inboxItems.get(position), dataSystemInbox);
                            }

                            inboxItems.get(position).setStatus("1");
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        clickItem.onClickItem(inboxItems.get(position));
                        inboxItems.get(position).setStatus("1");
                        notifyDataSetChanged();
                    }
                }
            });


        }

        public void bindItem(int position) {
            this.position = position;
            if (inboxItems.get(position).getTitle() != null && inboxItems.get(position).getTitle().equals("INVITE_SCHEDULE")
                    || inboxItems.get(position).getTitle() != null && inboxItems.get(position).getTitle().equals("SHARE")
                    || inboxItems.get(position).getTitle() != null && inboxItems.get(position).getTitle().equals("TICKET")) {
                DataSystemInbox dataSystemInbox = null;
                try {
                    dataSystemInbox = inboxItems.get(position).getContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    tvTitle.setText(dataSystemInbox.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (inboxItems.get(position).getStatus().equals("0")) {
                        cardView.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvTitle.setTextColor(Color.parseColor("#000000"));
                    } else {
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        tvTitle.setTextColor(Color.parseColor("#878787"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    tvTime.setText(DateUtltils.timeToString(Long.valueOf(inboxItems.get(position).getCreatedAt()) / 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                DataSystemInbox dataSystemInbox = null;
                try {
                    dataSystemInbox = inboxItems.get(position).getContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    tvTitle.setText(dataSystemInbox.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (inboxItems.get(position).getStatus().equals("0")) {
                        cardView.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvTitle.setTextColor(Color.parseColor("#000000"));
                    } else {
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        tvTitle.setTextColor(Color.parseColor("#878787"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    tvTime.setText(DateUtltils.timeToString(Long.valueOf(inboxItems.get(position).getCreatedAt()) / 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public interface ClickItem {
        void onClickItem(SystemInbox.Data.InboxItem inboxItem);

        void onClickInvite(SystemInbox.Data.InboxItem inboxItem, DataSystemInbox dataSystemInbox);

        void onClickTicket(SystemInbox.Data.InboxItem inboxItem, DataSystemInbox dataSystemInbox);
    }


}
