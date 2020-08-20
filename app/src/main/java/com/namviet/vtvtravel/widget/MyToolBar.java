package com.namviet.vtvtravel.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.namviet.vtvtravel.R;


/**
 * Created by xuannt on 11/13/2017.
 */

public class MyToolBar {
    private View rootView;
    private ToolbarListener toolbarListener;
    private ImageView btMenuHome;
    private RelativeLayout rlNotification;
    private TextView tvNotification;

    public MyToolBar(final View rootView, final ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
        this.rootView = rootView;
        if (null != this.rootView) {
            this.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            btMenuHome = rootView.findViewById(R.id.bt_menu_home);
            if (btMenuHome != null) {
                btMenuHome.setClickable(true);
                btMenuHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != toolbarListener) {
                            toolbarListener.menuClick();
                        }
                    }
                });
            }
            rlNotification = rootView.findViewById(R.id.rlNotification);
            if (rlNotification != null) {
                rlNotification.setClickable(true);
                rlNotification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != toolbarListener) {
                            toolbarListener.notificationClick();
                        }
                    }
                });
            }
            tvNotification = rootView.findViewById(R.id.tv_notification);
        }


    }

    public interface ToolbarListener {
        void menuClick();

        void backClick();

        void notificationClick();
    }

    public TextView getTvNotification() {
        return tvNotification;
    }

}
