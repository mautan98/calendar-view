package com.namviet.vtvtravel.view.fragment;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ChatAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentChatBinding;
import com.namviet.vtvtravel.help.SocketOn;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.chat.ChatData;
import com.namviet.vtvtravel.response.ChatResponse;
import com.namviet.vtvtravel.response.CreateRoomResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.socket.SocketManager;
import com.namviet.vtvtravel.viewmodel.ChatViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class ChatFragment extends MainFragment implements Observer, ChatAdapter.ChatListener {
    FragmentChatBinding binding;

    private ChatViewModel mChatViewModel;
    private ChatAdapter mChatAdapter;
    private List<ChatData> mListChat = new ArrayList<>();
    private CountDownTimer mTimer60s;
    private CountDownTimer mTimer30s;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (MyApplication.getInstance().ismIsFirstChat()) {
            MyApplication.getInstance().setmIsFirstChat(false);
        }
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.tvTitle.setText(getString(R.string.chat));
        binding.btnSend.setOnClickListener(this);

        mChatAdapter = new ChatAdapter(mActivity, mListChat, this);
        binding.recyclerChat.setAdapter(mChatAdapter);
        mChatViewModel = new ChatViewModel();
        binding.setChatViewModel(mChatViewModel);
        mChatViewModel.addObserver(this);

        initTextChangeListener();

        if (MyApplication.getInstance().getmChatDatas().size() > 0) {
            mListChat = MyApplication.getInstance().getmChatDatas();
            mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
        } else {
            firstMessageToServer("hi");
//            String mess = "Welcome Quý khách to VTV Travel. Xin chào a/c, VTV Travel rất mong được hỗ trợ.";
//            Account account = MyApplication.getInstance().getAccount();
//            if (null != account && account.isLogin()) {
//                mess = "Welcome " + account.getFullname() + " to VTV Travel. Xin chào a/c, VTV Travel rất mong được hỗ trợ.";
//            }
//            handleChat(mess, getCurrentTime(), Constants.TypeChat.USER_MESSAGE);
        }

//        try {
////            onReceive();
//            SocketManager.mSocket.connect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void firstMessageToServer(String mess) {
        mChatViewModel.loadItemChat(mess, MyApplication.getSessionId(), getToken());
    }

    private String getToken() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            return account.getToken();
        } else {
            return "";
        }
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
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivBack:
                try {
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mActivity.onBackPressed();
                break;
            case R.id.btn_send:
                cancelTimer60s();
                cancelTimer30s();
                //get Text input
                String mess = binding.edtTypeMessage.getText().toString();

                if (MyApplication.getInstance().isChatBot()) {
                    mChatViewModel.loadItemChat(mess, MyApplication.getSessionId(), getToken());
                    handleChat(mess, getCurrentTime(), Constants.TypeChat.USER_MESSAGE);
                    if (MyApplication.getInstance().isFirstTimeApp()) {
                        startCountDown();
                        MyApplication.getInstance().setFirstTimeApp(false);
                    }
                } else {
                    if (MyApplication.getInstance().getRoom() != null) {
                        String roomId = MyApplication.getInstance().getRoom().getRoom().getId();
                        String uuid = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
                        String clientMessageId = "android" + uuid;
                        SocketManager.emitMessage(roomId, mess, clientMessageId);
//                        handleChatV2(mess, Constants.TypeChat.);
                    }
                }
//                handleChat(mess, getCurrentTime(), Constants.TypeChat.USER_MESSAGE);
                binding.edtTypeMessage.setText("");
                break;
            default:
                break;
        }
    }

    private void initTextChangeListener() {
        binding.edtTypeMessage.addTextChangedListener(new TextWatcher() {
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
                } else {
                    disableSend();
                }
            }
        });
    }

    private void enableSend() {
        binding.btnSend.setEnabled(true);
        binding.btnSend.setImageResource(R.drawable.vt_send_green);
        binding.btnSend.setAlpha(1f);
    }

    private void disableSend() {
        binding.btnSend.setAlpha(0.5f);
        binding.btnSend.setEnabled(false);
        binding.btnSend.setImageResource(R.drawable.vt_send_gray);
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
                        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
                    } else {
                        chatResponse2.getData().setCurrent_time(getCurrentTime());

                        String mess = "Welcome Quý khách to VTVTravel. Xin chào a/c, VTVTravel rất mong được hỗ trợ.";
                        Account account = MyApplication.getInstance().getAccount();
                        if (null != account && account.isLogin()) {
                            mess = "Welcome " + account.getFullname() + " to VTVTravel. Xin chào a/c, VTVTravel rất mong được hỗ trợ.";
                        }

                        chatResponse2.getData().setText(mess);
                        mListChat.add(chatResponse2.getData());
                        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
                    }

                }
            } else if (arg instanceof CreateRoomResponse) {
                CreateRoomResponse createRoomResponse = (CreateRoomResponse) arg;
                SocketManager.initSocket(createRoomResponse.getData().getTokenMemberId());
                MyApplication.getInstance().setRoom(createRoomResponse.getData());
                SocketManager.onMessage();
                SocketManager.onNotReplyMessage();
//                onReceive();
            } else if (arg instanceof ResponseError) {
                ResponseError responseError = (ResponseError) arg;
                showMessage(responseError.getMessage());
            }
        }
    }

    private void startCountDown() {
        mTimer60s = new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                binding.llEditChat.setVisibility(View.GONE);
                try {
                    KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
                } catch (Exception e) {

                }

                String mess = "Cảm ơn Quý khách đã đồng hành cùng VTVTravel. Quý khách có hài lòng với câu trả lời của chúng tôi không?";
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    mess = "Cảm ơn " + account.getFullname() + " đã đồng hành cùng VTVTravel. Quý khách có hài lòng với câu trả lời của chúng tôi không?";
                }

                handleChat(mess, getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Bot", true);

                mTimer30s = new CountDownTimer(30000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        binding.llEditChat.setVisibility(View.VISIBLE);
                        removeLastIndex();
                        String mess = "Cảm ơn Quý khách, VTVTravel luôn mong muốn được đồng hành cùng Quý khách trong thời gian tới. Xin cảm ơn!";
                        Account account = MyApplication.getInstance().getAccount();
                        if (null != account && account.isLogin()) {
                            mess = "Cảm ơn " + account.getFullname() + ", VTVTravel luôn mong muốn được đồng hành cùng Quý khách trong thời gian tới. Xin cảm ơn!";
                        }
                        handleChat(mess, getCurrentTime(), Constants.TypeChat.THANK);
                    }
                }.start();
            }

        }.start();
    }

    private void cancelTimer60s() {
        if (mTimer60s != null) {
            mTimer60s.cancel();
        }
    }

    private void cancelTimer30s() {
        if (mTimer30s != null) {
            mTimer30s.cancel();
        }
    }

    @Override
    public void onDestroy() {
        cancelTimer60s();
        cancelTimer30s();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onSocket(SocketOn socketOn) {
        ChatData newMessage = newMessage(socketOn.getI(), socketOn.getArgs());
        handleChat(newMessage);
    }

    //    @Subscribe
//    public void onReceive(CreateRoomListener createRoomListener) {
//    public void onReceive() {
//        try {
//            SocketManager.onMessage(args -> {
//                Log.d("LamLV", String.valueOf(args));
//
//                ChatData newMessage = newMessage(0, args);
//                handleChat(newMessage);
//
//            });
//            SocketManager.onNotReplyMessage(args -> {
//                if (MyApplication.getInstance().isAdminChated()) {
//                    Log.d("LamLV", String.valueOf(args));
//                    ChatData newMessage = newMessage(1, args);
//                    handleChat(newMessage);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void handleChat(String mess, String time, String type) {
        ChatData userMessage = new ChatData(type, mess, time, getImage());
        mListChat.add(userMessage);
        MyApplication.getInstance().setmChatDatas(mListChat);
        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
    }

    private void handleChat(String mess, String time, String type, String name, boolean check) {
        ChatData.Sender sender = new ChatData.Sender();
        sender.setFull_name(name);
        ChatData userMessage = new ChatData(type, mess, time, getImage(), sender);
        mListChat.add(userMessage);
        MyApplication.getInstance().setmChatDatas(mListChat);
        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
    }

    private void handleChat(String mess, String time, String avatar, String type) {
        ChatData userMessage = new ChatData(type, mess, time, avatar);
        mListChat.add(userMessage);
        MyApplication.getInstance().setmChatDatas(mListChat);
        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
    }

    private void handleChat(ChatData userMessage) {
        mListChat.add(userMessage);
        MyApplication.getInstance().setmChatDatas(mListChat);
        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
    }

    private void removeLastIndex() {
        mListChat.remove(mListChat.size() - 1);
        MyApplication.getInstance().setmChatDatas(mListChat);
        mChatAdapter.updateChatList(mListChat, binding.recyclerChat);
    }

    /* NEW MESSAGE */
    private ChatData newMessage(int type, Object... args) {
        ChatData chatData = new Gson().fromJson(args[0].toString(), ChatData.class);
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

    @Override
    public void after60sYes() {
        cancelTimer30s();
        removeLastIndex();

        String mess = "Cảm ơn Quý khách, VTVTravel luôn mong muốn được đồng hành cùng Quý khách trong thời gian tới. Xin cảm ơn!";
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            mess = "Cảm ơn " + account.getFullname() + ", VTVTravel luôn mong muốn được đồng hành cùng Quý khách trong thời gian tới. Xin cảm ơn!";
        }
        handleChat(mess, getCurrentTime(), Constants.TypeChat.THANK);
        binding.llEditChat.setVisibility(View.VISIBLE);
    }

    @Override
    public void after60sNo() {
        removeLastIndex();
        handleChat("", getCurrentTime(), Constants.TypeChat.OPTION);
    }

    @Override
    public void optionForm() {
        cancelTimer30s();
        removeLastIndex();
        binding.llEditChat.setVisibility(View.VISIBLE);
        mActivity.switchFragment(SlideMenu.MenuType.FORM_CHAT_SCREEN);
    }

    @Override
    public void optionChat() {
        cancelTimer30s();
        removeLastIndex();
        handleChat("Chào Quý khách! Quý khách mong muốn chúng tôi hỗ trợ về vấn đề gì?", getCurrentTime(), Constants.TypeChat.ADMIN_FIRST, "Tổng đài viên - VTVTravel", true);
        binding.llEditChat.setVisibility(View.VISIBLE);
        MyApplication.getInstance().setChatBot(false);
        try {
            String uuid = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
            String username = "anonymous";
            if (MyApplication.getInstance().getAccount() != null && !MyApplication.getInstance().getAccount().getFullname().isEmpty()) {
                username = MyApplication.getInstance().getAccount().getFullname();
            } else if (MyApplication.getInstance().getAccount() != null && !MyApplication.getInstance().getAccount().getMobile().isEmpty()) {
                username = MyApplication.getInstance().getAccount().getMobile();
            }
            mChatViewModel.createRoom(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void option1039() {
        cancelTimer30s();
        removeLastIndex();
        binding.llEditChat.setVisibility(View.VISIBLE);
        call(getString(R.string.calling_address));
    }

    public void call(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, (dialog, id) -> {
                    String phone = "1039";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    mActivity.startActivity(intent);
                })
                .setNegativeButton(R.string.dimiss, (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
