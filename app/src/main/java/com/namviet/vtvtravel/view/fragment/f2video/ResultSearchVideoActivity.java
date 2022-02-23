package com.namviet.vtvtravel.view.fragment.f2video;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ActivitySearchResultContentSpecificBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse;

public class ResultSearchVideoActivity extends BaseActivityNew<ActivitySearchResultContentSpecificBinding> {
    private String keyword;
    private String regionID;
    private SearchSuggestionResponse.Data.Item item;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_search_result_content_specific;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        keyword = getIntent().getStringExtra("keyword");
        regionID = getIntent().getStringExtra("regionID");
        item = (SearchSuggestionResponse.Data.Item) getIntent().getSerializableExtra("searchSuggestion");
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        ResultSearchVideoFragment resultSearchVideoFragment = new ResultSearchVideoFragment();
        resultSearchVideoFragment.setData(keyword, regionID, item == null? null : item.getCategoryCode());
        return resultSearchVideoFragment;
    }

    public static void openScreen(Context context, String keyword, String regionID, SearchSuggestionResponse.Data.Item item){
        Intent intent = new Intent(context, ResultSearchVideoActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("regionID", regionID);
        intent.putExtra("searchSuggestion", item);
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
