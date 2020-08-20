package com.baseapp.utils;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.baseapp.R;
import com.baseapp.application.CoreApplication;
import com.baseapp.enity.StackEntry;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by vn on 12/15/2016.
 */

public class MyFragment {

    public static void clearBackStackByPopping(FragmentManager fm) {
        if (null == fm) {
            return;
        }
        try {
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    public static List<Fragment> getCurrentChildrenFragment(FragmentActivity activity) {
        if (null == activity) {
            return null;
        }
        Fragment currentFragment = getCurrentFragment(activity);
        if (null == currentFragment) {
            return null;
        }
        FragmentManager manager = currentFragment.getChildFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        List<Fragment> fragVisibleList = new ArrayList<>();
        if (null != fragmentList) {
            for (int i = fragmentList.size() - 1; i > -1; i--) {
                Fragment aFragmentList = fragmentList.get(i);
                if (aFragmentList != null
                        && !aFragmentList.getClass().getName().equals(currentFragment.getClass().getName())
                        && aFragmentList.isVisible()) {
                    fragVisibleList.add(aFragmentList);
                }

            }
        }
        if (null != fragmentList && 0 < fragmentList.size()) {
            return fragVisibleList;
        }
        return null;
    }

    /**
     * @param activity
     * @return fragment cap 2
     */
    public static Fragment getCurrentChildFragment(FragmentActivity activity) {
        if (null == activity) {
            return null;
        }
        Fragment currentParentFragment = getCurrentFragment(activity);
        if (null == currentParentFragment) {
            return null;
        }
        FragmentManager manager = currentParentFragment.getChildFragmentManager();
        List<Fragment> fragmentChildrenList = manager.getFragments();
        if (null != fragmentChildrenList) {
            for (int i = fragmentChildrenList.size() - 1; i > -1; i--) {
                Fragment fragment = fragmentChildrenList.get(i);
                if (null != fragment && fragment.isVisible()) {
                    return fragment;
                }
            }
        }
        return null;
    }

    public static Fragment getCurrentChildFragment(Fragment fragmentContainer) {
        if (null == fragmentContainer) {
            return null;
        }
        Fragment currentParentFragment = getCurrentFragment(fragmentContainer);
        if (null == currentParentFragment) {
            return null;
        }
        FragmentManager manager = currentParentFragment.getChildFragmentManager();
        List<Fragment> fragmentChildrenList = manager.getFragments();
        if (null != fragmentChildrenList) {
            for (int i = fragmentChildrenList.size() - 1; i > -1; i--) {
                Fragment fragment = fragmentChildrenList.get(i);
                if (null != fragment && fragment.isVisible()) {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * @param activity chua fragment can lay
     * @return fragment hien tai
     */
    public static Fragment getCurrentFragment(FragmentActivity activity) {
        if (null == activity) {
            return null;
        }
        FragmentManager manager = activity.getSupportFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        if (null != fragmentList) {
            for (int i = fragmentList.size() - 1; i > -1; i--) {
                Fragment fragment = fragmentList.get(i);
                if (null != fragment && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    /**
     * @param fragmentParent chua fragment can lay
     * @return fragment hien tai
     */
    public static Fragment getCurrentFragment(Fragment fragmentParent) {
        if (null == fragmentParent) {
            return null;
        }
        FragmentManager manager = fragmentParent.getChildFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        if (null != fragmentList) {
            for (int i = fragmentList.size() - 1; i > -1; i--) {
                Fragment fragment = fragmentList.get(i);
                if (null != fragment && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private static void showFragment(FragmentActivity activity, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragmentList = manager.getFragments();
        if (null != fragmentList) {
            for (Fragment fragment : fragmentList) {
                if (null != fragment) {
                    if (fragment.getClass().getName().equals(tag)) {
                        transaction.show(fragment);
                        L.i("Activity showFragment " + tag);
                    } else {
                        fragment.onPause();
                        transaction.hide(fragment);
                    }
                }
            }
            transaction.commit();
        }

    }

    private static void showFragment(Fragment fragmentParent, String tag) {
        FragmentManager manager = fragmentParent.getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragmentList = manager.getFragments();
        if (null != fragmentList) {
            for (Fragment fragment : fragmentList) {
                if (null != fragment) {
                    if (fragment.getClass().getName().equals(tag)) {
                        transaction.show(fragment);
                        L.i("Fragment showFragment " + tag);
                    } else {
                        fragment.onPause();
                        transaction.hide(fragment);
                    }
                }
            }
            transaction.commit();
        }
    }

    public static void openFragment(FragmentActivity fragmentActivity, int container, Class<? extends Fragment> fragmentClazz, Bundle bundle, Stack<StackEntry> fragmentsStack, boolean isAddToBackStack) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        String tag = fragmentClazz.getName();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        L.d("openFragment tag: " + tag);
        //kieu animation rieng theo man hinh
  /*      if("mkul.namviet.vn.mkul.ui.DetailPlayScreen.DetailPlayFragment".equals(tag)){
            transaction .setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top, R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
        }*/
        try {
            if (!isFragmentAdded(fragmentActivity, tag)) {
                Fragment fragment;
                fragment = fragmentClazz.newInstance();
                L.i("activity open " + tag);
                if (null != bundle) {
                    fragment.setArguments(bundle);
                }
                if (isAddToBackStack) {
                    transaction.addToBackStack(tag);
                }
                Fragment currentFragment = MyFragment.getCurrentFragment(fragmentActivity);
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.add(container, fragment, tag);
                if (CoreApplication.getInstance().isAllowStateLoss()) {
                    new Handler().post(new Runnable() {
                        public void run() {
                            transaction.commitAllowingStateLoss();
                            CoreApplication.getInstance().setAllowStateLoss(false);
                        }
                    });

                } else {
                    transaction.commitAllowingStateLoss();
                }
                L.i("  activity commit" + tag);
                // Put fragment into stack
                putFragmentIntoStack(fragmentsStack, tag);

            } else {
                showFragment(fragmentActivity, tag);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static void openFragment(Fragment fragmentParent, int container, Class<? extends Fragment> fragmentClazz, Bundle bundle, Stack<StackEntry> fragmentsStack, boolean isAddToBackStack) {
        FragmentManager manager = fragmentParent.getChildFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        String tag = fragmentClazz.getName();
        try {
            if (!isFragmentAdded(fragmentParent, tag)) {
                Fragment fragment;
                fragment = fragmentClazz.newInstance();
                L.i("Fragment open " + tag);
                if (null != bundle) {
                    fragment.setArguments(bundle);
                }
                if (isAddToBackStack) {
                    transaction.addToBackStack(tag);
                }
                Fragment currentFragment = MyFragment.getCurrentFragment(fragmentParent);
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.add(container, fragment, tag);
                if (CoreApplication.getInstance().isAllowStateLoss()) {
                    new Handler().post(new Runnable() {
                        public void run() {

                            transaction.commitAllowingStateLoss();
                            CoreApplication.getInstance().setAllowStateLoss(false);
                        }
                    });
                } else {
                    transaction.commitAllowingStateLoss();
//                    transaction.commit();
                }
                L.i("  Fragment commit" + tag);
                // Put fragment into stack
                putFragmentIntoStack(fragmentsStack, tag);

            } else {
                showFragment(fragmentParent, tag);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void openFragment(FragmentActivity fragmentActivity, Fragment fragmentParent, int container, Class<? extends Fragment> fragmentClazz, Bundle bundle, Stack<StackEntry> fragmentsStack, boolean isAddToBackStack) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        String tag = fragmentClazz.getName();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        L.d("openFragment tag: " + tag);
        //kieu animation rieng theo man hinh
  /*      if("mkul.namviet.vn.mkul.ui.DetailPlayScreen.DetailPlayFragment".equals(tag)){
            transaction .setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top, R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
        }*/
        try {
            if (!isFragmentAdded(fragmentActivity, tag)) {
                Fragment fragment;
                fragment = fragmentClazz.newInstance();
                L.i("activity open " + tag);
                if (null != bundle) {
                    fragment.setArguments(bundle);
                }
                if (isAddToBackStack) {
                    transaction.addToBackStack(tag);
                }
                Fragment currentFragment = MyFragment.getCurrentFragment(fragmentActivity);
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.add(container, fragment, tag);
                if (CoreApplication.getInstance().isAllowStateLoss()) {
                    new Handler().post(new Runnable() {
                        public void run() {

                            transaction.commitAllowingStateLoss();
                            CoreApplication.getInstance().setAllowStateLoss(false);
                        }
                    });
                } else {
                    transaction.commit();
                }
                L.i("  activity commit" + tag);
                // Put fragment into stack
                putFragmentIntoStack(fragmentsStack, tag);

            } else {
                showFragment(fragmentParent, tag);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void openFragment(FragmentActivity fragmentActivity, int container, Class<? extends Fragment> fragmentClazz, Bundle bundle, boolean isAddToBackStack) {
        openFragment(fragmentActivity, container, fragmentClazz, bundle, null, isAddToBackStack);
    }

    public static void openFragment(FragmentActivity fragmentActivity, Fragment fragmentParent, int container, Class<? extends Fragment> fragmentClazz, Bundle bundle, boolean isAddToBackStack) {
        openFragment(fragmentActivity, fragmentParent, container, fragmentClazz, bundle, null, isAddToBackStack);

    }

    //    public static void openFragment(FragmentActivity fragmentActivity, int container, Class<? extends Fragment> fragmentClazz, boolean isAddToBackStack) {
//        openFragment(fragmentActivity, container, fragmentClazz, null, null, isAddToBackStack);
//
//    }
//    public static void openFragment(Fragment fragment, int container, Class<? extends Fragment> fragmentClazz, boolean isAddToBackStack) {
//        openFragment(fragment, container, fragmentClazz, null, null, isAddToBackStack);
//
//    }
    public static void openFragment(Fragment fragment, int container, Class<? extends Fragment> fragmentClazz, Bundle bundle, boolean isAddToBackStack) {
        openFragment(fragment, container, fragmentClazz, bundle, null, isAddToBackStack);

    }

    public static void putFragmentIntoStack(Stack<StackEntry> fragmentsStack, String tag) {
        if (fragmentsStack == null) {
            fragmentsStack = new Stack<>();
        }
        fragmentsStack.add(new StackEntry(tag));
    }

    /**
     * @param fragmentActivity
     * @param tag              kem tra fragment da ton tai chua
     * @return
     */
    private static Boolean isFragmentAdded(FragmentActivity fragmentActivity, String tag) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        if (null != fragmentList) {
            for (int i = fragmentList.size() - 2; i > -1; i--) {
                Fragment fragment = fragmentList.get(i);
                if (null != fragment && fragment.getClass().getName().equals(tag) && !fragment.isRemoving())
                    return true;
            }
        }
        return false;
    }

    /**
     * @param fragmentParrent
     * @param tag             kiem tra fragment da ton tai
     * @return
     */
    private static Boolean isFragmentAdded(Fragment fragmentParrent, String tag) {
        FragmentManager manager = fragmentParrent.getChildFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        if (null != fragmentList) {
            for (int i = fragmentList.size() - 1; i > -1; i--) {
                Fragment fragment = fragmentList.get(i);
                if (null != fragment && fragment.getClass().getName().equals(tag) && !fragment.isRemoving())
                    return true;
            }
        }
        return false;
    }

    private Fragment getLastFragment(FragmentActivity fragmentActivity, Stack<StackEntry> fragmentsStack) {
        if (fragmentsStack.isEmpty()) {
            return null;
        }
        String fragTag = fragmentsStack.peek().getFragTag();
        Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(fragTag);
        return fragment;
    }

    private Fragment getLastFragment(Fragment fragmentParent, Stack<StackEntry> fragmentsStack) {
        if (fragmentsStack.isEmpty()) {
            return null;
        }
        String fragTag = fragmentsStack.peek().getFragTag();
        Fragment fragment = fragmentParent.getChildFragmentManager().findFragmentByTag(fragTag);
        return fragment;
    }

    public void finishParentFragment(FragmentActivity fragmentActivity, WeakReference<FragmentActivity> wrActivity, Class<?> frag) {
        if (frag == null) {
            return;
        }
        try {
            String tag = frag.getName();
            FragmentManager manager, childManager;
            if ((null != wrActivity.get())
                    && (wrActivity.get().isFinishing() != true)) {
                manager = wrActivity.get().getSupportFragmentManager();
            } else {
                manager = fragmentActivity.getSupportFragmentManager();
            }
            Fragment storedFrag = null, fragTemp;
            List<Fragment> fragmentList = manager.getFragments();
            if (null != fragmentList) {
                for (int i = fragmentList.size() - 1; i > -1; i--) {
                    fragTemp = fragmentList.get(i);
                    if (null != fragTemp && fragTemp.getClass().getName().equals(tag) && !fragTemp.isRemoving()) {
                        storedFrag = fragTemp;
                        break;
                    }
                }
            }

            if (null != storedFrag) {
                childManager = storedFrag.getChildFragmentManager();
                FragmentTransaction transaction = childManager.beginTransaction();
                List<Fragment> fragmentChildrenList = childManager.getFragments();
                for (Fragment childFrag : fragmentChildrenList) {
                    if (null != childFrag) {
                        transaction.remove(childFrag).detach(childFrag);
                    }
                }
                transaction.commit();
                childManager.executePendingTransactions();
                for (int i = 0; i < childManager.getBackStackEntryCount(); i++) {
                    childManager.popBackStack();
                }
                manager.beginTransaction().remove(storedFrag).commit();
                manager.executePendingTransactions();
                L.i(" finishParentFragment: " + tag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishParentFragment(Fragment fragmentContainer, WeakReference<Fragment> wrFragment, Class<?> frag) {
        if (frag == null) {
            return;
        }
        try {
            String tag = frag.getName();
            FragmentManager manager, childManager;
            if ((null != wrFragment.get())
                    && (wrFragment.get().isRemoving() != true)) {
                manager = wrFragment.get().getChildFragmentManager();
            } else {
                manager = fragmentContainer.getChildFragmentManager();
            }
            Fragment storedFrag = null, fragTemp;
            List<Fragment> fragmentList = manager.getFragments();
            if (null != fragmentList) {
                for (int i = fragmentList.size() - 1; i > -1; i--) {
                    fragTemp = fragmentList.get(i);
                    if (null != fragTemp && fragTemp.getClass().getName().equals(tag) && !fragTemp.isRemoving()) {
                        storedFrag = fragTemp;
                        break;
                    }
                }
            }

            if (null != storedFrag) {
                childManager = storedFrag.getChildFragmentManager();
                FragmentTransaction transaction = childManager.beginTransaction();
                List<Fragment> fragmentChildrenList = childManager.getFragments();
                for (Fragment childFrag : fragmentChildrenList) {
                    if (null != childFrag) {
                        transaction.remove(childFrag).detach(childFrag);
                    }
                }
                transaction.commit();
                childManager.executePendingTransactions();
                for (int i = 0; i < childManager.getBackStackEntryCount(); i++) {
                    childManager.popBackStack();
                }
                manager.beginTransaction().remove(storedFrag).commit();
                manager.executePendingTransactions();
                L.i(" finishParentFragment: " + tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
