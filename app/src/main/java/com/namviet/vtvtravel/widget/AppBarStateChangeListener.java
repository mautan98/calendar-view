package com.namviet.vtvtravel.widget;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        State newState = getStateFromOffset(appBarLayout, i);
        if(newState!=mCurrentState){
            mCurrentState = newState;
            onStateChanged(appBarLayout, mCurrentState);
        }
    }

    public static State getStateFromOffset(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            return State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            return State.COLLAPSED;
        } else {
            return  State.IDLE;
        }
    }

    public State getCurrentState() {
        return mCurrentState;
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
