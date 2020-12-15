package com.namviet.vtvtravel.view.fragment.f2comment;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2DialogComment2Binding;
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom;

import java.util.Objects;

public class OptionCommentDialog extends BaseDialogBottom {
    private F2DialogComment2Binding binding;
    private ClickButton clickButton;



    @Override
    protected int getLayoutResource() {
        return R.layout.f2_dialog_comment2;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_comment2, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        binding.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickCopy();
                dismiss();
            }
        });

        binding.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton.onClickReply();
                dismiss();
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setClickButton(ClickButton clickButton) {
        this.clickButton = clickButton;
    }


    public interface ClickButton{
        public void onClickReply();
        public void onClickCopy();
    }

}
