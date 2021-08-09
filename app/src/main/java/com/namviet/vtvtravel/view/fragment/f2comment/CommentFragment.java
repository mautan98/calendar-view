package com.namviet.vtvtravel.view.fragment.f2comment;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.comment.CommentAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentCommentBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnCommentSuccessInTravelNews;
import com.namviet.vtvtravel.model.f2event.OnUpdateCommentCount;
import com.namviet.vtvtravel.response.f2comment.CheckShowCaptcha;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2comment.CreateCommentResponse;
import com.namviet.vtvtravel.response.f2comment.DeleteCommentResponse;
import com.namviet.vtvtravel.response.f2comment.UpdateCommentResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.f2comment.CommentViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class CommentFragment extends BaseFragment<F2FragmentCommentBinding> implements Observer {
    public static int TYPE_COMMENT_NORMAL = 0;
    public static int TYPE_COMMENT_EDIT = 1;
    public static int TYPE_COMMENT_REPLY = 2;
    private CommentViewModel viewModel;
    private CommentAdapter commentAdapter;
    private DetailTravelNewsResponse detailTravelNewsResponse;
    private String contentId;
    private String parentId;
    private String userId;
    private String contentType;

    private int typeComment = TYPE_COMMENT_NORMAL;
    private String idCommentForEdit;

    private List<CommentResponse.Data.Comment> comments = new ArrayList<>();

    private int commentAdd = 0;

    private int page = 0;

    private EndlessRecyclerViewScrollListener scrollListener;

    private ProgressDialog progressDialog;


    public CommentFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_comment;
    }

    @Override
    public void initView() {
        viewModel = new CommentViewModel();
        getBinding().setCommentViewModel(viewModel);
        viewModel.addObserver(this);

        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Đang kiểm tra bình luận");

    }

    private void checkComment(String parentId, String content, String contentId, String contentType){
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            userId = String.valueOf(account.getId());
            viewModel.checkShowCaptcha(parentId, userId, content, contentId, contentType);
            progressDialog.show();
        } else {
            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
        }
    }

    private void postComment(String parentId, String content, String contentId, String contentType) {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                userId = String.valueOf(account.getId());
                viewModel.postComment(parentId, userId, content, contentId, contentType, detailTravelNewsResponse.getData().getName());
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initData() {
        handleSearch();
        contentId = detailTravelNewsResponse.getData().getId();
        contentType = detailTravelNewsResponse.getData().getContent_type();

        viewModel.getComment(detailTravelNewsResponse.getData().getId(), page);


        commentAdapter = new CommentAdapter(comments, mActivity, new CommentAdapter.ClickItem() {
            @Override
            public void onClickItem(CommentResponse.Data.Comment comment) {
                viewModel.getComment(detailTravelNewsResponse.getData().getId(), page);
            }

            @Override
            public void onLongClickItem(CommentResponse.Data.Comment comment, CommentResponse.Data.Comment commentParent) {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    userId = String.valueOf(account.getId());
                    if (userId.equals(comment.getUserId())) {
                        OptionCommentOfMineDialog optionCommentOfMineDialog = new OptionCommentOfMineDialog();
                        optionCommentOfMineDialog.setClickButton(new OptionCommentOfMineDialog.ClickButton() {
                            @Override
                            public void onClickEdit() {
                                typeComment = TYPE_COMMENT_EDIT;
                                idCommentForEdit = comment.getId();
                                getBinding().edtComment.setText(comment.getContent());
                            }

                            @Override
                            public void onClickCopy() {
                                ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("label", comment.getContent());
                                clipboard.setPrimaryClip(clip);
                                showToast("Đã sao chép tin nhắn vào bộ nhớ tạm");
                            }

                            @Override
                            public void onDelete() {
                                deleteComment(comment.getId());
                            }
                        });
                        optionCommentOfMineDialog.show(mActivity.getSupportFragmentManager(), null);
                    } else {
                        OptionCommentDialog optionCommentDialog = new OptionCommentDialog();
                        optionCommentDialog.setClickButton(new OptionCommentDialog.ClickButton() {

                            @Override
                            public void onClickReply() {
                                typeComment = TYPE_COMMENT_REPLY;
                                getBinding().edtComment.setText("Trả lời: " + comment.getUser().getFullname());
                                CommentFragment.this.parentId = commentParent.getId();
                            }

                            @Override
                            public void onClickCopy() {
                                ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("label", comment.getContent());
                                clipboard.setPrimaryClip(clip);
                                showToast("Đã sao chép tin nhắn vào bộ nhớ tạm");
                            }

                        });
                        optionCommentDialog.show(mActivity.getSupportFragmentManager(), null);
                    }
                } else {
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }


            }

            @Override
            public void onClickReply(CommentResponse.Data.Comment comment, CommentResponse.Data.Comment commentParent) {
                typeComment = TYPE_COMMENT_REPLY;
                getBinding().edtComment.setText("#" + comment.getUser().getFullname());
                CommentFragment.this.parentId = commentParent.getId();
            }

            @Override
            public void likeEvent(int position) {
                try {
                    viewModel.likeEvent(comments.get(position).getId(), "comments");

                    try {
                        TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.COMMENT, TrackingAnalytic.ScreenTitle.COMMENT)
                                .setContent_id(comments.get(position).getId())
                                .setContent_type("comments")
                                .setScreen_class(this.getClass().getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (comments.get(position).isLiked()) {
                        comments.get(position).setLiked(false);
                        if (null != comments.get(position).getLikeCount()) {
                            String likeCount = (Integer.parseInt(comments.get(position).getLikeCount()) - 1) + "";
                            comments.get(position).setLikeCount(likeCount);
                        }
                    } else {
                        comments.get(position).setLiked(true);
                        if (null != comments.get(position).getLikeCount()) {
                            String likeCount = (Integer.parseInt(comments.get(position).getLikeCount()) + 1) + "";
                            comments.get(position).setLikeCount(likeCount);
                        }
                    }
                    commentAdapter.notifyItemChanged(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, viewModel);
        getBinding().rclComment.setAdapter(commentAdapter);


        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            Glide.with(mActivity).load(account.getImageProfile()).error(R.drawable.f2_defaut_user).into(getBinding().imgAvatar);
        } else {
        }


        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) getBinding().rclComment.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        };



        getBinding().rclComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    try {
                        viewModel.getComment(detailTravelNewsResponse.getData().getId(), CommentFragment.this.page);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void inject() {

    }

    private void deleteComment(String commentId) {
        viewModel.deleteComment(commentId);
    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    userId = String.valueOf(account.getId());


                    if (typeComment == TYPE_COMMENT_EDIT) {
                        if (getBinding().edtComment.getText().toString().length()>5) {
                            viewModel.updateComment(idCommentForEdit, getBinding().edtComment.getText().toString());
                            getBinding().edtComment.setText("");

                            typeComment = TYPE_COMMENT_NORMAL;
                        }else {
                            Toast.makeText(mActivity, "Độ dài comment phải lớn hơn 5 ký tự", Toast.LENGTH_SHORT).show();
                        }
                    } else if (typeComment == TYPE_COMMENT_REPLY) {
                        if (getBinding().edtComment.getText().toString().length() > 5) {
                            checkComment(parentId, getBinding().edtComment.getText().toString(), contentId, contentType);
//                            getBinding().edtComment.setText("");

                            typeComment = TYPE_COMMENT_NORMAL;
                        }else {
                            Toast.makeText(mActivity, "Độ dài comment phải lớn hơn 5 ký tự", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (getBinding().edtComment.getText().toString().length() > 5) {
                            checkComment(null, getBinding().edtComment.getText().toString(), contentId, contentType);
//                            getBinding().edtComment.setText("");

                        }else {
                            Toast.makeText(mActivity, "Độ dài comment phải lớn hơn 5 ký tự", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }

            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getBinding().layoutLoading.setVisibility(View.GONE);
        if (observable instanceof CommentViewModel && null != o) {
            if (o instanceof CommentResponse) {
                CommentResponse response = (CommentResponse) o;
                if (response.getData().getContent().size() > 0) {
                    getBinding().layoutNoComment.setVisibility(View.GONE);
                }
                if(page == 0) {
                    comments.clear();
                    comments.addAll(response.getData().getContent());
                    commentAdapter.notifyDataSetChanged();
                }else {
                    comments.addAll(response.getData().getContent());
                    commentAdapter.notifyDataSetChanged();
                }
                page = page + 1;

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.COMMENT, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.COMMENT, TrackingAnalytic.ScreenTitle.COMMENT)
                            .setContent_type(contentType)
                            .setContent_id(contentId)
                            .setComment(getBinding().edtComment.getText().toString())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if(o instanceof CheckShowCaptcha){
                CheckShowCaptcha checkShowCaptcha = (CheckShowCaptcha) o;
                if(checkShowCaptcha.getData().isCaptcha() == false
                        &&
                        checkShowCaptcha.getData().isSensitiveWord() == false) {
                    getBinding().edtComment.setText("");
                    postComment(checkShowCaptcha.getParentId(), checkShowCaptcha.getContent(), checkShowCaptcha.getContentId(), checkShowCaptcha.getContentType());
                }else if(checkShowCaptcha.getData().isCaptcha()){
                    CaptchaDialog captchaDialog = CaptchaDialog.newInstance(new CaptchaDialog.ClickButton() {
                        @Override
                        public void onClickButton() {
                            getBinding().edtComment.setText("");
                            postComment(checkShowCaptcha.getParentId(), checkShowCaptcha.getContent(), checkShowCaptcha.getContentId(), checkShowCaptcha.getContentType());
                        }
                    });
                    captchaDialog.show(getChildFragmentManager(), "");
                }else if(checkShowCaptcha.getData().isSensitiveWord()){
                    Toast.makeText(mActivity, "Bình luận chứa từ ngữ khiễm nhã", Toast.LENGTH_SHORT).show();
                }
            } else if (o instanceof CreateCommentResponse) {
                CreateCommentResponse response = (CreateCommentResponse) o;
                if (response != null && response.isSuccess()) {
                    EventBus.getDefault().post(new OnCommentSuccessInTravelNews());
                    page = 0;
                    viewModel.getComment(detailTravelNewsResponse.getData().getId(), page);
                    commentAdd = commentAdd + 1;
                }
            } else if (o instanceof DeleteCommentResponse) {
                DeleteCommentResponse response = (DeleteCommentResponse) o;
                if (response != null && response.isSuccess()) {
                    EventBus.getDefault().post(new OnCommentSuccessInTravelNews());
                    page = 0;
                    viewModel.getComment(detailTravelNewsResponse.getData().getId(), page);
                    commentAdd = commentAdd - 1;
                }
            } else if (o instanceof UpdateCommentResponse) {
                UpdateCommentResponse response = (UpdateCommentResponse) o;
                if (response != null && response.isSuccess()) {
                    page = 0;
                    viewModel.getComment(detailTravelNewsResponse.getData().getId(), page);
                }
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    public void setDetailTravelNewsResponse(DetailTravelNewsResponse detailTravelNewsResponse) {
        this.detailTravelNewsResponse = detailTravelNewsResponse;
    }


    private void handleSearch() {
        RxTextView.afterTextChangeEvents(getBinding().edtComment)
                .skipInitialValue()
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        if (getBinding().edtComment.getText().toString().isEmpty()) {
//                            getBinding().imgSend.setAlpha(0.3f);
                        } else {
//                            getBinding().imgSend.setAlpha(1f);
                        }
                    }
                });


    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            OnUpdateCommentCount onUpdateCommentCount = new OnUpdateCommentCount(String.valueOf(commentAdd));
            EventBus.getDefault().post(onUpdateCommentCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.COMMENT, TrackingAnalytic.ScreenTitle.COMMENT);
    }
}
