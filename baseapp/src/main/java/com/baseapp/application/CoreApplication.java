package com.baseapp.application;



/**
 * Created by vn on 12/19/2016.
 */
public class CoreApplication  {
    private static CoreApplication ourInstance = new CoreApplication();
    private boolean allowStateLoss = true;
    private int screenHeight;
    public static CoreApplication getInstance() {
        return ourInstance;
    }

    protected CoreApplication() {
    }


    public boolean isAllowStateLoss() {
        return allowStateLoss;
    }

    public void setAllowStateLoss(boolean allowStateLoss) {
        this.allowStateLoss = allowStateLoss;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
