package com.namviet.vtvtravel.view.fragment;

import android.view.View;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2systeminbox.SystemInboxAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentSystemInboxBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2systeminbox.ConfirmEnterTrip;
import com.namviet.vtvtravel.response.f2systeminbox.DataSystemInbox;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.response.f2systeminbox.TicketInfo;
import com.namviet.vtvtravel.view.dialog.f2.ReceiverTripInviteDialog;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ReceiveInviteTripDetailActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity;
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SystemInboxFragment extends BaseFragment<F2FragmentSystemInboxBinding> implements Observer {
    private SystemInboxViewModel systemInboxViewModel;
    private SystemInboxAdapter systemInboxAdapter;
    private List<SystemInbox.Data.InboxItem> inboxItems = new ArrayList<>();
    private DataSystemInbox dataSystemInbox;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_system_inbox;
    }

    @Override
    public void initView() {
        systemInboxViewModel = new SystemInboxViewModel();
        systemInboxViewModel.addObserver(this);
        systemInboxViewModel.getSystemInbox();
    }

    @Override
    public void initData() {
        systemInboxAdapter = new SystemInboxAdapter(inboxItems, mActivity, new SystemInboxAdapter.ClickItem() {
            @Override
            public void onClickItem(SystemInbox.Data.InboxItem inboxItem) {
                try {
                    systemInboxViewModel.updateSystemInbox(inboxItem.getId());
                    EventBus.getDefault().post(new OnReloadCountSystemInbox());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TravelVoucherActivity.openScreen(mActivity, true, TravelVoucherActivity.OpenType.LIST, true);
            }

            @Override
            public void onClickInvite(SystemInbox.Data.InboxItem inboxItem, DataSystemInbox dataSystemInbox) {
                try {
//                    SystemInboxFragment.this.dataSystemInbox = dataSystemInbox;
//                    ReceiverTripInviteDialog notifyDialog = ReceiverTripInviteDialog.newInstance(dataSystemInbox.getTitle(), dataSystemInbox.getContent(), "Đồng ý tham gia", new ReceiverTripInviteDialog.ClickButton() {
//                        @Override
//                        public void onClickButton() {
//                            try {
//
//                                Account account = MyApplication.getInstance().getAccount();
//                                if (null != account && account.isLogin()) {
//                                    systemInboxViewModel.confirmEnterTrip(dataSystemInbox.getScheduleCustomId(), String.valueOf(account.getId()));
//                                } else {
//                                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//                                }
//
//                                systemInboxViewModel.updateSystemInbox(inboxItem.getId());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
//                    notifyDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClickTicket(SystemInbox.Data.InboxItem inboxItem, DataSystemInbox dataSystemInbox) {
                try {
                    systemInboxViewModel.updateSystemInbox(inboxItem.getId());
                    String ticketId = dataSystemInbox.getTicketID();
                    systemInboxViewModel.ticketInfo(ticketId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, systemInboxViewModel);
        getBinding().rclContent.setAdapter(systemInboxAdapter);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            if (observable instanceof SystemInboxViewModel && null != o) {
                if (o instanceof SystemInbox) {
                    SystemInbox systemInbox = (SystemInbox) o;
                    inboxItems.clear();
                    inboxItems.addAll(systemInbox.getData().getInboxItem());
                    systemInboxAdapter.notifyDataSetChanged();
                } else if (o instanceof ConfirmEnterTrip) {
                    ReceiveInviteTripDetailActivity.startScreen(mActivity, dataSystemInbox.getScheduleCustomId());
                } else if (o instanceof TicketInfo) {
                    try {
                        TicketInfo ticketInfo = (TicketInfo) o;
                        if(ticketInfo.getData().getStatus().equals("0")){
                            VirtualSwitchBoardActivity.Companion.openActivity(mActivity, VirtualSwitchBoardActivity.Companion.getTRAVEL_TYPE());
                        }else {
                            showToast("Bạn đã xử lý ticket này.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (o instanceof BaseResponse) {

                } else if (o instanceof ErrorResponse) {
                    ErrorResponse responseError = (ErrorResponse) o;
                    try {
                        Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
