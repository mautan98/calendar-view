package com.namviet.vtvtravel.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.DialogCommentBinding;
import com.namviet.vtvtravel.listener.PostCommentListener;
import com.namviet.vtvtravel.model.Comment;

public class CommentDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private DialogCommentBinding binding;
    private Comment mComment;

    private PostCommentListener postCommentListener;

    public void setPostCommentListener(PostCommentListener postCommentListener) {
        this.postCommentListener = postCommentListener;
    }

    public static CommentDialogFragment newInstance(Comment mComment) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_DIALOG, mComment);
        CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
        commentDialogFragment.setArguments(bundle);
        return commentDialogFragment;
    }

    public static CommentDialogFragment newInstance() {
        CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
        return commentDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mComment = getArguments().getParcelable(Constants.IntentKey.KEY_DIALOG);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_comment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View v) {
        binding.edComment.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        updateView();
    }

    protected void updateView() {
        binding.edComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binding.ivSend.setVisibility(View.VISIBLE);
                    binding.ivSend.setEnabled(true);
                } else {
                    binding.ivSend.setVisibility(View.INVISIBLE);
                    binding.ivSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ivSend.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivSend) {
            String comment = binding.edComment.getText().toString().trim();
            if (null != postCommentListener) {
                if (null != mComment) {
                    postCommentListener.onPostComment(comment, mComment.getId());
                } else {
                    postCommentListener.onPostComment(comment, null);
                }

            }
            dismiss();
        }
    }
}
