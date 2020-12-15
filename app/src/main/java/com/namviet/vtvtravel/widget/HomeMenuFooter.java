package com.namviet.vtvtravel.widget;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.namviet.vtvtravel.R;

import java.util.ArrayList;

/**
 * Created by admin on 12/12/2017.
 */

public class HomeMenuFooter extends LinearLayout implements View.OnClickListener {

    private LinearLayout llHot;
    private LinearLayout llSuggest;
    private LinearLayout llMoment;
    private LinearLayout llVideo;
    private LinearLayout llMenu;

    private ImageView ivHot;
    private ImageView ivSuggest;
    private ImageView ivMoment;
    private ImageView ivVideo;
    private ImageView ivMenu;


    private TextView tvHot;
    private TextView tvSuggest;
    private TextView tvMoment;
    private TextView tvVideo;
    private TextView tvMenu;


    private HomeBarBottomClick homeBarBottomClick;
    private ArrayList<ItemFooter> arrItems;
    private int index = 0;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public HomeMenuFooter(Context context) {
        super(context);
        initLayout();
    }

    public HomeMenuFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public HomeMenuFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        inflate(getContext(), R.layout.home_menu_footer, this);

        llHot = findViewById(R.id.ll_footer_hot);
        llSuggest = findViewById(R.id.ll_footer_suggest);
        llMoment = findViewById(R.id.ll_footer_moment);
        llVideo = findViewById(R.id.ll_footer_video);
        llMenu = findViewById(R.id.ll_footer_menu);


        ivHot = findViewById(R.id.iv_footer_hot);
        ivSuggest = findViewById(R.id.iv_footer_suggest);
        ivMoment = findViewById(R.id.iv_footer_moment);
        ivVideo = findViewById(R.id.iv_footer_video);
        ivMenu = findViewById(R.id.iv_footer_menu);


        tvHot = findViewById(R.id.tv_footer_hot);
        tvSuggest = findViewById(R.id.tv_footer_suggest);
        tvMoment = findViewById(R.id.tv_footer_moment);
        tvVideo = findViewById(R.id.tv_footer_video);
        tvMenu = findViewById(R.id.tv_footer_menu);

        arrItems = new ArrayList<>();
        arrItems.add(new ItemFooter(ivHot, tvHot, R.drawable.f2_ic_home_enable, R.drawable.f2_ic_home_disable));
        arrItems.add(new ItemFooter(ivMoment, tvMoment, R.drawable.f2_ic_moment_disable, R.drawable.f2_ic_moment_disable));
        arrItems.add(new ItemFooter(ivSuggest, tvSuggest, R.drawable.f2_ic_booking_disable, R.drawable.f2_ic_booking_disable));
        arrItems.add(new ItemFooter(ivVideo, tvVideo, R.drawable.f2_ic_video_enable, R.drawable.test_ic_video));
        arrItems.add(new ItemFooter(ivMenu, tvMenu, R.drawable.f2_ic_new_menu, R.drawable.f2_ic_new_menu));

        llHot.setOnClickListener(this);
        llSuggest.setOnClickListener(this);
        llMoment.setOnClickListener(this);
        llVideo.setOnClickListener(this);
        llMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == llHot) {
            if (index != 0) {
                if (isLogin()) {
                    if (null != homeBarBottomClick) {
                        homeBarBottomClick.onHotClick(0);
                    }
                    index = 0;
                    changeState(index);
                } else {
                    if (null != homeBarBottomClick) {
                        homeBarBottomClick.onNavigationLogin();
                    }
                }

            }

        } else if (view == llMoment) {

            if (isLogin()) {
                if (index != 1) {
                    if (null != homeBarBottomClick) {
                        homeBarBottomClick.onMomentClick(1);
                    }
                    index = 1;
                    changeState(index);
                }
            } else {
                if (null != homeBarBottomClick) {
                    homeBarBottomClick.onNavigationLogin();
                }
            }


        } else if (view == llSuggest) {
            if (isLogin()) {
                if (index != 2) {
                    if (null != homeBarBottomClick) {
                        homeBarBottomClick.onSuggestClick(2);
                    }
                    index = 2;
                    changeState(index);
                }
            } else {
                if (null != homeBarBottomClick) {
                    homeBarBottomClick.onNavigationLogin();
                }
            }

        } else if (view == llVideo) {
            if (isLogin()) {
                if (index != 3) {
                    if (null != homeBarBottomClick) {
                        homeBarBottomClick.onVideoClick(3);
                    }
                    index = 3;
                    changeState(index);
                }
            } else {
                if (null != homeBarBottomClick) {
                    homeBarBottomClick.onNavigationLogin();
                }
            }
        } else if (view == llMenu) {
            if (isLogin()) {
                if (index != 4) {
                    if (null != homeBarBottomClick) {
                        homeBarBottomClick.onMenuClick(4);
                    }
                    index = 4;
                    changeState(index);
                }
            } else {
                if (null != homeBarBottomClick) {
                    homeBarBottomClick.onNavigationLogin();
                }
            }
        }

    }

    public void setHomeBarBottomOnClick(HomeBarBottomClick homeBarBottomOnClick) {
        this.homeBarBottomClick = homeBarBottomOnClick;
    }

    public interface HomeBarBottomClick {

        void onMenuClick(int position);

        void onHotClick(int position);

        void onSuggestClick(int position);

        void onMomentClick(int position);

        void onVideoClick(int position);

        void onNavigationLogin();
    }

    private void changeState(int index) {
        for (int i = 0; i < arrItems.size(); i++) {
            ItemFooter item = arrItems.get(i);
            if (i == index) {
                item.getIcon().setImageResource(item.getSrcSelected());
                item.getTitle().setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            } else {
                item.getIcon().setImageResource(item.getSrcSelect());
                item.getTitle().setTextColor(ContextCompat.getColor(getContext(), R.color.gray_99));
            }
        }

    }

    class ItemFooter {
        private ImageView icon;
        private TextView title;
        private int srcSelected;
        private int srcSelect;


        public ItemFooter(ImageView icon, TextView title, int srcSelected, int srcSelect) {
            this.icon = icon;
            this.title = title;
            this.srcSelected = srcSelected;
            this.srcSelect = srcSelect;
        }

        public ImageView getIcon() {
            return icon;
        }

        public void setIcon(ImageView icon) {
            this.icon = icon;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public int getSrcSelected() {
            return srcSelected;
        }

        public void setSrcSelected(int srcSelected) {
            this.srcSelected = srcSelected;
        }

        public int getSrcSelect() {
            return srcSelect;
        }

        public void setSrcSelect(int srcSelect) {
            this.srcSelect = srcSelect;
        }
    }

    public void setIndexCurrent(int index) {
        this.index = index;
        changeState(index);
    }

    public int getIndex() {
        return index;
    }
}
