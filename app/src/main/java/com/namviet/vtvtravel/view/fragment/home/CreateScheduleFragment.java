package com.namviet.vtvtravel.view.fragment.home;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.GroupScheduleAdapter;
import com.namviet.vtvtravel.adapter.SchedulePageAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentCreateScheduleBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.GroupSchedule;
import com.namviet.vtvtravel.response.SaveScheduleResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CreateScheduleFragment extends MainFragment implements Observer {
    private FragmentCreateScheduleBinding binding;
    private SchedulePageAdapter schedulePageAdapter;
    private ArrayList<Fragment> fragmentList;
    private ScheduleEatFragment scheduleEatFragment;
    private ScheduleShoppingFragment scheduleShoppingFragment;
    private SchedulePlayFragment schedulePlayFragment;
    private ArrayList<GroupSchedule> groupScheduleList = new ArrayList<>();
    private GroupScheduleAdapter groupScheduleAdapter;
    private PlaceViewModel placeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_schedule, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
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
        placeViewModel = new PlaceViewModel(getContext());
        binding.setPlaceViewModel(placeViewModel);
        placeViewModel.addObserver(this);
        binding.toolBar.tvTitle.setText(getString(R.string.create_schedule_travel));
        binding.toolBar.ivSearch.setVisibility(View.GONE);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.tbScheduleEat.setOnClickListener(this);
        binding.tbScheduleShopping.setOnClickListener(this);
        binding.tbSchedulePlay.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.tvAddMore.setOnClickListener(this);

        binding.rvScheduleSelect.setLayoutManager(new LinearLayoutManager(getContext()));
        scheduleEatFragment = ScheduleEatFragment.newInstance();
        schedulePlayFragment = SchedulePlayFragment.newInstance();
        scheduleShoppingFragment = ScheduleShoppingFragment.newInstance();
        fragmentList = new ArrayList<>();
        fragmentList.add(scheduleEatFragment);
        fragmentList.add(scheduleShoppingFragment);
        fragmentList.add(schedulePlayFragment);

        schedulePageAdapter = new SchedulePageAdapter(getChildFragmentManager(), fragmentList);
        binding.vpSchedule.setAdapter(schedulePageAdapter);
        binding.vpSchedule.setOffscreenPageLimit(3);
        binding.vpSchedule.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvNext) {
            int current = binding.vpSchedule.getCurrentItem();
            if (current == 2) {
                if (scheduleEatFragment.getScheduleSelected().size() == 0 && scheduleShoppingFragment.getScheduleSelected().size() == 0 && schedulePlayFragment.getScheduleSelected().size() == 0) {
                    showMessage(getString(R.string.schedule_empty));
                } else {
                    groupScheduleList.clear();
                    if (scheduleEatFragment.getScheduleSelected().size() > 0) {
                        groupScheduleList.add(new GroupSchedule("??n u???ng", scheduleEatFragment.getScheduleSelected(), Constants.TypeSchedule.RESTAURANTS));
                    }
                    if (scheduleShoppingFragment.getScheduleSelected().size() > 0) {
                        groupScheduleList.add(new GroupSchedule("Th??m quan mua s???m", scheduleShoppingFragment.getScheduleSelected(), Constants.TypeSchedule.HOTEL));
                    }
                    if (schedulePlayFragment.getScheduleSelected().size() > 0) {
                        groupScheduleList.add(new GroupSchedule("Vui ch??i gi???i tr??", schedulePlayFragment.getScheduleSelected(), Constants.TypeSchedule.CENTERS));
                    }
                    binding.toolBar.tvTitle.setText(getString(R.string.my_schedule_travel));
                    binding.vpSchedule.setVisibility(View.INVISIBLE);
                    binding.rlTabs.setVisibility(View.INVISIBLE);
                    binding.tvNext.setVisibility(View.GONE);
                    binding.llSave.setVisibility(View.VISIBLE);
                    binding.rvScheduleSelect.setVisibility(View.VISIBLE);
                    groupScheduleAdapter = new GroupScheduleAdapter(mActivity, groupScheduleList);
                    binding.rvScheduleSelect.setAdapter(groupScheduleAdapter);

                }
            } else {
                binding.vpSchedule.setCurrentItem(current + 1);
            }
        } else if (view == binding.tbScheduleEat) {
            binding.vpSchedule.setCurrentItem(0);
        } else if (view == binding.tbScheduleShopping) {
            binding.vpSchedule.setCurrentItem(1);
        } else if (view == binding.tbSchedulePlay) {
            binding.vpSchedule.setCurrentItem(2);
        } else if (view == binding.tvAddMore) {
            binding.vpSchedule.setVisibility(View.VISIBLE);
            binding.rlTabs.setVisibility(View.VISIBLE);
            binding.tvNext.setVisibility(View.VISIBLE);
            binding.llSave.setVisibility(View.GONE);
            binding.rvScheduleSelect.setVisibility(View.GONE);
        } else if (view == binding.tvSave) {
            showDialogLoading();
            Account account = MyApplication.getInstance().getAccount();
            placeViewModel.saveScheduleTravel(account.getId(), groupScheduleAdapter.getGroupSchedules());
        }

    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    private void changeCurrentTab(int position) {
        if (position == 0) {
            binding.tbScheduleEat.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_bg_tv_notify));
            binding.arrowEat.setVisibility(View.VISIBLE);
            binding.arrowShopping.setVisibility(View.GONE);

            binding.tbScheduleShopping.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.tbSchedulePlay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            binding.tbScheduleShopping.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_33));
            binding.tbSchedulePlay.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_33));
        } else if (position == 1) {
            binding.tbScheduleEat.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_bg_tv_notify));
            binding.arrowEat.setVisibility(View.VISIBLE);
            binding.arrowShopping.setVisibility(View.VISIBLE);

            binding.tbScheduleShopping.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_bg_tv_notify));
            binding.tbScheduleShopping.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

            binding.tbSchedulePlay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            binding.tbSchedulePlay.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_33));
        } else {
            binding.tbScheduleShopping.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_bg_tv_notify));
            binding.tbScheduleShopping.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

            binding.tbSchedulePlay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_bg_tv_notify));
            binding.tbSchedulePlay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

            binding.arrowShopping.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (o instanceof SaveScheduleResponse) {
                            SaveScheduleResponse response = (SaveScheduleResponse) o;
                            if (response.isSuccess()) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                createSuccess(getString(R.string.create_schedule_success), 1);
                            } else {
                                showMessage(response.getMessage());
                            }
                        }
                    }
                });

            } else {
            }
        }
    }


}
