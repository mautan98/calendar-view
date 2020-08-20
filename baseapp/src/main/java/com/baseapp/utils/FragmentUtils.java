package com.baseapp.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.baseapp.R;
import com.baseapp.menu.SlideMenu;

import java.util.Map;
import java.util.Stack;

/**
 * Created by xuannt on 11/8/2017.
 */

public class FragmentUtils {

    //    Whenever you want to display any new fragment, just push that fragment into stack using following code.
    public static void pushFragment(AppCompatActivity activity, Fragment fragment, int frId, Map<SlideMenu.MenuType, Stack<Fragment>> mStackMap, SlideMenu.MenuType menuType, boolean shouldAdd) {
        if (shouldAdd) {
            mStackMap.get(menuType).push(fragment);
        }
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(frId, fragment, fragment.getClass().getSimpleName());
        ft.commitAllowingStateLoss();
    }

    public static void pushFragmentNoStack(AppCompatActivity activity, Fragment fragment, int frId) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(frId, fragment, fragment.getClass().getSimpleName());
        ft.commitAllowingStateLoss();
    }

    //    Whenever you want to destroy the current fragment and go back to previous fragment, just pop that fragment using following code without sending data to previous fragment
    public static void popFragment(AppCompatActivity activity, int frId, Map<SlideMenu.MenuType, Stack<Fragment>> mStackMap, SlideMenu.MenuType menuType) {
        final Stack<Fragment> stackFragment = mStackMap.get(menuType);
        if (stackFragment.size() > 1) {
            Fragment fragment = stackFragment.elementAt(stackFragment.size() - 2);
            stackFragment.pop();
            if (fragment != null) {
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.replace(frId, fragment, fragment.getClass().getSimpleName());
                ft.commitAllowingStateLoss();
            }
        }
    }

    //    Here you can pop a fragment and send data to previous fragment
    public static void popFragment(AppCompatActivity activity, int frId, Map<SlideMenu.MenuType, Stack<Fragment>> mStackMap, SlideMenu.MenuType menuType, Bundle argBundle) {
        final Stack<Fragment> stackFragment = mStackMap.get(menuType);
        if (stackFragment.size() > 1) {
            Fragment fragment = stackFragment.elementAt(stackFragment.size() - 2);
            stackFragment.pop();

            if (fragment != null) {
                if (argBundle != null) {
                    fragment.setArguments(argBundle);
                }
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                ft.replace(frId, fragment, fragment.getClass().getSimpleName());
                ft.commitAllowingStateLoss();
            }
        }
    }

    public static void backstackFragment(AppCompatActivity activity, int frId) {
        if (activity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            activity.finish();
        }
        activity.getSupportFragmentManager().popBackStack();
        removeCurrentFragment(activity, frId);
    }

    public static void removeCurrentFragment(AppCompatActivity activity, int frId) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Fragment currentFrag = activity.getSupportFragmentManager()
                .findFragmentById(frId);

        if (currentFrag != null) {
            transaction.remove(currentFrag);
        }
        transaction.commitAllowingStateLoss();
    }
}
