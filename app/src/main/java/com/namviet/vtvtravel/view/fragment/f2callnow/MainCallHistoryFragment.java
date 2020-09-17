package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentMainCallHisstoryBinding;
import com.namviet.vtvtravel.databinding.F2FragmentMainCallNowBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2callnow.CallNowHistory;
import com.namviet.vtvtravel.model.f2event.OnDeleteSuccess;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2callnow.CallNowHistoryResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonDialog;
import com.namviet.vtvtravel.view.fragment.f2offline.TwoButtonDialog;
import com.namviet.vtvtravel.viewmodel.f2callnow.MainCallHistoryViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainCallHistoryFragment extends MainFragment implements Observer {

    private F2FragmentMainCallHisstoryBinding binding;
    private AllCallHistoryFragment allCallHistoryFragment;
    private MissingCallFragment missingCallFragment;
    private MainCallHistoryViewModel mainCallHistoryViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_main_call_hisstory, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        updateViews();
        binding.btnAllCallHistory.setOnClickListener(this);
        binding.btnMissingCall.setOnClickListener(this);

        mainCallHistoryViewModel = new MainCallHistoryViewModel();
        binding.setMainCallHistoryViewModel(mainCallHistoryViewModel);
        mainCallHistoryViewModel.addObserver(this);

        mainCallHistoryViewModel.getCallHistory();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        setClick();
        renderViewPager();
    }

    private void setClick() {
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnDelete.setVisibility(View.VISIBLE);
                binding.btnDone.setVisibility(View.VISIBLE);
                binding.btnEdit.setVisibility(View.GONE);
                setEnableDelete(true);

            }
        });

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnDelete.setVisibility(View.GONE);
                binding.btnDone.setVisibility(View.GONE);

                try {
                    if (callNowHistoryResponse.getData().getContent().size() > 0) {
                        binding.btnEdit.setVisibility(View.VISIBLE);
                    } else {
                        binding.btnEdit.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    binding.btnEdit.setVisibility(View.GONE);
                }

                setEnableDelete(false);
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TwoButtonDialog twoButtonDialog = TwoButtonDialog.newInstance("Bạn có muốn xóa hết danh sách Lịch sử cuộc gọi?", "Hủy", "Xóa", new TwoButtonDialog.ClickButton() {
                    @Override
                    public void onClickButton1() {

                    }

                    @Override
                    public void onClickButton2() {
                        mainCallHistoryViewModel.deleteCallHistory("ALL", new ArrayList<>());
                    }
                });
                twoButtonDialog.show(mActivity.getSupportFragmentManager(), null);
            }
        });
    }

    private void setEnableDelete(boolean isEnable) {
        allCallHistoryFragment.enableDelete(isEnable);
        missingCallFragment.enableDelete(isEnable);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnAllCallHistory:
                binding.vpMainHistoryCall.setCurrentItem(0);
                break;
            case R.id.btnMissingCall:
                binding.vpMainHistoryCall.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    private void renderViewPager() {
        MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());

        allCallHistoryFragment = new AllCallHistoryFragment();
        mainAdapter.addFragment(allCallHistoryFragment, "allCallHistoryFragment");

        missingCallFragment = new MissingCallFragment();
        mainAdapter.addFragment(missingCallFragment, "missingCallFragment");

        binding.vpMainHistoryCall.setAdapter(mainAdapter);

        binding.vpMainHistoryCall.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (position == 0) {
//                    btnAllCallHistory();
//                } else {
//                    btnMissingCall();
//                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnAllCallHistory();
                } else {
                    btnMissingCall();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//
    }

    private void btnAllCallHistory() {
        binding.btnAllCallHistory.setTextColor((Color.parseColor("#000000")));
        binding.btnMissingCall.setTextColor((Color.parseColor("#757575")));
        binding.btnAllCallHistory.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_selected_tab_history));
        binding.btnMissingCall.setBackgroundColor((Color.parseColor("#FFFFFF")));

        try {
            if (callNowHistoryResponse.getData().getContent().size() <= 0) {
                Toast.makeText(mActivity, "Không có lịch sử cuộc gọi", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }

    }

    private void btnMissingCall() {
        binding.btnAllCallHistory.setTextColor((Color.parseColor("#757575")));
        binding.btnMissingCall.setTextColor((Color.parseColor("#000000")));
        binding.btnMissingCall.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.f2_bg_selected_tab_history));
        binding.btnAllCallHistory.setBackgroundColor((Color.parseColor("#FFFFFF")));

        try {
            if (callNowHistoriesMissingCall.size() <= 0) {
                Toast.makeText(mActivity, "Không có lịch sử cuộc gọi nhỡ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private CallNowHistoryResponse callNowHistoryResponse;
    private ArrayList<CallNowHistory> callNowHistoriesMissingCall;

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        try {
            if (observable instanceof MainCallHistoryViewModel && null != o) {
                if (o instanceof CallNowHistoryResponse) {
                    callNowHistoryResponse = (CallNowHistoryResponse) o;
                    if (callNowHistoryResponse.getData().getContent().size() > 0) {
                        if(binding.btnDone.getVisibility() == View.GONE) {
                            binding.btnEdit.setVisibility(View.VISIBLE);
                        }else {
                            binding.btnEdit.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(mActivity, "Chưa có lịch sử cuộc gọi", Toast.LENGTH_SHORT).show();
                    }
                    allCallHistoryFragment.setData(callNowHistoryResponse.getData().getContent());

                    callNowHistoriesMissingCall = new ArrayList<>();
                    try {
                        Account account = MyApplication.getInstance().getAccount();
                        for (int i = 0; i < callNowHistoryResponse.getData().getContent().size(); i++) {
                            if (callNowHistoryResponse.getData().getContent().get(i).getPrice() == null && callNowHistoryResponse.getData().getContent().get(i).getReceiver().equals("84" + account.getMobile().substring(2))) {
                                callNowHistoriesMissingCall.add(callNowHistoryResponse.getData().getContent().get(i));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    missingCallFragment.setData(callNowHistoriesMissingCall);

                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            }
        } catch (Exception e) {

        }
    }


    @Subscribe
    public void onDeleteItemSuccess(OnDeleteSuccess onDeleteSuccess) {
        mainCallHistoryViewModel.getCallHistory();
    }

}
