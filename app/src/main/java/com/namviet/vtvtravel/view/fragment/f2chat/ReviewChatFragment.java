package com.namviet.vtvtravel.view.fragment.f2chat;

import android.view.View;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2chat.StarReviewChatAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentReviewChatBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.chat.Star;
import com.namviet.vtvtravel.model.f2event.OnBackToChatBot;
import com.namviet.vtvtravel.model.f2event.OnReviewSuccess;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.viewmodel.f2chat.ReviewChatViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ReviewChatFragment extends BaseFragment<F2FragmentReviewChatBinding> implements View.OnClickListener, StarReviewChatAdapter.ClickItem, Observer {
    private StarReviewChatAdapter starReviewChatAdapter;
    private List<Star> listStar = new ArrayList<>();
    private ArrayList<String> stringArrayList;
    private String rate = "0";
    private String admin = "anonymous";
    private String note = "";
    private ReviewChatViewModel viewModel;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_review_chat;
    }

    @Override
    public void initView() {
        viewModel = new ReviewChatViewModel();
        getBinding().setReviewChatViewModel(viewModel);
        viewModel.addObserver(this);

        initStars();
        starReviewChatAdapter = new StarReviewChatAdapter(mActivity, listStar, this);
        getBinding().rclStar.setAdapter(starReviewChatAdapter);
        getBinding().imgBack.setOnClickListener(this);
        getBinding().imgClose.setOnClickListener(this);
        getBinding().tvSend.setOnClickListener(this);
        initCheckBox();
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

    private void initCheckBox() {
        stringArrayList = new ArrayList<>();
        getBinding().cb1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        stringArrayList.add(ReviewChatFragment.this.getBinding().cb1.getText().toString());
                    } else {
                        stringArrayList.remove(ReviewChatFragment.this.getBinding().cb1.getText().toString());
                    }
                }
        );
        getBinding().cb2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        stringArrayList.add(getBinding().cb2.getText().toString());
                    } else {
                        stringArrayList.remove(getBinding().cb2.getText().toString());
                    }
                }
        );
        getBinding().cb3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        stringArrayList.add(getBinding().cb3.getText().toString());
                    } else {
                        stringArrayList.remove(getBinding().cb3.getText().toString());
                    }
                }
        );
        getBinding().cb4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        stringArrayList.add(getBinding().cb4.getText().toString());
                    } else {
                        stringArrayList.remove(getBinding().cb4.getText().toString());
                    }
                }
        );
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
            case R.id.imgClose:
                mActivity.onBackPressed();
                break;
            case R.id.tvSend:
                try {
                    note = getBinding().tvNote.getText().toString();
                    if ("0".equals(rate)) {
                        Toast.makeText(mActivity, "Bạn cần chọn thang điểm sao!", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.reviewChat(admin, note, stringArrayList, rate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void clickStar(int position) {
        initStars();
        for (int i = 0; i <= position; i++) {
            listStar.get(i).setSelected(true);
        }
        starReviewChatAdapter.notifyDataSetChanged();
        rate = String.valueOf(position + 1);
        if(position > 5){
            getBinding().layoutCheckbox.setVisibility(View.GONE);
        }else {
            getBinding().layoutCheckbox.setVisibility(View.VISIBLE);
        }
    }

    private void initStars() {
        listStar.clear();
        for (int i = 0; i < 10; i++) {
            listStar.add(new Star(false));
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ReviewChatViewModel && null != o) {
            if (o instanceof BaseResponse) {
                Toast.makeText(mActivity, "Gửi đánh giá thành công!", Toast.LENGTH_SHORT).show();
                mActivity.onBackPressed();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new OnReviewSuccess());
    }
}
