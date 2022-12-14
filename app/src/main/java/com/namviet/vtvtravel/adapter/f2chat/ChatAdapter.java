package com.namviet.vtvtravel.adapter.f2chat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ChatSlideshowAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.chat.ChatAnswer;
import com.namviet.vtvtravel.model.chat.ChatData;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.f2.ChatActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.TreeChatBotActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseItemChat> {
    private ChatActivity mContext;
    private List<ChatData> mListChat;
    private ChatListener mChatListener;

    private RecyclerView recyclerView;

    public ChatAdapter(ChatActivity context, List<ChatData> chatDatas, ChatListener chatListener, RecyclerView recyclerView) {
        this.mContext = context;
        this.mListChat = chatDatas;
        this.mChatListener = chatListener;
        this.recyclerView = recyclerView;
    }

    public void updateChatList(final List<ChatData> chatBases, RecyclerView recyclerView) {
        mContext.runOnUiThread(() -> {
//            setChatList(chatBases);
            recyclerView.smoothScrollToPosition(mListChat.size() - 1);
        });
    }

    public void removeLastIndex(final List<ChatData> chatBases, RecyclerView recyclerView) {
        mContext.runOnUiThread(() -> {
//            setChatList(chatBases);
            recyclerView.smoothScrollToPosition(mListChat.size() - 1);
        });
    }

//    public void setChatList(List<ChatData> chatBases) {
//        this.mListChat = chatBases;
//        this.notifyDataSetChanged();
//    }

    @Override
    public int getItemViewType(int position) {
        Log.d("LamLV: ", mListChat.get(position).toString());
        String chat = mListChat.get(position).getType();
        if (Constants.TypeChat.USER_MESSAGE.equals(chat)) {
            return R.layout.f2_item_chat_user_message;
        } else if (Constants.TypeChat.WEATHER.equals(chat)) {
            return R.layout.f2_item_chat_weather;
        } else if (Constants.TypeChat.SLIDESHOW.equals(chat)) {
            return R.layout.f2_item_chat_slideshow;
        } else if (Constants.TypeChat.DIRECTION.equals(chat)) {
            return R.layout.item_chat_direction;
        } else if (Constants.TypeChat.ADMIN.equals(chat)) {
            return R.layout.f2_item_chat_admin;
        } else if (Constants.TypeChat.DEFECT_TEXT.equals(chat)) {
            return R.layout.f2_item_chat_defect_text;
        } else if (Constants.TypeChat.USER_SOCKET.equals(chat)) {
            return R.layout.f2_item_chat_user_socket;
        } else if (Constants.TypeChat.YES_NO_REVIEW.equals(chat)) {
            return R.layout.f2_item_chat_yes_no_review;
        } else if (Constants.TypeChat.THANK.equals(chat)) {
            return R.layout.f2_item_chat_thank;
        } else if (Constants.TypeChat.OPTION.equals(chat)) {
            return R.layout.f2_item_chat_option;
        } else if (Constants.TypeChat.FORM.equals(chat)) {
            return R.layout.item_chat_form;
        } else if (Constants.TypeChat.ADMIN_FIRST.equals(chat)) {
            return R.layout.f2_item_chat_admin_first;
        } else if (Constants.TypeChat.NOT_REPLY.equals(chat)) {
            return R.layout.f2_item_chat_not_reply;
        } else if (Constants.TypeChat.GET_USER_GUILD.equals(chat)) {
            return R.layout.f2_item_chat_get_user_guild;
        } else if (Constants.TypeChat.USER_GUILD_TEXT.equals(chat)) {
            return R.layout.f2_item_chat_user_guild_text;
        } else if (Constants.TypeChat.USER_GUILD_SLIDE.equals(chat)) {
            return R.layout.f2_item_chat_user_guild_slide;
        } else if (Constants.TypeChat.REVIEW.equals(chat)) {
            return R.layout.f2_item_chat_review;
        } else {
            return R.layout.f2_item_chat_text;
        }
    }

    @NonNull
    @Override
    public BaseItemChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.f2_item_chat_user_message:
                return new UserMessageHolder(v);
            case R.layout.f2_item_chat_weather:
                return new ChatWeatherHolder(v);
            case R.layout.f2_item_chat_slideshow:
                return new ChatSlideShowHolder(v);
            case R.layout.item_chat_direction:
                return new DirectionHolder(v);
            case R.layout.f2_item_chat_admin:
                return new AdminHolder(v);
            case R.layout.f2_item_chat_defect_text:
                return new DefectTextHolder(v);
            case R.layout.f2_item_chat_user_socket:
                return new UserSocketHolder(v);
            case R.layout.f2_item_chat_yes_no_review:
                return new YesNoReviewHolder(v);
            case R.layout.f2_item_chat_thank:
                return new ThankHolder(v);
            case R.layout.f2_item_chat_option:
                return new OptionHolder(v);
            case R.layout.f2_item_chat_admin_first:
                return new AdminFirstHolder(v);
            case R.layout.f2_item_chat_not_reply:
                return new NotReplyHolder(v);
            case R.layout.f2_item_chat_get_user_guild:
                return new GetUserGuildHolder(v);
            case R.layout.f2_item_chat_user_guild_text:
                return new UserGuildTextHolder(v);
            case R.layout.f2_item_chat_user_guild_slide:
                return new UserGuildSlideHolder(v);
            case R.layout.f2_item_chat_review:
                return new ReviewHolder(v);
            default:
                return new ChatTextHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseItemChat holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return mListChat == null ? 0 : mListChat.size();
    }

    public class BaseItemChat extends RecyclerView.ViewHolder {
        protected TextView mChatNameTv;
        protected ImageView mChatTextImv;
        protected TextView mChatTextTv;
        protected TextView mChatTimeTv;
        protected TextView mChatFirstTime;

        public BaseItemChat(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTextImv = itemView.findViewById(R.id.imv_chat_text);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            mChatFirstTime = itemView.findViewById(R.id.tv_first_time);
        }

        public void bindItem(int position) {
            ChatData chatData = mListChat.get(position);

            try {
                mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
            } catch (Exception e) {
                mChatNameTv.setText("Chatbot" + ", ");
            }
            if (chatData.getAvatar_url() != null && "".equals(chatData.getAvatar_url())) {
                setImageUrl(chatData.getAvatar_url(), mChatTextImv);
            }

            if ("".equals(chatData.getText()) && Constants.TypeChat.WEATHER.equals(chatData.getType())) {
                mChatTextTv.setText("VTV g???i ?? cho b???n");
            } else if ("".equals(chatData.getText()) && Constants.TypeChat.DIRECTION.equals(chatData.getType())) {
                mChatTextTv.setText("Ch??ng t??i c?? g???i ?? cho b???n:");
            } else if (position == 0) {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
//                    mChatTextTv.setText(spannableString(chatData.getText(), 8, 8 + account.getFullname().length()));
                    mChatTextTv.setText(chatData.getText());
                } else {
                    mChatTextTv.setText(chatData.getText());
                }
            } else {
                mChatTextTv.setText(chatData.getText());
            }

//            String time = DateUtltils.timeToString3((long) chatData.getCreated());
            mChatTimeTv.setText(chatData.getCurrent_time());

            if (position == 0) {
                mChatFirstTime.setVisibility(View.VISIBLE);
                Locale.setDefault(new Locale("vi", "VN"));
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_17, Locale.getDefault());
                Date d = new Date();
                mChatFirstTime.setText(sdf.format(d));
            } else {
                mChatFirstTime.setVisibility(View.GONE);
            }
        }

        private SpannableString spannableString(String text, int start, int end) {
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        }
    }

    public class ChatTextHolder extends BaseItemChat {
        public ChatTextHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
        }
    }

    public class ChatWeatherHolder extends BaseItemChat {
        private LinearLayout mWeatherBannerLl;
        private ImageView mWeatherIconImv;
        private TextView mWeatherDegreeTv;
        private TextView mWeatherSkyTv;
        private TextView mWeatherHumidity;
        private TextView mWeatherWindDirection;
        private TextView mWeatherWindSpeed;

        public ChatWeatherHolder(View itemView) {
            super(itemView);
            mWeatherBannerLl = itemView.findViewById(R.id.ll_weather_banner);
            mWeatherIconImv = itemView.findViewById(R.id.imv_weather_icon);
            mWeatherDegreeTv = itemView.findViewById(R.id.tv_weather_degree);
            mWeatherSkyTv = itemView.findViewById(R.id.tv_weather_sky);
            mWeatherHumidity = itemView.findViewById(R.id.tv_weather_humidity);
            mWeatherWindDirection = itemView.findViewById(R.id.tv_weather_wind_direction);
            mWeatherWindSpeed = itemView.findViewById(R.id.tv_weather_wind_speed);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            ChatAnswer chatAnswer = mListChat.get(position).getAnswers().get(0);
            setImageToLinearLayout(chatAnswer.getBanner_url(), mWeatherBannerLl);
            setImageUrl(chatAnswer.getWeather().getIcon_url(), mWeatherIconImv);
            mWeatherDegreeTv.setText((int) chatAnswer.getTemp() + "??");
            mWeatherSkyTv.setText(chatAnswer.getWeather().getDescription());
            mWeatherHumidity.setText("????? ???m: " + (int) chatAnswer.getHumidity() + "%");
            mWeatherWindDirection.setText("H?????ng gi??: " + (int) chatAnswer.getWind().getDeg() + "??");
            mWeatherWindSpeed.setText("T???c ????? gi??: " + chatAnswer.getWind().getSpeed() + "m/s");
        }

        private void setImageToLinearLayout(String url, LinearLayout linearLayout) {
            Glide.with(mContext).load(url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.setBackground(resource);
                    }
                }
            });
        }
    }

    public class ChatSlideShowHolder extends BaseItemChat implements TravelSelectListener {
        private RecyclerView mChatSlideShowRecycler;

        public ChatSlideShowHolder(View itemView) {
            super(itemView);
            mChatSlideShowRecycler = itemView.findViewById(R.id.recycler_chat_slideshow);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            List<ChatAnswer> chatAnswers;
            chatAnswers = mListChat.get(position).getAnswers();
            ChatSlideshowAdapter chatSlideshowAdapter = new ChatSlideshowAdapter(mContext, chatAnswers);

            chatSlideshowAdapter.setTravelSelectListener(this);

            mChatSlideShowRecycler.setAdapter(chatSlideshowAdapter);
        }

        @Override
        public void onSelectTravel(Travel travel) {
            TreeChatBotActivity.startScreen(mContext, travel);
//            if (WSConfig.Api.GET_PLACE.equals(travel.getContent_type())) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
//                mContext.setBundle(bundle);
//                mContext.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
//            } else if (WSConfig.Api.GET_RESTAURANTS.equals(travel.getContent_type())) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
//                mContext.setBundle(bundle);
//                mContext.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
//            } else if (WSConfig.Api.GET_HOTELS.equals(travel.getContent_type())) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
//                mContext.setBundle(bundle);
//                mContext.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_STAY_SCREEN);
//            } else if (WSConfig.Api.GET_CENTER.equals(travel.getContent_type())) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
//                mContext.setBundle(bundle);
//                mContext.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
//            }
        }
    }

    public class UserMessageHolder extends BaseItemChat {
        public UserMessageHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindItem(int position) {
            ChatData chatData = mListChat.get(position);

            initUserInfo(mChatNameTv, mChatTextImv);

            mChatTextTv.setText(chatData.getText());
//            String time = chatData.getCurrent_time();
            mChatTimeTv.setText(chatData.getCurrent_time());
        }

        private void initUserInfo(TextView name, ImageView avatar) {
            Account account = MyApplication.getInstance().getAccount();
            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.error(R.mipmap.user);
            if (null != account && account.isLogin()) {
                if (account.getImageProfile() != null && !account.getImageProfile().isEmpty()) {
                    Glide.with(mContext).load(account.getImageProfile()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.f2_defaut_user).error(R.drawable.f2_defaut_user).into(avatar);
                } else {
                    avatar.setImageResource(R.drawable.f2_defaut_user);
                }
                try {
                    name.setText(account.getFullname() + ", ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class DirectionHolder extends BaseItemChat {
        private WebView mDirectionWv;

        public DirectionHolder(View itemView) {
            super(itemView);
            mDirectionWv = itemView.findViewById(R.id.wv_chat_direction);
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            String url = mListChat.get(position).getAnswers().get(0).getIframe();
            mDirectionWv.getSettings().setJavaScriptEnabled(true);
            mDirectionWv.loadData("<iframe src=\"" + url + "\" ></iframe>", "text/html; charset=utf-8", "UTF-8");
        }
    }

    public class UserSocketHolder extends BaseItemChat {
        private TextView mChatNameTv;
        private ImageView mChatTextImv;
        private TextView mChatTextTv;
        private TextView mChatTimeTv;
        private TextView mChatFirstTime;

        public UserSocketHolder(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTextImv = itemView.findViewById(R.id.imv_chat_text);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            mChatFirstTime = itemView.findViewById(R.id.tv_first_time);
        }

        @Override
        public void bindItem(int position) {
            ChatData chatData = mListChat.get(position);

            initUserInfo(mChatNameTv, mChatTextImv);

            mChatTextTv.setText(chatData.getContent());
//            String time = chatData.getCurrent_time();
            mChatTimeTv.setText(chatData.getCurrent_time());
        }

        private void initUserInfo(TextView name, ImageView avatar) {
            Account account = MyApplication.getInstance().getAccount();
            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.error(R.mipmap.user);
            if (null != account && account.isLogin()) {
                if (account.getImageProfile() != null && !account.getImageProfile().isEmpty()) {
                    Glide.with(mContext).load(account.getImageProfile()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.f2_defaut_user).error(R.drawable.f2_defaut_user).into(avatar);
                } else {
                    avatar.setImageResource(R.drawable.f2_defaut_user);
                }
                try {
                    name.setText(account.getFullname() + ", ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
    }

    public class AdminHolder extends BaseItemChat {
        private TextView mChatNameTv;
        private ImageView mChatTextImv;
        private TextView mChatTextTv;
        private TextView mChatTimeTv;
        private TextView mChatFirstTime;

        public AdminHolder(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTextImv = itemView.findViewById(R.id.imv_chat_text);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            mChatFirstTime = itemView.findViewById(R.id.tv_first_time);
        }

        public void bindItem(int position) {
            try {
                MyApplication.getInstance().setAdminChated(true);
                ChatData chatData = mListChat.get(position);
                if (chatData.getSender() != null && null != chatData.getSender().getFull_name()) {
                    mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
                }else {
                    mChatNameTv.setText("T???ng ????i vi??n" + ", ");
                }
                mChatTextTv.setText(chatData.getContent());
                if (chatData.getSender() != null && null != chatData.getSender().getAvatarUrl()) {
                    setImageUrl(chatData.getSender().getAvatarUrl(), mChatTextImv);
                }else {
                    Glide.with(mContext).load(R.drawable.f2_ic_chat6).into(mChatTextImv);
                }
                String time = DateUtltils.timeToString4((long) chatData.getCreated());
                mChatTimeTv.setText(chatData.getCurrent_time());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        }
    }

    public class DefectTextHolder extends BaseItemChat {
        private TextView mChatTextTv;
        private TextView mChatTimeTv;

        public DefectTextHolder(View itemView) {
            super(itemView);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
        }

        public void bindItem(int position) {
            try {
                ChatData chatData = mListChat.get(position);
                mChatTextTv.setText(chatData.getContent());
                mChatTimeTv.setText(chatData.getCurrent_time());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        }
    }

    public class YesNoReviewHolder extends BaseItemChat {
        private TextView mChatNameTv;
        private TextView mChatTextTv;
        private TextView mChatTimeTv;
        private Button mYesBtn;
        private Button mNoBtn;
        private ImageView mChatTextImv;

        public YesNoReviewHolder(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            mYesBtn = itemView.findViewById(R.id.btnYes);
            mNoBtn = itemView.findViewById(R.id.btnNo);
            mChatTextImv = itemView.findViewById(R.id.imv_chat_text);
        }

        public void bindItem(int position) {
            ChatData chatData = mListChat.get(position);
            mChatNameTv.setText("Chatbot" + ", ");
            mChatTimeTv.setText(chatData.getCurrent_time());

//            mChatTextImv.setImageResource(R.drawable.ic_bot);

            String mess = mListChat.get(position).getText();
            Account account = MyApplication.getInstance().getAccount();
            try {
                if (null != account && account.isLogin()) {
                    mChatTextTv.setText(mess);
//                    mChatTextTv.setText(spannableString(mess, 7, 7 + account.getFullname().length()));
                } else {
                    mChatTextTv.setText(mess);
                }
            } catch (Exception e) {
                //Kh??ng c?? fullname
                mChatTextTv.setText(mess);
                e.printStackTrace();
            }

            mYesBtn.setOnClickListener(v -> {
                mChatListener.clickYesReview();
            });

            mNoBtn.setOnClickListener(v -> {
                mChatListener.clickNoReview();
            });
        }

//        private SpannableString spannableString(String text, int start, int end) {
//            SpannableString spannableString = new SpannableString(text);
//            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return spannableString;
//        }
    }

    public class ThankHolder extends BaseItemChat {
        private TextView mThankTxt;

        public ThankHolder(View itemView) {
            super(itemView);
            mThankTxt = itemView.findViewById(R.id.txtThank);
        }

        public void bindItem(int position) {
            String mess = mListChat.get(position).getText();
            Account account = MyApplication.getInstance().getAccount();
            try {
                if (null != account && account.isLogin()) {
                    mThankTxt.setText(spannableString(mess, 7, 7 + account.getFullname().length()));
                } else {
                    mThankTxt.setText(mess);
                }
            } catch (Exception e) {
                //khong c?? fullname
                mThankTxt.setText(mess);
                e.printStackTrace();
            }

            try {
                mChatTimeTv.setText(mListChat.get(position).getCurrent_time());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private SpannableString spannableString(String text, int start, int end) {
            SpannableString spannableString = new SpannableString(text);
//            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#424242")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }

    public class OptionHolder extends BaseItemChat {
        private Button mFormBtn;
        private Button mChatBtn;
        private Button m1039Btn;


        public OptionHolder(View itemView) {
            super(itemView);
            mFormBtn = itemView.findViewById(R.id.btnForm);
            mChatBtn = itemView.findViewById(R.id.btnChat);
            m1039Btn = itemView.findViewById(R.id.btn1039);


        }

        public void bindItem(int position) {

            mFormBtn.setOnClickListener(v -> {
                mChatListener.optionForm();
            });
            mChatBtn.setOnClickListener(v -> {
                mChatListener.optionChat();
            });
            m1039Btn.setOnClickListener(v -> {
                mChatListener.option1039();
            });


            try {
                mChatTextTv.setText((mListChat.get(position).getText()));
                mChatTimeTv.setText(mListChat.get(position).getCurrent_time());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public class AdminFirstHolder extends BaseItemChat {
        private TextView mChatNameTv;
        private ImageView mChatTextImv;
        private TextView mChatTextTv;
        private TextView mChatTimeTv;
        private TextView mChatFirstTime;

        public AdminFirstHolder(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTextImv = itemView.findViewById(R.id.imv_chat_text);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            mChatFirstTime = itemView.findViewById(R.id.tv_first_time);
        }

        public void bindItem(int position) {
            ChatData chatData = mListChat.get(position);
//            mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
            mChatNameTv.setText("Chatbot, ");
            mChatTextTv.setText(chatData.getText());
            mChatTimeTv.setText(chatData.getCurrent_time());
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        }
    }

    public class NotReplyHolder extends BaseItemChat {
        private TextView mChatNameTv;
        private ImageView mChatTextImv;
        private TextView mChatTextTv;
        private TextView mChatTimeTv;
        private TextView mChatFirstTime;
        private LinearLayout btnFeedBack;

        public NotReplyHolder(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTextImv = itemView.findViewById(R.id.imv_chat_text);
            mChatTextTv = itemView.findViewById(R.id.tv_chat_text);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            mChatFirstTime = itemView.findViewById(R.id.tv_first_time);
            btnFeedBack = itemView.findViewById(R.id.btnFeedBack);
        }

        public void bindItem(int position) {
            try {
                MyApplication.getInstance().setAdminChated(true);
                ChatData chatData = mListChat.get(position);
                if (null != chatData.getSender().getFull_name()) {
                    mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
                }
                mChatTextTv.setText(chatData.getContent());
                mChatTimeTv.setText(chatData.getCurrent_time());
            } catch (Exception e) {
                e.printStackTrace();
            }
            btnFeedBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mChatListener.optionForm();
                }
            });
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
        }
    }

    public class GetUserGuildHolder extends BaseItemChat implements GetUserGuildAdapter.ClickItem {
        private RecyclerView recycleGetUserGuild;

        public GetUserGuildHolder(View itemView) {
            super(itemView);
            recycleGetUserGuild = itemView.findViewById(R.id.recycleGetUserGuild);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            ChatData chatData = mListChat.get(position);

            GetUserGuildAdapter getUserGuildAdapter = new GetUserGuildAdapter(mContext, chatData.getItemGetUserGuild());
            getUserGuildAdapter.setClickItem(this);
            recycleGetUserGuild.setAdapter(getUserGuildAdapter);
        }

        @Override
        public void getCode(String code) {
            mChatListener.clickGetUserGuild(code);
        }
    }

    public class UserGuildTextHolder extends BaseItemChat implements UserGuildTextAdapter.ClickItem {
        private RecyclerView recycleRegisterVip;

        public UserGuildTextHolder(View itemView) {
            super(itemView);
            recycleRegisterVip = itemView.findViewById(R.id.recycleRegisterVip);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            ChatData chatData = mListChat.get(position);

            UserGuildTextAdapter userGuildTextAdapter = new UserGuildTextAdapter(mContext, chatData.getItemPostUserGuild(), this);
            recycleRegisterVip.setAdapter(userGuildTextAdapter);

            try {
                mChatTimeTv.setText(chatData.getCurrent_time());
            } catch (Exception e) {
                mChatTimeTv.setText("");
            }
            try {
                mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
            } catch (Exception e) {
                mChatNameTv.setText("Chatbot" + ", ");
            }
        }

        @Override
        public void clickItem() {
            mChatListener.clickShortLink();
        }
    }

    public class UserGuildSlideHolder extends BaseItemChat implements UserGuildSlideAdapter.ClickItem {
        private RecyclerView recycleDeal;

        public UserGuildSlideHolder(View itemView) {
            super(itemView);
            recycleDeal = itemView.findViewById(R.id.recycleDeal);
        }

        @Override
        public void bindItem(int position) {
            super.bindItem(position);
            ChatData chatData = mListChat.get(position);

            UserGuildSlideAdapter userGuildSlideAdapter = new UserGuildSlideAdapter(mContext, chatData.getItemPostUserGuild(), recyclerView);
            recycleDeal.setAdapter(userGuildSlideAdapter);
            userGuildSlideAdapter.setClickItem(this);

            try {
                mChatTimeTv.setText(chatData.getCurrent_time());
            } catch (Exception e) {
                mChatTimeTv.setText("");
            }
            try {
                mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
            } catch (Exception e) {
                mChatNameTv.setText("Chatbot" + ", ");
            }
        }

        @Override
        public void clickDealButton(String url) {
//            DetailDealWebviewActivity.startScreen(mContext, url);
            mChatListener.clickWebViewDeal(url);
        }
    }

    public class ReviewHolder extends BaseItemChat {
        private TextView mChatNameTv;
        private TextView mChatTimeTv;
        private LinearLayout reviewBtn;

        public ReviewHolder(View itemView) {
            super(itemView);
            mChatNameTv = itemView.findViewById(R.id.tv_chat_name);
            mChatTimeTv = itemView.findViewById(R.id.tv_chat_time);
            reviewBtn = itemView.findViewById(R.id.reviewBtn);
        }

        public void bindItem(int position) {
            ChatData chatData = mListChat.get(position);
            try {
                mChatTimeTv.setText(chatData.getCurrent_time());
            } catch (Exception e) {
                mChatTimeTv.setText("");
            }
            try {
                mChatNameTv.setText(chatData.getSender().getFull_name() + ", ");
            } catch (Exception e) {
                mChatNameTv.setText("Chatbot" + ", ");
            }

            reviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mChatListener.clickReview();
                }
            });
        }
    }

    public interface ChatListener {
        void clickYesReview();

        void clickNoReview();

        void optionForm();

        void optionChat();

        void option1039();

        void clickGetUserGuild(String code);

        void clickReview();

        void clickWebViewDeal(String url);

        void clickShortLink();
    }
}
