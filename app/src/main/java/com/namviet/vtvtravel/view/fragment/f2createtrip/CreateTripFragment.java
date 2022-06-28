package com.namviet.vtvtravel.view.fragment.f2createtrip;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.Utils;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentCreateTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.f2.MyTripActivity;
import com.namviet.vtvtravel.view.fragment.f2createtrip.dialog.BottomSheetPassengerDialog;
import com.namviet.vtvtravel.view.fragment.f2createtrip.viewmodel.CreateTripViewModel;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.BodyCreateTrip;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.CreateScheduleResponse;
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionMainFragment;
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.AlreadyReceiverDialog;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;


public class CreateTripFragment extends BaseFragment<F2FragmentCreateTripBinding> implements ChooseRegionMainFragment.ChooseRegion, Observer {
    private String link = WSConfig.HOST_CREATE_TRIP;
    private ArrayList<Location> locationList = new ArrayList<>();
    private SearchBigLocationViewModel locationViewModel;
    private CreateTripViewModel myTripsViewModel;
    Map<String, String> extraHeaders = new HashMap<>();
    private int numAdult = 1;
    private int numChildren = 0;
    private int numBaby = 0;
    private String checkinPlaceId;
    private String checkoutPlaceId;
    private long startAtTimestamp;
    private long endAtTimestamp;

    private OnBackToTripsFragment onBackToTripsFragment;

    public void setOnBackToTripsFragment(OnBackToTripsFragment onBackToTripsFragment) {
        this.onBackToTripsFragment = onBackToTripsFragment;
    }

