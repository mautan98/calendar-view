package com.namviet.vtvtravel.widget;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OnScrollToPositionListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private int mCurrentPosition = 0;

    public OnScrollToPositionListener(LinearLayoutManager layoutManager) {
        linearLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastFullVisiblePos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int firstVisiblePos = linearLayoutManager.findFirstVisibleItemPosition();
        if (lastFullVisiblePos < linearLayoutManager.getItemCount() - 1) {
            if (firstVisiblePos > mCurrentPosition) {
                mCurrentPosition = firstVisiblePos;
                onPositionChange(recyclerView, mCurrentPosition);
            } else if (firstVisiblePos < mCurrentPosition) {
                if (firstVisiblePos < mCurrentPosition - 1) {
                    mCurrentPosition = mCurrentPosition - 1;
                    onPositionChange(recyclerView, mCurrentPosition);
                } else if (mCurrentPosition == 1) {
                    if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
                            || (linearLayoutManager.findLastVisibleItemPosition() == firstVisiblePos)) {
                        mCurrentPosition = 0;
                        onPositionChange(recyclerView, mCurrentPosition);
                    }
                }
            }
        } else {
            if (lastFullVisiblePos != mCurrentPosition) {
                mCurrentPosition = lastFullVisiblePos;
                onPositionChange(recyclerView, mCurrentPosition);
            }
        }
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    protected abstract void onPositionChange(RecyclerView recyclerView, int position);


}
