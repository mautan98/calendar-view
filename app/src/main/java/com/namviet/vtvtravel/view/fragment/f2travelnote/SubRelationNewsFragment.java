package com.namviet.vtvtravel.view.fragment.f2travelnote;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.FragmentRelationNewsBinding;
import com.namviet.vtvtravel.databinding.FragmentSubRelationNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

import java.util.List;

public class SubRelationNewsFragment extends BaseFragment<FragmentSubRelationNewsBinding> {
    private String contentLink;

    public SubRelationNewsFragment(){

    }



    @Override
    public int getLayoutRes() {
        return R.layout.fragment_relation_news;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }
}
