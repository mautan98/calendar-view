package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.ImageSlider;
import com.daimajia.slider.library.ItemGroup;
import com.daimajia.slider.library.R;
import com.daimajia.slider.library.Travel;

import java.io.File;


public abstract class BaseSliderView {

    protected Context mContext;

    private Bundle mBundle;

    /**
     * Error place holder image.
     */
    private int mErrorPlaceHolderRes;

    /**
     * Empty imageView placeholder.
     */
    private int mEmptyPlaceHolderRes;

    private ItemGroup groupSlider;
    private File mFile;
    private int mRes;

    protected OnSliderClickListener mOnSliderClickListener;

    private boolean mErrorDisappear;

    private ImageLoadListener mLoadListener;

    private String mDescription;


    /**
     * Scale type of the image.
     */
    private ScaleType mScaleType = ScaleType.Fit;

    public enum ScaleType {
        CenterCrop, CenterInside, Fit, FitCenterCrop
    }

    protected BaseSliderView(Context context) {
        mContext = context;
    }

    /**
     * the placeholder image when loading image from url or file.
     *
     * @param resId Image resource id
     * @return
     */
    public BaseSliderView empty(int resId) {
        mEmptyPlaceHolderRes = resId;
        return this;
    }

    /**
     * determine whether remove the image which failed to download or load from file
     *
     * @param disappear
     * @return
     */
    public BaseSliderView errorDisappear(boolean disappear) {
        mErrorDisappear = disappear;
        return this;
    }

    /**
     * if you set errorDisappear false, this will set a error placeholder image.
     *
     * @param resId image resource id
     * @return
     */
    public BaseSliderView error(int resId) {
        mErrorPlaceHolderRes = resId;
        return this;
    }

    /**
     * the description of a slider image.
     *
     * @param description
     * @return
     */
    public BaseSliderView description(String description) {
        mDescription = description;
        return this;
    }

    /**
     * set a url as a image that preparing to load
     *
     * @param group
     * @return
     */
    public BaseSliderView image(ItemGroup group) {
        if (mFile != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        groupSlider = group;
        return this;
    }

    /**
     * set a file as a image that will to load
     *
     * @param file
     * @return
     */
    public BaseSliderView image(File file) {
        if (groupSlider != null || mRes != 0) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    public BaseSliderView image(int res) {
        if (groupSlider != null || mFile != null) {
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mRes = res;
        return this;
    }

    /**
     * lets users add a bundle of additional information
     *
     * @param bundle
     * @return
     */
    public BaseSliderView bundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public ItemGroup getUrl() {
        return groupSlider;
    }

    public boolean isErrorDisappear() {
        return mErrorDisappear;
    }

    public int getEmpty() {
        return mEmptyPlaceHolderRes;
    }

    public int getError() {
        return mErrorPlaceHolderRes;
    }

    public String getDescription() {
        return mDescription;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set a slider image click listener
     *
     * @param l
     * @return
     */
    public BaseSliderView setOnSliderClickListener(OnSliderClickListener l) {
        mOnSliderClickListener = l;
        return this;
    }


    protected void bindEventAndShow(final View v, ImageView targetImageView) {
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (targetImageView == null)
            return;

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        ProgressBar prBar = v.findViewById(R.id.loading_bar);
        if (groupSlider != null) {
        } else if (mFile != null) {
            setImageFile(mFile, targetImageView, prBar);
        } else if (mRes != 0) {
            setImageRes(mRes, targetImageView, prBar);
        }
    }

    protected void bindThreeEventAndShow(final View v, ImageView ivItem1, TextView tvItem1, ImageView ivItem2, TextView tvItem2, ImageView ivItem3, TextView tvItem3) {
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        View cvItem1 = v.findViewById(R.id.cvItem1);
        View llItem2 = v.findViewById(R.id.llItem2);
        View llItem3 = v.findViewById(R.id.llItem3);
        ProgressBar prBar = v.findViewById(R.id.loading_bar);
        if (groupSlider != null) {
            if (groupSlider.getGroup().size() > 0) {
                setImageUrl(groupSlider.getGroup().get(0).getLogo_url(), ivItem1);
                tvItem1.setText(groupSlider.getGroup().get(0).getName().trim());
                cvItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mOnSliderClickListener) {
                            mOnSliderClickListener.onSliderClick(groupSlider.getGroup().get(0));
                        }
                    }
                });
            }

            if (groupSlider.getGroup().size() > 1) {
                setImageUrl(groupSlider.getGroup().get(1).getLogo_url(), ivItem2);
                tvItem2.setText(groupSlider.getGroup().get(1).getName().trim());
                llItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mOnSliderClickListener) {
                            mOnSliderClickListener.onSliderClick(groupSlider.getGroup().get(1));
                        }
                    }
                });
            } else {
                ivItem2.setVisibility(View.INVISIBLE);
                tvItem2.setVisibility(View.INVISIBLE);
            }

            if (groupSlider.getGroup().size() > 2) {
                setImageUrl(groupSlider.getGroup().get(2).getLogo_url(), ivItem3);
                tvItem3.setText(groupSlider.getGroup().get(2).getName().trim());
                llItem3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mOnSliderClickListener) {
                            mOnSliderClickListener.onSliderClick(groupSlider.getGroup().get(2));
                        }
                    }
                });
            } else {
                tvItem3.setVisibility(View.INVISIBLE);
                ivItem3.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected void bindTwoEventAndShow(final View v, ImageView ivItem1, TextView tvItem1, ImageView ivItem2, TextView tvItem2) {
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }
        View llItem2 = v.findViewById(R.id.llItem2);
        View llItem3 = v.findViewById(R.id.llItem3);
        ProgressBar prBar = v.findViewById(R.id.loading_bar);
        if (groupSlider != null) {
            if (groupSlider.getGroup().size() > 0) {
                setImageUrl(groupSlider.getGroup().get(0).getLogo_url(), ivItem1);
                tvItem1.setText(groupSlider.getGroup().get(0).getName().trim());
                llItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mOnSliderClickListener) {
                            mOnSliderClickListener.onSliderClick(groupSlider.getGroup().get(0));
                        }
                    }
                });
            }

            if (groupSlider.getGroup().size() > 1) {
                setImageUrl(groupSlider.getGroup().get(1).getLogo_url(), ivItem2);
                tvItem2.setText(groupSlider.getGroup().get(1).getName().trim());
                llItem3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mOnSliderClickListener) {
                            mOnSliderClickListener.onSliderClick(groupSlider.getGroup().get(1));
                        }
                    }
                });
            } else {
                ivItem2.setVisibility(View.INVISIBLE);
                tvItem2.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img_placeholder);
        requestOptions.error(R.drawable.img_placeholder);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

    }

    public void setImageFile(File file, ImageView image, final ProgressBar prLoading) {
        Glide.with(mContext).load(file).thumbnail(0.2f).into(image);
    }

    public void setImageRes(int res, ImageView image, final ProgressBar prLoading) {
        image.setImageResource(res);
    }

    public BaseSliderView setScaleType(ScaleType type) {
        mScaleType = type;
        return this;
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }


    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     *
     * @param l
     */
    public void setOnImageLoadListener(ImageLoadListener l) {
        mLoadListener = l;
    }

    public interface OnSliderClickListener {
        public void onSliderClick(Travel travel);
    }

    /**
     * when you have some extra information, please put it in this bundle.
     *
     * @return
     */
    public Bundle getBundle() {
        return mBundle;
    }

    public interface ImageLoadListener {
        public void onStart(BaseSliderView target);

        public void onEnd(boolean result, BaseSliderView target);
    }


}
