package com.namviet.vtvtravel.view.fragment.f2offline;

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
import com.namviet.vtvtravel.databinding.F2FragmentRuleBinding;

import java.io.File;
import java.util.Objects;

public class RuleDialog extends BaseDialogBottom {
    private F2FragmentRuleBinding binding;
    private String offlineDynamic;
    private String parentLink;
    @Override
    protected int getLayoutResource() {
        return R.layout.f2_fragment_rule;
    }

    @Override
    protected void init(Bundle saveInstanceState, View view) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.setBackgroundColor(Color.TRANSPARENT);
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_rule, container, false);
        return binding.getRoot();
    }

    private void setClick(){
        binding.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    protected void setUp(View view) {
        File file = new File(getBaseActivity().getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/"+offlineDynamic);
        binding.webView.loadUrl("file:///" + file);
//        binding.webView.loadDataWithBaseURL(null, "file:///" + file, "text/html", "UTF-8", null);
    }

    public void setOfflineDynamic(String offlineDynamic) {
        this.offlineDynamic = offlineDynamic;
    }
}
