package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityCommentBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2comment.CommentFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog;

import static com.namviet.vtvtravel.config.Constants.IntentKey.PARENT_COMMENT_ID;

public class CommentActivity extends BaseActivityNew<F2ActivityCommentBinding> {
    private DetailTravelNewsResponse detailTravelNewsResponse;
    private String parentCommentId;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_comment;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        detailTravelNewsResponse = (DetailTravelNewsResponse) getIntent().getSerializableExtra(Constants.IntentKey.DATA);
        parentCommentId =  getIntent().getStringExtra(PARENT_COMMENT_ID);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setDetailTravelNewsResponse(detailTravelNewsResponse);
        commentFragment.setParentId(parentCommentId);
        return commentFragment;
    }


    public static void startScreen(Activity activity, DetailTravelNewsResponse detailTravelNewsResponse, String parentCommentId){
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, detailTravelNewsResponse);
        intent.putExtra(PARENT_COMMENT_ID, parentCommentId);
        activity.startActivity(intent);
    }
}
