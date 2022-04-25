package com.namviet.vtvtravel.view.fragment.f2userinformation;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.namviet.vtvtravel.BuildConfig;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentPersonalInformationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnUpdateAccount;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.fragment.f2chat.ChangeAvatarDialog;
import com.namviet.vtvtravel.view.fragment.f2chat.ViewImageProfileFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

public class UserInformationFragment extends BaseFragment<F2FragmentPersonalInformationBinding> implements View.OnClickListener, GenderDialog.Gender, ChangeAvatarDialog.ClickOption, Observer {
    private boolean checkMergeInfo = false;
    private boolean checkMergePaper = false;
    private boolean checkMergeBill = false;
    private boolean checkStatus = false;

    public int REQUEST_PERMISTION_AVATAR = 20;
    public static int REQUEST_CAMERA = 100;
    public static int REQUEST_SELECT_PICTURE = 0x01;
    public static Uri fileUri;
    public static final String IMAGE_NAME = "VTVTravelImg";
    public static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";
    private Calendar calendar;
    private AccountViewModel accountViewModel;
    private Account mAccount;
    private Uri mUri;
    private String urlImageProfile;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_personal_information;
    }

    @Override
    public void initView() {
        changeStatus();
        setView();

        accountViewModel = new AccountViewModel();
        getBinding().setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);
        calendar = Calendar.getInstance();
        mAccount = MyApplication.getInstance().getAccount();
        urlImageProfile = mAccount.getImageProfile();
    }

    private void setView() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            getBinding().txtName.setText(account.getFullname());
            getBinding().edtUsername.setText(account.getFullname());
            getBinding().edtFullname.setText(account.getFullname());
            getBinding().edtNumberPhone.setText(account.getMobile());
            getBinding().edtEmail.setText(account.getEmail());


            if (account.getGender() == null) {
                getBinding().tvGender.setText("Nữ");
            } else {
                switch (account.getGender()) {
                    case 0:
                        getBinding().tvGender.setText("Nữ");
                        break;
                    case 1:
                        getBinding().tvGender.setText("Nam");
                        break;
                    case 2:
                        getBinding().tvGender.setText("Khác");
                        break;
                    default:
                        getBinding().tvGender.setText("Nữ");
                        break;
                }
            }

            getBinding().tvBirthOfDay.setText(account.getBirthday());
            getBinding().edtAddress.setText(account.getAddress());
            try {
                if (!"".equals(account.getImageProfile()) && account.getImageProfile() != null) {
                    Glide.with(mActivity).load(account.getImageProfile()).error(R.drawable.f2_defaut_user).into(getBinding().imgAvatar);
                    Glide.with(mActivity).load(account.getImageProfile()).error(R.drawable.f2_defaut_user).into(getBinding().imgAvatarEdit);
                } else {
                    getBinding().imgAvatar.setImageResource(R.drawable.f2_defaut_user);
                    getBinding().imgAvatarEdit.setImageResource(R.drawable.f2_defaut_user);
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(this);
        getBinding().txtEdit.setOnClickListener(this);
        getBinding().txtDone.setOnClickListener(this);
        getBinding().imgMergeInfo.setOnClickListener(this);
        getBinding().imgMergePaper.setOnClickListener(this);
        getBinding().imgMergeBill.setOnClickListener(this);
        getBinding().tvGender.setOnClickListener(this);

        getBinding().imgAvatarEdit.setOnClickListener(this);
        getBinding().tvBirthOfDay.setOnClickListener(this);
        getBinding().txtDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                mActivity.onBackPressed();
                break;
            case R.id.txtEdit:
                changeStatus();
                break;
            case R.id.imgMergeInfo:
                if (checkMergeInfo) {
                    getBinding().layoutInfo.setVisibility(View.VISIBLE);
                    getBinding().imgMergeInfo.setRotation(0);
                } else {
                    getBinding().layoutInfo.setVisibility(GONE);
                    getBinding().imgMergeInfo.setRotation(-90);
                }
                checkMergeInfo = !checkMergeInfo;
                break;
            case R.id.imgMergePaper:
                if (checkMergePaper) {
                    getBinding().layoutPaper.setVisibility(View.VISIBLE);
                    getBinding().imgMergePaper.setRotation(0);
                } else {
                    getBinding().layoutPaper.setVisibility(GONE);
                    getBinding().imgMergePaper.setRotation(-90);
                }
                checkMergePaper = !checkMergePaper;
                break;
            case R.id.imgMergeBill:
                if (checkMergeBill) {
                    getBinding().layoutBill.setVisibility(View.VISIBLE);
                    getBinding().imgMergeBill.setRotation(0);
                } else {
                    getBinding().layoutBill.setVisibility(GONE);
                    getBinding().imgMergeBill.setRotation(-90);
                }
                checkMergeBill = !checkMergeBill;
                break;
            case R.id.tvGender:
                GenderDialog genderDialog = new GenderDialog(this);
                genderDialog.show(getChildFragmentManager(), null);
                break;
            case R.id.imgAvatarEdit:
                editAvatar();
                break;
            case R.id.tvBirthOfDay:
                editBirthOfDay();
                break;
            case R.id.txtDone:
                changeStatus();
                editDone();
                break;
        }
    }

    private void fadeOut(View view) {
        view.setAlpha(1.0f);
        view.animate().alpha(0f).setDuration(500);
    }

    private void fadeIn(View view) {
        view.setAlpha(0f);
        view.animate().alpha(1.0f).setDuration(500);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void clickGender(String gender) {
        try {
            getBinding().tvGender.setText(gender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editDone() {
        String name = getBinding().edtFullname.getText().toString().trim();
        String mobile = getBinding().edtNumberPhone.getText().toString().trim();
        if (null != mAccount.getMobile() && mAccount.getMobile().length() > 0) {
            mobile = null;
        }

        String mail = getBinding().edtEmail.getText().toString().trim();

        Integer gender = null;
        switch (getBinding().tvGender.getText().toString().trim()) {
            case "Nam":
                gender = 0;
                break;
            case "Nữ":
                gender = 1;
                break;
            case "Khác":
                gender = null;
                break;
        }

        String birthday = getBinding().tvBirthOfDay.getText().toString().trim();
        String address = getBinding().edtAddress.getText().toString().trim();
        String cmnd = null;
        String about = null;
        accountViewModel.updateInfo(mAccount.getId(), name, mobile, mail, birthday, gender, address, cmnd, about);
    }

    private void editBirthOfDay() {
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((dialog, year1, monthOfYear, dayOfMonth) -> {
            String day = "" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
            String month = "" + (monthOfYear < 9 ? "0" + (monthOfYear + 1) : monthOfYear + 1);
            String mBirth = day + "/" + month + "/" + year1;
            getBinding().tvBirthOfDay.setText(mBirth);
        }, year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
        datePickerDialog.setMaxDate(calendar);
    }

    private void editAvatar() {
        ChangeAvatarDialog changeAvatarDialog = new ChangeAvatarDialog(this);
        changeAvatarDialog.show(getChildFragmentManager(), null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISTION_AVATAR) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhotoFromGalley();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
//                    upLoadImage(selectedUri);
                } else {
                    Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                Uri selectedUri = fileUri;
                if (null != selectedUri) {
                    startCropActivity(selectedUri);
                } else {
                    Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
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
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getContext().getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME + ".jpg")));
        uCrop.start(getActivity());
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
                            getBinding().imgAvatarEdit.setImageBitmap(bitmap);
                            uploadFile(bitmap);
                        }

                        @Override
                        public void onFailure(@NonNull Exception bitmapWorkerException) {

                        }
                    });

        } else {
            Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }


    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(getActivity(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//        fileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
    }


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

    private void uploadFile(Bitmap bitmap) {
        accountViewModel.updateAvatar(saveBitmap(bitmap), mAccount, Constants.TypeAvatar.PROFILE);
    }

    private File saveBitmap(Bitmap bm) {
        File sd = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File dest = new File(sd, IMAGE_NAME);
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

    private void changeStatus() {
        if (!checkStatus) {
            fadeIn(getBinding().txtEdit);
            fadeOut(getBinding().layoutHeaderEdit);
            fadeOut(getBinding().txtTitleEdit);
            getBinding().txtEdit.setVisibility(View.VISIBLE);
            getBinding().layoutHeaderEdit.setVisibility(View.GONE);
            getBinding().txtTitleEdit.setVisibility(View.GONE);

            fadeOut(getBinding().txtDone);
            fadeIn(getBinding().layoutHeader);
            fadeIn(getBinding().txtTitle);
            getBinding().txtDone.setVisibility(View.GONE);
            getBinding().layoutHeader.setVisibility(View.VISIBLE);
            getBinding().txtTitle.setVisibility(View.VISIBLE);

            getBinding().edtUsername.setEnabled(false);
            getBinding().edtFullname.setEnabled(false);
            getBinding().edtEmail.setEnabled(false);
            getBinding().edtNumberPhone.setEnabled(false);
            getBinding().tvBirthOfDay.setEnabled(false);
            getBinding().edtAddress.setEnabled(false);
            getBinding().tvGender.setEnabled(false);
        } else {
            fadeOut(getBinding().txtEdit);
            fadeIn(getBinding().layoutHeaderEdit);
            fadeIn(getBinding().txtTitleEdit);
            getBinding().txtEdit.setVisibility(View.GONE);
            getBinding().layoutHeaderEdit.setVisibility(View.VISIBLE);
            getBinding().txtTitleEdit.setVisibility(View.VISIBLE);

            fadeIn(getBinding().txtDone);
            fadeOut(getBinding().layoutHeader);
            fadeOut(getBinding().txtTitle);
            getBinding().txtDone.setVisibility(View.VISIBLE);
            getBinding().layoutHeader.setVisibility(View.GONE);
            getBinding().txtTitle.setVisibility(View.GONE);

            getBinding().edtUsername.setEnabled(true);
            getBinding().edtFullname.setEnabled(true);
            getBinding().edtEmail.setEnabled(true);
            getBinding().edtNumberPhone.setEnabled(true);
            getBinding().tvBirthOfDay.setEnabled(true);
            getBinding().edtAddress.setEnabled(true);
            getBinding().tvGender.setEnabled(true);
        }
        checkStatus = !checkStatus;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AccountViewModel && null != o) {
            if (o instanceof AccountResponse) {
                AccountResponse accountResponse = (AccountResponse) o;
                if (accountResponse.isSuccess()) {
                    Account account = accountResponse.getData();
                    account.setToken(mAccount.getToken());
                    MyApplication.getInstance().setAccount(account);
//                        mActivity.updateLogin();
                    setView();
                    Toast.makeText(mActivity, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new OnUpdateAccount());
                } else {
                    Toast.makeText(mActivity, accountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void clickGallery() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISTION_AVATAR);
        } else {
            getPhotoFromGalley();
        }
    }

    @Override
    public void clickCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISTION_AVATAR);
        } else {
            getPhotoFromCamera();
        }
    }

    @Override
    public void clickView() {
        mActivity.getSupportFragmentManager().beginTransaction().add(mActivity.getFrame(), new ViewImageProfileFragment(urlImageProfile)).addToBackStack("TAG").commit();
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.USER_INFO, TrackingAnalytic.ScreenTitle.USER_INFO);
    }
}
