package com.namviet.vtvtravel.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.datetimepicker.date.DatePickerDialog;
import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailScheduleBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.DetailScheduleResponse;
import com.namviet.vtvtravel.response.SaveScheduleResponse;
import com.namviet.vtvtravel.response.ScheduleResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class DetailScheduleTravelFragment extends MainFragment implements Observer {
    private FragmentDetailScheduleBinding binding;
    private Schedule mSchedule;
    private PlaceViewModel placeViewModel;
    private Calendar calendar;
    private String startTour = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mSchedule = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_schedule, container, false);
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
        binding.toolBar.tvTitle.setText(getString(R.string.detail_suggest_travel));
        binding.toolBar.ivSearch.setVisibility(View.INVISIBLE);
        binding.toolBar.ivSearch.setEnabled(false);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.btDone.setOnClickListener(this);
        binding.tvDatePlan.setOnClickListener(this);
        calendar = Calendar.getInstance();
        updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.btDone) {
            if (null != startTour) {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account) {
                    placeViewModel.saveTourTravel(account.getId(), mSchedule.getId(), startTour,
                            DateUtltils.timeEndTour(startTour, mSchedule.getDuration()), binding.switchNotify.isChecked());
                }
            } else {
                showMessage(getString(R.string.date_start_empty));
            }

        } else if (view == binding.tvDatePlan) {
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                    String day = "" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                    String month = "" + (monthOfYear < 9 ? "0" + (monthOfYear + 1) : monthOfYear + 1);
                    startTour = day + "/" + month + "/" + year;
                    binding.tvDatePlan.setText(startTour);
                    binding.scrollView.scrollTo(0, 10);
                }
            }, year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
            datePickerDialog.setMinDate(calendar);
        }

    }

    @Override
    protected void updateViews() {
        super.updateViews();
        showDialogLoading();
        if (null != mSchedule) {
            binding.tvTitleTour.setText(mSchedule.getName());
            placeViewModel.loadDetailSchedule(mSchedule.getId());
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
                        if (o instanceof DetailScheduleResponse) {
                            DetailScheduleResponse response = (DetailScheduleResponse) o;
                            binding.webContent.loadData(response.getData().getDescription(), "text/html; charset=utf-8", "UTF-8");
                        } else if (o instanceof SaveScheduleResponse) {
                            SaveScheduleResponse response = (SaveScheduleResponse) o;
                            if (response.isSuccess()) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                createSuccess(getString(R.string.create_tour_success), 2);
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