    public CreateTripFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_create_trip;
    }

    @androidx.annotation.Nullable
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable @Nullable ViewGroup container, @androidx.annotation.Nullable @Nullable Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.ActionBarTheme, true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView() {
        locationViewModel = new SearchBigLocationViewModel();
        myTripsViewModel = new CreateTripViewModel();
        locationViewModel.addObserver(this);
        locationViewModel.getAllLocation();
        myTripsViewModel.addObserver(this);

    }

    @Override
    public void initData() {
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().edtStartPlace.setOnClickListener(v -> {
            if (locationList.size() > 0) {
                ChooseRegionMainFragment chooseRegionMainFragment = new ChooseRegionMainFragment();
                chooseRegionMainFragment.setData(locationList, CreateTripFragment.this);
                addFragment(chooseRegionMainFragment);
            }
        });
        getBinding().edtDestination.setOnClickListener(v -> {
            if (locationList.size() > 0) {
                ChooseRegionMainFragment chooseRegionMainFragment = new ChooseRegionMainFragment();
                chooseRegionMainFragment.setData(locationList, new ChooseRegionMainFragment.ChooseRegion() {
                    @Override
                    public void clickRegion(@Nullable Location location) {
                        if (location != null) {
                            checkoutPlaceId = location.getId();
                            getBinding().edtDestination.setText(location.getName());
                        }
                    }
                });
                addFragment(chooseRegionMainFragment);
            }
        });
        getBinding().tripInday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getBinding().edtReturnDate.setVisibility(isChecked?View.GONE:View.VISIBLE);
                getBinding().returnDay.setVisibility(isChecked?View.GONE:View.VISIBLE);
                if (isChecked){
                    if (startAtTimestamp != 0) {
                        endAtTimestamp = startAtTimestamp;
                        getBinding().edtReturnDate.setText(Utils.formatTimestampTrips(endAtTimestamp));
                    }
                }
            }
        });
        Calendar calendar = Calendar.getInstance();
        getBinding().edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedYear = calendar.get(Calendar.YEAR);
                int selectedMonth = calendar.get(Calendar.MONTH);
                int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                Calendar minCalendar = Calendar.getInstance(TimeZone.getDefault());
                minCalendar.add(Calendar.DATE,1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR,year);
                                calendar.set(Calendar.MONTH,month);
                                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                startAtTimestamp = calendar.getTimeInMillis();
                                if (getBinding().tripInday.isChecked()){
                                    endAtTimestamp = startAtTimestamp;
                                }
                                getBinding().edtStartDate.setText(Utils.formatTimestampTrips(calendar.getTimeInMillis()));
                            }
                        }, selectedYear, selectedMonth, selectedDayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        getBinding().edtReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedYear = calendar.get(Calendar.YEAR);
                int selectedMonth = calendar.get(Calendar.MONTH);
                int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR,year);
                                calendar.set(Calendar.MONTH,month);
                                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                endAtTimestamp = calendar.getTimeInMillis();
                                getBinding().edtReturnDate.setText(Utils.formatTimestampTrips(calendar.getTimeInMillis()));
                            }
                        }, selectedYear, selectedMonth, selectedDayOfMonth);
                if (startAtTimestamp > 0){
                    Date date = new Date(startAtTimestamp);
                    Calendar minCalendarReturn = Calendar.getInstance(TimeZone.getDefault(),Locale.getDefault());
                    minCalendarReturn.setTime(date);
                    minCalendarReturn.add(Calendar.DATE,1);
                    datePickerDialog.getDatePicker().setMinDate(minCalendarReturn.getTimeInMillis());
                }

                datePickerDialog.show();
            }
        });

        getBinding().edtAmountPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetPassengerDialog passengerDialog = new BottomSheetPassengerDialog(getContext());
                passengerDialog.setInfor(numAdult,numChildren,numBaby);
                passengerDialog.setApplyListener(new BottomSheetPassengerDialog.ApplyPassengerListener() {
                    @Override
                    public void onApplyClick(int numAdult, int numChildren, int numBaby) {
                        CreateTripFragment.this.numAdult = numAdult;
                        CreateTripFragment.this.numChildren = numChildren;
                        CreateTripFragment.this.numBaby = numBaby;
                        String amount = getContext().getString(R.string.number_passenger,numAdult,numChildren,numBaby);
                        getBinding().edtAmountPeople.setText(amount);
                    }
                });
                passengerDialog.show(requireFragmentManager(),passengerDialog.getTag());
            }
        });
        getBinding().btnScheduleTrip.setOnClickListener(v -> {
            BodyCreateTrip body = initBodyCreateTrip();
            if (validateCreate()) {
                if (!myTripsViewModel.isLoading)
                myTripsViewModel.createScheduleTrip(body);
            }
        });
        getBinding().imvBack.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }

    private BodyCreateTrip initBodyCreateTrip(){
        String des = getBinding().edtTripDesc.getText().toString();
        String name = getBinding().edtTripName.getText().toString();
        String startAtString = Utils.formatTimeStamp(startAtTimestamp,"yyyy-MM-dd");
        String endAtString = Utils.formatTimeStamp(endAtTimestamp,"yyyy-MM-dd");
        BodyCreateTrip body = new BodyCreateTrip();
        body.setName(name);
        body.setDescription(des);
        body.setPlaceCheckInId(checkinPlaceId);
        body.setPlaceCheckOutId(checkoutPlaceId);
        body.setAdults(numAdult);
        body.setChildren(numChildren);
        body.setInfant(numBaby);
        body.setStartAt(startAtString);
        body.setEndAt(endAtString);
        body.setType("1");
        return body;
    }

    @Override
    public void setObserver() {

    }

    private String genLink() {
        try {
            Double lat = null;
            Double lng = null;
            if (null != MyApplication.getInstance().getMyLocation()
                    && MyApplication.getInstance().getMyLocation().getLat() != MyApplication.getInstance().getMyLocation().getLog()) {
                lat = MyApplication.getInstance().getMyLocation().getLat();
                lng = MyApplication.getInstance().getMyLocation().getLog();
            }
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin() && lat != null && lng != null) {
                extraHeaders.put("token",account.getToken());
                return link + "?lat=" + lat + "&long=" + lng + "&backLink=" + "true";
            } else if (null != account && account.isLogin()) {
                extraHeaders.put("token",account.getToken());
                return link + "?backLink=" + "true";
            } else {
                return link + "?backLink=" + "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return link;
        }
    }

    private boolean validateCreate(){
        AlreadyReceiverDialog dialog = new AlreadyReceiverDialog();
        dialog.setDialogTitle(getString(R.string.error_title));
        dialog.setLabelButton(getString(R.string.close_title));
        if (ValidateUtils.isEmptyEdittext(getBinding().edtTripName)){
            showErrorDialog(dialog,"Tên chuyến đi không được để trống");
            return false;
        } else if (ValidateUtils.isEmptyEdittext(getBinding().edtTripDesc)){
            showErrorDialog(dialog,"Mô tả không được để trống");
            return false;
        } else if (ValidateUtils.isEmptyTextview(getBinding().edtStartPlace)){
            showErrorDialog(dialog, "Bạn chưa chọn nơi đi");
            return false;
        } else if (ValidateUtils.isEmptyTextview(getBinding().edtDestination)){
            showErrorDialog(dialog,"Bạn chưa chọn nơi đến");
            return false;
        } else if (ValidateUtils.isEmptyTextview(getBinding().edtStartDate)){
            showErrorDialog(dialog, "Bạn chưa chọn ngày đi");
            return false;
        } else if (ValidateUtils.isEmptyTextview(getBinding().edtReturnDate) && !getBinding().tripInday.isChecked()){
            showErrorDialog(dialog, "Bạn chưa chọn ngày về");
            return false;
        } else if (ValidateUtils.isEmptyTextview(getBinding().edtAmountPeople)) {
            showErrorDialog(dialog, "Bạn chưa chọn số lượng hành khách");
            return false;
        }
        return true;
    }

    private void showErrorDialog(AlreadyReceiverDialog dialog,String description){
        dialog.setDescription(description);
        dialog.show(getChildFragmentManager(),null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void clickRegion(@Nullable Location location) {
        if (location != null) {
            checkinPlaceId = location.getId();
            getBinding().edtStartPlace.setText(location.getName());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SearchBigLocationViewModel) {
            if (arg instanceof AllLocationResponse) {
                AllLocationResponse locationResponse = (AllLocationResponse) arg;
                if (locationResponse.getData() != null)
                    locationList = (ArrayList<Location>) locationResponse.getData();
            }
        } else if (o instanceof CreateTripViewModel){
            if (arg instanceof CreateScheduleResponse){
                CreateScheduleResponse dataCreateTrips = (CreateScheduleResponse) arg;
                if (dataCreateTrips.getDataCreateTrips() != null){
                    if (onBackToTripsFragment != null)
                    onBackToTripsFragment.backToMainTrips();
                   getActivity().onBackPressed();
                }
                if (onBackToTripsFragment == null){
                    MyTripActivity.startScreen(getContext());
                    getActivity().finish();
                }

            }
        }
    }

    public interface OnBackToTripsFragment{
        void backToMainTrips();
    }
}
