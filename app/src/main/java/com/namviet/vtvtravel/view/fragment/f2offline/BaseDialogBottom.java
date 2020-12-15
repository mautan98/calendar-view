package com.namviet.vtvtravel.view.fragment.f2offline;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.View;

public abstract class BaseDialogBottom extends BottomSheetDialogFragment {
    private Context mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof BaseActivity) {
//            BaseActivity mActivity = (BaseActivity) context;
//            this.mActivity = mActivity;
//        }
        this.mActivity = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(getLayoutResource(), null);
//        if (this.getDialog().getWindow() != null) {
//            this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//        init(savedInstanceState, rootView);
//        return rootView;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            setUp(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected abstract int getLayoutResource();

    protected abstract void init(Bundle saveInstanceState, View view);

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public Context getBaseActivity() {
        return mActivity;
    }

}
