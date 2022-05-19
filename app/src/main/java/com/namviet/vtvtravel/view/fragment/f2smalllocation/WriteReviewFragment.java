package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.baseapp.utils.KeyboardUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.namviet.vtvtravel.BuildConfig;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.smalllocation.AddImageAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentWriteReviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnReviewSuccess;
import com.namviet.vtvtravel.response.f2review.CreateReviewResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2review.UploadImageResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.f2review.ReviewViewModel;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WriteReviewFragment extends BaseFragment<F2FragmentWriteReviewBinding> implements Observer {
    private ReviewViewModel viewModel;
    private AddImageAdapter addImageAdapter;
    private BottomSheetBehavior bottomSheetBehavior;


    public int REQUEST_PERMISTION_AVATAR = 20;
    public static int REQUEST_CAMERA = 100;
    public static int REQUEST_SELECT_PICTURE = 0x01;
    public static Uri fileUri;
    public static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";
    private Uri mUri;


    private List<Bitmap> bitmaps = new ArrayList<>();
    private int countImageNeedToUpload = 0;
    List<String> listUrl = new ArrayList<>();

    private ProgressDialog progressDialog;

    @SuppressLint("ValidFragment")
    public WriteReviewFragment(String contentId, String contentType, String title) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.title = title;
    }

    private String contentId;
    private String contentType;
    private String title;

    public WriteReviewFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_write_review;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(getBinding().bottomSheetLayout);
        viewModel = new ReviewViewModel();
        getBinding().setReviewViewModel(viewModel);
        viewModel.addObserver(this);
        getBinding().ratingReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 1) {
                    getBinding().tvRateText.setText("Kém");
                } else if (rating == 2) {
                    getBinding().tvRateText.setText("Trung bình");
                } else if (rating == 3) {
                    getBinding().tvRateText.setText("Hài lòng");
                } else if (rating == 4) {
                    getBinding().tvRateText.setText("Rất tốt");
                } else if (rating == 5) {
                    getBinding().tvRateText.setText("Tuyệt vời");
                } else {
                    getBinding().tvRateText.setText("Trải nghiệm của bạn thế nào?");
                }
            }
        });

        RxTextView.afterTextChangeEvents(getBinding().edtReview)
                .skipInitialValue()
                .debounce(10, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void accept(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        String s = getBinding().edtReview.getText().toString().length() + "/1000 ký tự tối thiểu";
                        getBinding().tvLeftChar.setText(s);
                    }
                });


        try {
            getBinding().rootView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    KeyboardUtils.hideKeyboard(mActivity, getBinding().edtReview);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        addImageAdapter = new AddImageAdapter(bitmaps, mActivity, new AddImageAdapter.ClickItem() {
            @Override
            public void onClickAddImage() {
                KeyboardUtils.hideKeyboard(mActivity, getBinding().getRoot());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                        } else {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            getBinding().btnViewAlpha.setVisibility(View.VISIBLE);
                        }
                    }
                }, 500);
            }

            @Override
            public void onDeleteImage(int position) {
                bitmaps.remove(position);
                addImageAdapter.notifyDataSetChanged();
            }
        });
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(mActivity);
        getBinding().rclImage.setLayoutManager(flexboxLayoutManager);
        getBinding().rclImage.setAdapter(addImageAdapter);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getBinding().ratingReview.getRating() != 0 || !getBinding().edtReview.getText().toString().isEmpty()) {
                    CancelReviewDialog cancelReviewDialog = CancelReviewDialog.newInstance(new CancelReviewDialog.ClickButton() {
                        @Override
                        public void onClickButton() {
                            mActivity.onBackPressed();
                            KeyboardUtils.hideKeyboard(mActivity, getBinding().edtReview);
                        }
                    });
                    cancelReviewDialog.show(mActivity.getSupportFragmentManager(), null);
                } else {
                    KeyboardUtils.hideKeyboard(mActivity, getBinding().edtReview);
                    mActivity.onBackPressed();
                }

            }
        });

        getBinding().btnSendTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, getBinding().edtReview);
                sendReview();
            }
        });

        getBinding().btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, getBinding().edtReview);
                sendReview();
            }
        });

        getBinding().btnPickFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getBinding().btnViewAlpha.setVisibility(View.GONE);
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISTION_AVATAR);
                } else {
                    getPhotoFromCamera();
                }
            }
        });

        getBinding().btnPickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoFromGalley();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getBinding().btnViewAlpha.setVisibility(View.GONE);
            }
        });

        getBinding().btnViewAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().btnViewAlpha.setVisibility(View.GONE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }


    private void sendReview() {
        if (getBinding().ratingReview.getRating() == 0) {
            showToast("Bạn hãy chọn điểm cho đánh giá này!");
            return;
        }
        if (getBinding().edtReview.getText().toString().isEmpty()) {
            showToast("Bạn hãy nhập nội dung đánh giá");
            return;
        }
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            listUrl.clear();
            if (bitmaps.size() > 0) {
                showProgress();
                countImageNeedToUpload = 0;
                uploadImage();
            } else {
                showProgress();
                viewModel.createReview(null, String.valueOf(account.getId()), getBinding().edtReview.getText().toString(), contentId, contentType, String.valueOf((int) getBinding().ratingReview.getRating()), listUrl,title );
            }

        } else {
            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
        }
    }

    private void uploadImage() {
        for (int i = 0; i < bitmaps.size(); i++) {
            File file = saveBitmap(bitmaps.get(i));
            viewModel.updateImage(file, "image");
        }
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ReviewViewModel && null != o) {
            if (o instanceof GetReviewResponse) {
                GetReviewResponse response = (GetReviewResponse) o;
            } else if (o instanceof CreateReviewResponse) {
                dismissDialog();
                CreateReviewResponse response = (CreateReviewResponse) o;
                EventBus.getDefault().post(new OnReviewSuccess());
//                bitmaps.clear();
//                addImageAdapter.notifyDataSetChanged();
                genDialogSendReviewSuccess();

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.REVIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.REVIEW, TrackingAnalytic.ScreenTitle.REVIEW)
                            .setComment(getBinding().edtReview.getText().toString())
                            .setRating(String.valueOf(getBinding().ratingReview.getRating()))
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (o instanceof UploadImageResponse) {
                UploadImageResponse response = (UploadImageResponse) o;
                Log.e("link", response.getData());
                countImageNeedToUpload = countImageNeedToUpload + 1;
                listUrl.add(response.getData());
                if (countImageNeedToUpload == bitmaps.size()) {
                    showToast("Tải ảnh lên hoàn tất, đang gửi đánh giá...");
                    Account account = MyApplication.getInstance().getAccount();
                    if (null != account && account.isLogin()) {
                        viewModel.createReview(null, String.valueOf(account.getId()), getBinding().edtReview.getText().toString(), contentId, contentType, String.valueOf((int) getBinding().ratingReview.getRating()), listUrl, title);
                    } else {
                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                    }
                }
            }

        } else if (o instanceof ErrorResponse) {
            ErrorResponse responseError = (ErrorResponse) o;
            try {
                showToast("Có lỗi xảy ra, mời bạn thử lại sau!");
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
            } catch (Exception e) {

            }
        } else {
            try {
                showToast("Có lỗi xảy ra");
            } catch (Exception e) {
                e.printStackTrace();
            }
            dismissDialog();
        }
    }


    public void getPhotoFromGalley() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
    }

    public void getPhotoFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            fileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        fileUri = FileProvider.getUriForFile(mActivity,
                BuildConfig.APPLICATION_ID + ".provider",
                photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            Toast.makeText(mActivity, "Đã có lỗi xảy ra, mời bạn thử chọn ảnh từ thư viện!", Toast.LENGTH_SHORT).show();
        }
    }
    public static final String IMAGE_NAME = "VTVTravelImg";
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = IMAGE_NAME;
        File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //resume tasks needing this permission
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                getBinding().btnViewAlpha.setVisibility(View.VISIBLE);
            }
        }

        if (requestCode == REQUEST_PERMISTION_AVATAR) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhotoFromCamera();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                getBinding().btnViewAlpha.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    handleConvertURIToBitmap(selectedUri);
                } else {
                    Toast.makeText(mActivity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                Uri selectedUri = fileUri;
                if (null != selectedUri) {
                    startCropActivity(selectedUri);
//                    handleConvertURIToBitmap(selectedUri);
                } else {
                    Toast.makeText(mActivity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(mActivity.getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME + ".jpg")));
        uCrop.start(mActivity);
    }

    private void handleCropResult(@NonNull Intent result) {
        this.mUri = UCrop.getOutput(result);
        if (this.mUri != null) {
            int maxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(getContext());
            BitmapLoadUtils.decodeBitmapInBackground(getActivity(), this.mUri, null, maxBitmapSize, maxBitmapSize,
                    new BitmapLoadCallback() {

                        @Override
                        public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo,
                                                   @NonNull String imageInputPath,
                                                   @Nullable String imageOutputPath) {
                            bitmaps.add(bitmap);
                            addImageAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(@NonNull Exception bitmapWorkerException) {

                        }
                    });

        } else {
            Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleConvertURIToBitmap(Uri uri) {
        if (uri != null) {
            int maxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(mActivity);
            BitmapLoadUtils.decodeBitmapInBackground(mActivity, uri, null, maxBitmapSize, maxBitmapSize,
                    new BitmapLoadCallback() {

                        @Override
                        public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo,
                                                   @NonNull String imageInputPath,
                                                   @Nullable String imageOutputPath) {
                            bitmaps.add(bitmap);
                            addImageAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(@NonNull Exception bitmapWorkerException) {

                        }
                    });

        } else {
            Toast.makeText(mActivity, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

//    private void handleCropResult(@NonNull Intent result) {
//        this.mUri = UCrop.getOutput(result);
//        if (this.mUri != null) {
//            int maxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(mActivity);
//            BitmapLoadUtils.decodeBitmapInBackground(mActivity, this.mUri, null, maxBitmapSize, maxBitmapSize,
//                    new BitmapLoadCallback() {
//
//                        @Override
//                        public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo,
//                                                   @NonNull String imageInputPath,
//                                                   @Nullable String imageOutputPath) {
//                            showToast("ok");
//
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Exception bitmapWorkerException) {
//
//                        }
//                    });
//
//        } else {
//            Toast.makeText(mActivity, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
//        }
//    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            try {
                Toast.makeText(mActivity, cropError.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {

            }
        } else {
            try {
                Toast.makeText(mActivity, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }
        }
    }

//    private File saveBitmap(Bitmap bm) {
//
//        String fileName = "android" + System.currentTimeMillis();
//        File sdCard = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File dir = new File(sdCard.getAbsolutePath() + "/VTVTravelFileUploaded/");
//        dir.mkdirs();
//        File file = new File(dir, fileName + ".jpg");
//
//        try {
//            FileOutputStream fOut = new FileOutputStream(file, false);
//            bm.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
//            fOut.flush();
//            fOut.close();
//        } catch (Exception e) {
//
//        }
//        return file;
//    }

    private File saveBitmap(Bitmap bm) {
        String fileName = "android" + System.currentTimeMillis();
        File sd = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File dest = new File(sd, fileName);
        Log.e("path", "" + dest.getPath());
        Bitmap result = Bitmap.createScaledBitmap(bm, 200, 200, false);
        try {
            FileOutputStream out = new FileOutputStream(dest);
            result.compress(Bitmap.CompressFormat.PNG, 70, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

    private void genDialogSendReviewSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("Gửi đánh giá thành công.");
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActivity.onBackPressed();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    private void showProgress() {
        try {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage("Đang gửi đánh giá...");
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.REVIEW, TrackingAnalytic.ScreenTitle.REVIEW);
    }
}
