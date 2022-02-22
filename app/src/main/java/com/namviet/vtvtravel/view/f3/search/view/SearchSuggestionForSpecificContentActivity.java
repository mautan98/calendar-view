package com.namviet.vtvtravel.view.f3.search.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ActivitySearchSuggestionForSpecificContentBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class SearchSuggestionForSpecificContentActivity extends BaseActivityNew<ActivitySearchSuggestionForSpecificContentBinding> {
    public static class Type{
        public static String VIDEO = "videos";
        public static String NEWS = "news";
        public static String IMAGE = "image";
    }
    private String keyword;
    private String type;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_search_suggestion_for_specific_content;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        keyword = getIntent().getStringExtra("keyword");
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new SearchSuggestionForSpecificContentFragment(keyword, type);
    }

    public static void openScreen(Context context, String keyword, String type){
        Intent intent = new Intent(context, SearchSuggestionForSpecificContentActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void afterSetContentView() {
        super.afterSetContentView();
        try {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
