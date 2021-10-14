package com.namviet.vtvtravel.view.f3.deal.adapter.dealdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.namviet.vtvtravel.R;

import java.util.List;

public class InfomationDealAdapter extends PagerAdapter {
    private Context context;
    private List<String> urls;

    public InfomationDealAdapter(Context context, List<String> url) {
        this.context = context;
        this.urls = url;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_information_deal, null);
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadDataWithBaseURL("", urls.get(position), "text/html", "UTF-8", null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
   //     container.removeView((View) object);
    }

    @Override
    public int getCount() {
        try {
            return urls.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Thông tin";
            case 1:
                return "Thể Lệ";
            case 2:
                return "Điều kiện sử dụng";
        }
        return "";
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
