package com.namviet.vtvtravel.view.fragment.f2chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baseapp.utils.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2chat.ChatAdapter;
import com.namviet.vtvtravel.api.ParamSocket;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentChatBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.help.SocketOn;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.chat.ChatData;
import com.namviet.vtvtravel.model.f2event.OnBackToChatBot;
import com.namviet.vtvtravel.model.f2event.OnReOpenChatScreen;
import com.namviet.vtvtravel.model.f2event.OnReviewSuccess;
import com.namviet.vtvtravel.model.f2event.OnSocketSendSurvey;
import com.namviet.vtvtravel.response.ChatResponse;
import com.namviet.vtvtravel.response.CreateRoomResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserThemeResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.ChatActivity;
import com.namviet.vtvtravel.view.f2.DetailDealWebviewActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ReviewChatActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.FormChatActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;
import com.namviet.vtvtravel.viewmodel.ChatViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class ChatFragmentV2 extends BaseFragment<F2FragmentChatBinding> implements Observer, ChatAdapter.ChatListener, View.OnClickListener {
    //    private static final String URL = "http://103.21.148.54:8079";
    private static final String URL = "https://chat.vtvtravel.vn";

    public Socket mSocket;

    private static final String SEND_MESSAGE = "send_message";

    private static final String RECEIVE_MESSAGE = "receive_message";
    private static final String NOT_REPLY_MESSAGE = "not_reply_message";
    private static final String ADMIN_SEND_SURVEY = "admin_send_survey";

    private ChatViewModel mChatViewModel;
    private ChatAdapter mChatAdapter;
    private List<ChatData> mListChat = new ArrayList<>();
    private CountDownTimer mTimer60s;
    private boolean isBot = true;


    private Handler handler;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_chat;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initViews();
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.CHAT, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.CHAT_BOT, TrackingAnalytic.ScreenTitle.CHAT_BOT).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    QuestionChangeThemeDialog questionChangeThemeDialog = new QuestionChangeThemeDialog(new QuestionChangeThemeDialog.ClickOption() {
                        @Override
                        public void clickChangeTheme() {
                            try {
                                Account account = MyApplication.getInstance().getAccount();
                                if (null != account && account.isLogin()) {
                                    ChangeThemeDialog changeThemeDialog = ChangeThemeDialog.Companion.newInstance(new ChangeThemeDialog.Click() {
                                        @Override
                                        public void onClick(String url) {
                                            if (url != null && url.length() > 0)
                                                Glide.with(mActivity).load(url).into(getBinding().imgBackground);
                                        }
                                    });
                                    changeThemeDialog.show(mActivity.getSupportFragmentManager(), null);
                                } else {
                                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    questionChangeThemeDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    protected void initViews() {
        getBinding().imgBack.setOnClickListener(this);
        getBinding().layoutSend.setOnClickListener(this);
        getBinding().imgSmile.setOnClickListener(this);
        getBinding().layoutChat.setVisibility(View.GONE);
        getBinding().view.setVisibility(View.GONE);

        mChatAdapter = new ChatAdapter((ChatActivity) mActivity, mListChat, this);
        getBinding().recyclerChat.setAdapter(mChatAdapter);
        mChatViewModel = new ChatViewModel();
        getBinding().setChatViewModel(mChatViewModel);
        mChatViewModel.addObserver(this);
        initTextChangeListener();

        initChat();
    }

    private void initChat() {
        mChatViewModel.getUserGuild();
        try {
            getBinding().recyclerChat.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        getBinding().recyclerChat.scrollToPosition(
                                Objects.requireNonNull(getBinding().recyclerChat.getAdapter()).getItemCount() - 1);
                    } catch (Exception e) {
                    }
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mChatViewModel.getUserTheme();
    }

    private String getImage() {
        Account account = MyApplication.getInstance().getAccount();
        String avatarUrl;
        if (null != account && account.isLogin()) {
            avatarUrl = account.getImageProfile();
        } else {
            avatarUrl = "";
        }
        return avatarUrl;
    }

    private String getCurrentTime() {
        Calendar rightNow = Calendar.getInstance();

        int currentHourIn12Format = rightNow.get(Calendar.HOUR);
        int currentMinuteIn12Format = rightNow.get(Calendar.MINUTE);

        String hour;
        String minute;
        if (currentHourIn12Format < 10) {
            hour = "0" + currentHourIn12Format;
        } else {
            hour = String.valueOf(currentHourIn12Format);
        }

        if (currentMinuteIn12Format < 10) {
            minute = "0" + currentMinuteIn12Format;
        } else {
            minute = String.valueOf(currentMinuteIn12Format);
        }
        int currentTypeIn12Format = rightNow.get(Calendar.AM_PM);
        String time;
        if (currentTypeIn12Format == Calendar.AM) {
            time = hour + ":" + minute + " AM";
        } else {
            time = hour + ":" + minute + " PM";
        }
        return time;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                try {
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                } catch (Exception e) {

                }
                mActivity.finish();
                break;
            case R.id.layoutSend:
                startCountDown(180000);
                String mess = getBinding().edtTypeMessage.getText().toString();

                if (mess != null && mess.isEmpty()) {
                    return;
                }

                if (MyApplication.getInstance().getRoom() != null) {
                    String roomId = MyApplication.getInstance().getRoom().getRoom().getId();
                    String uuid = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
                    String clientMessageId = "android" + uuid;
                    emitMessage(roomId, mess, clientMessageId);
                } else {
                    Toast.makeText(mActivity, "Create room error", Toast.LENGTH_SHORT).show();
                }
                getBinding().edtTypeMessage.setText("");

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.CHAT, TrackingAnalytic.getDefault("", "").setMessage(mess).setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imgSmile:
//                Toast.makeText(mActivity, "Smile", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private boolean isAlreadyCall = false;

    public void call(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, (dialog, id) -> {
                    String phone = "1039";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    mActivity.startActivity(intent);
                    isAlreadyCall = true;
                    cancelTimer();
                })
                .setNegativeButton(R.string.dimiss, (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initTextChangeListener() {
        getBinding().edtTypeMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    enableSend();
                    startCountDown(180000);
                } else {
                    disableSend();
                }
            }
        });
    }

    private void enableSend() {
        getBinding().layoutSend.setClickable(true);
        getBinding().btnSend.setImageResource(R.drawable.vt_send_green);
        getBinding().btnSend.setAlpha(1f);
    }

    private void disableSend() {
        getBinding().btnSend.setAlpha(0.5f);
        getBinding().layoutSend.setClickable(false);
        getBinding().btnSend.setImageResource(R.drawable.vt_send_gray);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof ChatResponse) {
                ChatResponse chatResponse2 = (ChatResponse) arg;
                if (chatResponse2.getData().getText_sensitive() != null && chatResponse2.getData().getText_sensitive().size() > 0) {
                    for (String s : chatResponse2.getData().getText_sensitive()) {
                        handleChat(s, getCurrentTime(), chatResponse2.getData().getAvatar_url(), "");
                    }
                } else {
                    if (MyApplication.getInstance().getmChatDatas().size() > 0) {
                        chatResponse2.getData().setCurrent_time(getCurrentTime());
                        mListChat.add(chatResponse2.getData());
                        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
                        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
                    } else {
                        chatResponse2.getData().setCurrent_time(getCurrentTime());

                        String mess = "Welcome Quý khách to VTVTravel. Xin chào a/c, VTVTravel rất mong được hỗ trợ.";
                        Account account = MyApplication.getInstance().getAccount();
                        if (null != account && account.isLogin() && account.getFullname() != null && !"".equals(account.getFullname())) {
                            mess = "Welcome " + account.getFullname() + " to VTVTravel. Xin chào a/c, VTVTravel rất mong được hỗ trợ.";
                        }

                        chatResponse2.getData().setText(mess);
                        mListChat.add(chatResponse2.getData());
                        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
                        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
                    }
                }
            } else if (arg instanceof GetUserGuildResponse) {
                GetUserGuildResponse response = (GetUserGuildResponse) arg;
                ChatData chatData = new ChatData();
                chatData.setItemGetUserGuild(response.getData().getItems());
                chatData.setText(response.getData().getOnbot());
                chatData.setCurrent_time(getCurrentTime());
                chatData.setType(Constants.TypeChat.GET_USER_GUILD);
                handleChat(chatData);
            } else if (arg instanceof GetUserThemeResponse) {
                GetUserThemeResponse response = (GetUserThemeResponse) arg;
                try {
                    Glide.with(mActivity).load(response.getData().getPathUri()).into(getBinding().imgBackground);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (arg instanceof PostUserGuildResponse) {
                PostUserGuildResponse response = (PostUserGuildResponse) arg;
                ChatData chatData = new ChatData();
                chatData.setItemPostUserGuild(response.getData().getItems());
                chatData.setText(response.getData().getOnbot());
                chatData.setCurrent_time(getCurrentTime());
                if ("TEXT".equals(response.getData().getType()) || "TEXT_BUTTON".equals(response.getData().getType())) {
                    chatData.setType(Constants.TypeChat.USER_GUILD_TEXT);
                    handleChat(chatData);
                    handleChatReview("", getCurrentTime(), Constants.TypeChat.REVIEW);
                } else if ("SLIDE".equals(response.getData().getType())) {
                    chatData.setType(Constants.TypeChat.USER_GUILD_SLIDE);
                    handleChat(chatData);
                    handleChatReview("", getCurrentTime(), Constants.TypeChat.REVIEW);
                } else if ("CSKH".equals(response.getData().getCode()) || "CSKH".equals(response.getData().getType())) {
                    handleChat("", getCurrentTime(), Constants.TypeChat.OPTION);
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                }
            } else if (arg instanceof CreateRoomResponse) {
                isBot = false;
                CreateRoomResponse createRoomResponse = (CreateRoomResponse) arg;
                initSocket(createRoomResponse.getData().getTokenMemberId());
                MyApplication.getInstance().setRoom(createRoomResponse.getData());
                onMessage();
                onNotReplyMessage();
                onAdminSendSurvey();
            } else if (arg instanceof ResponseError) {
                ResponseError responseError = (ResponseError) arg;
//                showMessage(responseError.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (mTimer60s != null) {
            mTimer60s.cancel();
        }

        if (mSocket != null) {
            mSocket.close();
        }
        EventBus.getDefault().unregister(this);
        MyApplication.getInstance().getmChatDatas().clear();
        super.onDestroy();
    }

    @Subscribe
    public void onSocket(SocketOn socketOn) {
        handleOnReceive();
    }

    @Override
    public void clickShortLink() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            cancelTimer();
            ServiceActivity.startScreen(mActivity);
        } else {
            RequestAccountDialog requestAccountDialog = RequestAccountDialog.newInstance(new RequestAccountDialog.ClickButton() {
                @Override
                public void clickLogin() {
                    cancelTimer();
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

                @Override
                public void clickExit() {
                    startCountDown(60000);
                }
            });
            requestAccountDialog.show(mActivity.getSupportFragmentManager(), null);
        }
    }

    @Override
    public void clickYesReview() {
        if (isBot) {
            removeLastIndex();
            String mess = "Cảm ơn Quý khách! Chúc Quý khách có những trải nghiệm tuyệt vời khi sử dụng dịch vụ của VTVTravel!";
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin() && account.getFullname() != null && !"".equals(account.getFullname())) {
                mess = "Cảm ơn " + account.getFullname() + "! Chúc " + account.getFullname() + " có những trải nghiệm tuyệt vời khi sử dụng dịch vụ của VTVTravel!";
            }
            handleChat(mess, getCurrentTime(), Constants.TypeChat.THANK);
            mChatViewModel.yesNoReview("Có");
            startCountDown(15000);
            getBinding().layoutChat.setVisibility(View.GONE);
            getBinding().view.setVisibility(View.GONE);
        }
    }

    @Override
    public void clickNoReview() {
        if (!isBot) {
            return;
        }
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            removeLastIndex();
            handleChat("Để hỗ trợ Quý khách tốt hơn và nâng cao chất lượng dịch vụ, vui lòng chọn một trong các hình thức sau:", getCurrentTime(), Constants.TypeChat.OPTION);
            mChatViewModel.yesNoReview("Không");
            startCountDown(60000);
            getBinding().layoutChat.setVisibility(View.GONE);
            getBinding().view.setVisibility(View.GONE);
        } else {
            RequestAccountDialog requestAccountDialog = RequestAccountDialog.newInstance(new RequestAccountDialog.ClickButton() {
                @Override
                public void clickLogin() {
                    cancelTimer();
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

                @Override
                public void clickExit() {
                    startCountDown(60000);
                }
            });
            requestAccountDialog.show(mActivity.getSupportFragmentManager(), null);
        }
    }

    @Override
    public void optionForm() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
//            removeLastIndex();
            FormChatActivity.startScreen(mActivity);
            getBinding().layoutChat.setVisibility(View.GONE);
            getBinding().view.setVisibility(View.GONE);
            cancelTimer();
        } else {
            RequestAccountDialog requestAccountDialog = RequestAccountDialog.newInstance(new RequestAccountDialog.ClickButton() {
                @Override
                public void clickLogin() {
                    cancelTimer();
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

                @Override
                public void clickExit() {
                    startCountDown(60000);
                }
            });
            requestAccountDialog.show(mActivity.getSupportFragmentManager(), null);
        }
    }

    private void cancelTimer() {
        if (mTimer60s != null) {
            mTimer60s.cancel();
        }
    }

    @Override
    public void option1039() {
        if (!isBot) {
            return;
        }
//        removeLastIndex();
        getBinding().layoutChat.setVisibility(View.GONE);
        getBinding().view.setVisibility(View.GONE);
        call(getString(R.string.calling_address));
        startCountDown(60000);
    }

    @Override
    public void clickGetUserGuild(String code) {
        if (!isBot) {
            return;
        }
        mChatViewModel.postUserGuild(code);
        getBinding().layoutChat.setVisibility(View.GONE);
        getBinding().view.setVisibility(View.GONE);
    }

    @Override
    public void clickReview() {
        if (!isBot) {
            return;
        }
        String mess = "Cảm ơn Quý khách đã đồng hành cùng VTVTravel. Quý khách có hài lòng với câu trả lời của chúng tôi không?";
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin() && account.getFullname() != null && !"".equals(account.getFullname())) {
            mess = "Cảm ơn " + account.getFullname() + " đã đồng hành cùng VTVTravel. Quý khách có hài lòng với câu trả lời của chúng tôi không?";
        }
        handleChat(mess, getCurrentTime(), Constants.TypeChat.YES_NO_REVIEW, "Chatbot, ", true);
        startCountDown(60000);
        getBinding().layoutChat.setVisibility(View.GONE);
        getBinding().view.setVisibility(View.GONE);
    }

    @Override
    public void clickWebViewDeal(String url) {
        cancelTimer();
        DetailDealWebviewActivity.startScreen(mActivity, url);
    }

    @Override
    public void optionChat() {
        startCountDown(180000);
        getBinding().layoutChat.setVisibility(View.VISIBLE);
        getBinding().view.setVisibility(View.VISIBLE);
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            removeLastIndex();
            handleChat("Chào Quý khách, Quý khách mong muốn chúng tôi hỗ trợ về vấn đề gì?", getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Chatbot", true);
            MyApplication.getInstance().setChatBot(false);
            try {
                String mobile = "anonymous";
                if (MyApplication.getInstance().getAccount() != null && !MyApplication.getInstance().getAccount().getMobile().isEmpty()) {
                    mobile = MyApplication.getInstance().getAccount().getMobile();
                }
                mChatViewModel.createRoom(mobile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            RequestAccountDialog requestAccountDialog = RequestAccountDialog.newInstance(new RequestAccountDialog.ClickButton() {
                @Override
                public void clickLogin() {
                    cancelTimer();
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

                @Override
                public void clickExit() {
                    startCountDown(60000);
                }
            });
            requestAccountDialog.show(mActivity.getSupportFragmentManager(), null);
        }
    }

    @Subscribe
    public void onBackToChatBot(OnBackToChatBot onBackToChatBot) {
        try {
            String mess = "Tạm biệt Quý khách, VTVTravel luôn mong muốn được đồng hành cùng bạn trong thời gian tới. Xin cảm ơn!";
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin() && account.getFullname() != null && !"".equals(account.getFullname())) {
                mess = "Tạm biệt " + account.getFullname() + ", VTVTravel luôn mong muốn được đồng hành cùng bạn trong thời gian tới. Xin cảm ơn!";
            }
            handleChat(mess, getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Chatbot", true);


            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new OnReOpenChatScreen());
                    //                mActivity.finish();
                }
            }, 10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void onSocketSendSurvey(OnSocketSendSurvey onSocketSendSurvey) {
        addFragment(new ReviewChatFragment());
    }

    private void handleChat(String mess, String time, String type) {
        ChatData userMessage = new ChatData(type, mess, time, getImage());
        mListChat.add(userMessage);

        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void handleChatReview(String mess, String time, String type) {
        ChatData userMessage = new ChatData(type, mess, time, getImage());
        for (int i = 0; i < mListChat.size(); i++) {
            if (mListChat.get(i).getType().equals(Constants.TypeChat.REVIEW)) {
                mListChat.remove(i);
            }
        }

        mListChat.add(userMessage);

        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void handleChat(String mess, String time, String type, String name, boolean check) {
        ChatData.Sender sender = new ChatData.Sender();
        sender.setFull_name(name);
        ChatData userMessage = new ChatData(type, mess, time, getImage(), sender);
        mListChat.add(userMessage);

        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void handleChat(String mess, String time, String avatar, String type) {
        ChatData userMessage = new ChatData(type, mess, time, avatar);
        mListChat.add(userMessage);

        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void handleChat(ChatData userMessage) {
        mListChat.add(userMessage);

        mChatAdapter.notifyItemInserted(mListChat.size() - 1);
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void handleOnReceive() {

        mChatAdapter.notifyDataSetChanged();
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void removeLastIndex() {
        mListChat.remove(mListChat.size() - 1);

        mChatAdapter.notifyItemRemoved(mListChat.size() - 1);
        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
    }

    private void startCountDown(int milliSecond) {
        final int[] i = {0};
        if (mTimer60s != null) {
            mTimer60s.cancel();
            i[0] = 0;
        }
//        mTimer60s = new CountDownTimer(milliSecond, 1000) {
        mTimer60s = new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {
                i[0] = i[0] + 1;
            }

            public void onFinish() {
                try {
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                } catch (Exception e) {

                }
                try {
                    String mess = "Tạm biệt Quý khách, VTVTravel luôn mong muốn được đồng hành cùng bạn trong thời gian tới. Xin cảm ơn!";
                    Account account = MyApplication.getInstance().getAccount();
                    if (null != account && account.isLogin() && account.getFullname() != null && !"".equals(account.getFullname())) {
                        mess = "Tạm biệt " + account.getFullname() + ", VTVTravel luôn mong muốn được đồng hành cùng bạn trong thời gian tới. Xin cảm ơn!";
                    }
                    handleChat(mess, getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Chatbot", true);
                } catch (Exception e) {

                }


                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EventBus.getDefault().post(new OnReOpenChatScreen());
//                            removeFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 10000);


//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mListChat.clear();
//                        mChatAdapter.notifyDataSetChanged();
//                        mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
//                        initChat();
//                    }
//                });

            }

        }.start();
    }

    private void removeFragment() {
        mActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


    public void initSocket(String token) {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.path = "/chat-socket";
            opts.query = "auth_token=" + token;
            mSocket = IO.socket(WSConfig.URL_SOCKET, opts);
            mSocket.connect();


            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT", "hihi");
                }
            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void emitMessage(String roomId, String message, String clientMessageID) {
        JSONObject jsonObject = ParamSocket.emitMessage(roomId, message, clientMessageID);
        mSocket.emit(SEND_MESSAGE, jsonObject, new Ack() {
            @Override
            public void call(Object... args) {
                Log.e("", "");
            }
        });
    }

    /* ON  RECEIVE A MESSAGE */
    public void onMessage() {
        // ack from client to server
        mSocket.on(RECEIVE_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {

//                    SocketOn socketOn = new SocketOn(0, args);

                    ChatData chatData = new Gson().fromJson(args[0].toString(), ChatData.class);
                    ChatData newMessage;
                    if ("2".equals(chatData.getType())) {
                        newMessage = newMessage(2, chatData);
                    } else {
                        newMessage = newMessage(0, chatData);
                    }
                    mListChat.add(newMessage);
//                    mChatAdapter.updateChatList(mListChat, getBinding().recyclerChat);
//                    handleChat("Chào bạn! Bạn mong muốn chúng tôi hỗ trợ về vấn đề gì?", getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Chatbot", true);

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatAdapter.notifyDataSetChanged();
                            getBinding().recyclerChat.smoothScrollToPosition(mListChat.size() - 1);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                handleChat(newMessage);
            }
        });
    }

    /* ON  RECEIVE A MESSAGE */
    public void onNotReplyMessage() {
        // ack from client to server
        mSocket.on(NOT_REPLY_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    SocketOn socketOn = new SocketOn(1, args);
                    ChatData chatData = new Gson().fromJson(args[0].toString(), ChatData.class);
                    ChatData newMessage = newMessage(socketOn.getI(), chatData);
                    mListChat.add(newMessage);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatAdapter.notifyDataSetChanged();
                            getBinding().recyclerChat.smoothScrollToPosition(mListChat.size() - 1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /* ON  RECEIVE A MESSAGE */
    public void onAdminSendSurvey() {
        // ack from client to server
        mSocket.on(ADMIN_SEND_SURVEY, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ReviewChatActivity.startScreen(mActivity);
                cancelTimer();
            }
        });
    }

    /* NEW MESSAGE */
    private ChatData newMessage(int type, ChatData chatData) {
//        ChatData chatData = new Gson().fromJson(args[0].toString(), ChatData.class);
        return updateMessageByType(chatData, type);
    }

    /*Update massage by type*/
    private ChatData updateMessageByType(ChatData message, int type) {
        // 0 is on receive message
        // 1 is typing message
        String accountIdUser = MyApplication.getInstance().getRoom().getMemberId();
        switch (type) {
            case 0:
                if (isSameUser(accountIdUser, message)) {
                    message.setOwner(true);
                    message.setType(Constants.TypeChat.USER_SOCKET);
                    message.setCurrent_time(getCurrentTime());
                } else {
                    message.setOwner(false);
                    message.setType(Constants.TypeChat.ADMIN);
                    message.setCurrent_time(getCurrentTime());
                }
                break;
            case 1:
                message.setOwner(false);
                message.setType(Constants.TypeChat.NOT_REPLY);

                ChatData.Sender sender = new ChatData.Sender();
                sender.setFull_name("Tổng đài viên - VTVTravel");
                message.setSender(new ChatData.Sender());
                message.setCurrent_time(getCurrentTime());
                break;
            case 2:
                message.setOwner(false);
                message.setType(Constants.TypeChat.DEFECT_TEXT);

                ChatData.Sender sender2 = new ChatData.Sender();
                sender2.setFull_name("Tổng đài viên - VTVTravel");
                message.setSender(sender2);
                message.setCurrent_time(getCurrentTime());
                break;
            default:
                break;
        }
        return message;

    }

    /*CHECK SAME USER ID*/
    private boolean isSameUser(String idCurrentUser, ChatData message) {
        try {
            return idCurrentUser.equals(message.getSender().getAdminId());
        } catch (Exception e) {
            return idCurrentUser.equals(message.getRoomId());
        }
    }


    @Subscribe
    public void onReviewSuccess(OnReviewSuccess onReviewSuccess) {
        String mess = "Tạm biệt Quý khách, VTVTravel luôn mong muốn được đồng hành cùng bạn trong thời gian tới. Xin cảm ơn!";
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin() && account.getFullname() != null && !"".equals(account.getFullname())) {
            mess = "Tạm biệt " + account.getFullname() + ", VTVTravel luôn mong muốn được đồng hành cùng bạn trong thời gian tới. Xin cảm ơn!";
        }
        handleChat(mess, getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Chatbot", true);


        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new OnReOpenChatScreen());
//                mActivity.finish();
            }
        }, 10000);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (isAlreadyCall) {
            isAlreadyCall = false;
            startCountDown(60000);
        }
    }
}
