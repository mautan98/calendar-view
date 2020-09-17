package com.namviet.vtvtravel.view.fragment;

import android.view.View;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2systeminbox.SystemInboxAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSystemInboxBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
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
        });
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
        if (observable instanceof SystemInboxViewModel && null != o) {
            if (o instanceof SystemInbox) {
                SystemInbox systemInbox = (SystemInbox) o;
                inboxItems.clear();
                inboxItems.addAll(systemInbox.getData().getInboxItem());
                systemInboxAdapter.notifyDataSetChanged();
            } else if(o instanceof BaseResponse){

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                    Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        }
    }
}
