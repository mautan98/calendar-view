package com.namviet.vtvtravel.view.fragment.f2livetv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2livetv.ChannelLiveTVDialogAdapter;
import com.namviet.vtvtravel.databinding.F2LayoutBottomSheetLivetvBinding;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.List;
import java.util.Objects;

public class ListChannelDialog extends BaseDialogBottom implements ChannelLiveTVDialogAdapter.ClickButton {
    private F2LayoutBottomSheetLivetvBinding binding;
    private List<LiveTvResponse.Channel> channelList;
    private ChannelLiveTVDialogAdapter channelLiveTVDialogAdapter;
    private ClickChannel clickButton;
    private Context context;

    public ListChannelDialog() {
    }

    @SuppressLint("ValidFragment")
    public ListChannelDialog(Context context, List<LiveTvResponse.Channel> channelList, ClickChannel clickButton) {
        this.channelList = channelList;
        this.clickButton = clickButton;
        this.context = context;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.f2_layout_bottom_sheet_livetv;
    }

    @Override
    protected void init(Bundle saveInstanceState, View view) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void setUp(View view) {
        channelLiveTVDialogAdapter = new ChannelLiveTVDialogAdapter(context, channelList, this);
        binding.rclChannel.setAdapter(channelLiveTVDialogAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) Objects.requireNonNull(getView()).getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getDialog().getWindow() != null) {
            this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_layout_bottom_sheet_livetv, container, false);
        return binding.getRoot();
    }

    @Override
    public void clickChannel(int position) {
        clickButton.clickItem(position);
        dismiss();
    }

    public interface ClickChannel {
        void clickItem(int position);
    }
}
