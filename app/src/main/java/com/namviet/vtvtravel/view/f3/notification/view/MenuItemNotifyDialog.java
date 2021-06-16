package com.namviet.vtvtravel.view.f3.notification.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.filter.SortAdapter;
import com.namviet.vtvtravel.databinding.F2DialogSortBinding;
import com.namviet.vtvtravel.databinding.F3DialogMenuItemNotifyBinding;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.List;
import java.util.Objects;

public class MenuItemNotifyDialog extends BaseDialogBottom {
    private F3DialogMenuItemNotifyBinding binding;
    private IMenuItemNotifyClick iMenuItemNotifyClick;

    public void setImenuItemNotifyClick(IMenuItemNotifyClick iMenuItemNotifyClick) {
        this.iMenuItemNotifyClick = iMenuItemNotifyClick;
    }

    @SuppressLint("ValidFragment")

    public MenuItemNotifyDialog() {
    }

    private SortSmallLocationResponse sortSmallLocationResponse;

    @Override
    protected int getLayoutResource() {
        return R.layout.f3_dialog_menu_item_notify;
    }

    @Override
    protected void init(Bundle saveInstanceState, View view) {
        try {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            view.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f3_dialog_menu_item_notify, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.lnlNoteViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMenuItemNotifyClick.onMenuItemClickListener(MenuItem.VIEWED);
            }
        });
        binding.lnlBookmarkNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMenuItemNotifyClick.onMenuItemClickListener(MenuItem.BOOKMARK);
            }
        });
        binding.lnlRemoveNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMenuItemNotifyClick.onMenuItemClickListener(MenuItem.REMOVE);
            }
        });
        binding.lnlCancelNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMenuItemNotifyClick.onMenuItemClickListener(MenuItem.CANCEL);
            }
        });
    }

    public interface IMenuItemNotifyClick{
        void onMenuItemClickListener(MenuItem menuItem);
    }
    public enum MenuItem{
        VIEWED,
        BOOKMARK,
        REMOVE,
        CANCEL;
    }
}
