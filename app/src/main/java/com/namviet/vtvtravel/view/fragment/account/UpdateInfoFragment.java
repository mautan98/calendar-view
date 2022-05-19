package com.namviet.vtvtravel.view.fragment.account;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.baseapp.utils.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentUpdateInfoBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

public class UpdateInfoFragment extends MainFragment implements Observer {
    private FragmentUpdateInfoBinding binding;
    public int REQUEST_PERMISTION_AVATAR = 20;
    public static int REQUEST_CAMERA = 100;
    public static int REQUEST_SELECT_PICTURE = 0x01;
    public static Uri fileUri;
    public static final String IMAGE_NAME = "VTVTravelImg";
    public static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";
    private Calendar calendar;
    private AccountViewModel accountViewModel;
    private Account mAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_info, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        accountViewModel = new AccountViewModel();
        binding.setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);

        binding.toolBar.ivSearch.setVisibility(View.GONE);
        binding.toolBar.tvTitle.setText(getString(R.string.update_info));
        binding.toolBar.ivBack.setOnClickListener(this);

        binding.rlAvatar.setOnClickListener(this);
        binding.edtBirth.setOnClickListener(this);
        binding.btSave.setOnClickListener(this);
        binding.btCancel.setOnClickListener(this);
        calendar = Calendar.getInstance();
        updateViews();
        initAvatar();
    }

    private void initAvatar(){
        Account account = MyApplication.getInstance().getAccount();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.user);
        if (null != account && account.isLogin()) {
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(account.getImageProfile()).into(binding.avatar);
        }else {
            Glide.with(this).setDefaultRequestOptions(requestOptions).load("").into(binding.avatar);
        }
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        mAccount = MyApplication.getInstance().getAccount();
        if (null != mAccount.getMobile() && mAccount.getMobile().length() > 0) {
            binding.rlPhone.setVisibility(View.GONE);
        } else {
            binding.rlPhone.setVisibility(View.VISIBLE);
        }
        if (null != mAccount.getFullname()) {
            binding.name.setText(mAccount.getFullname());
            binding.edtFullName.setText(mAccount.getFullname());
        }
        if (null != mAccount.getEmail()) {
            binding.edtEmail.setText(mAccount.getEmail());
        }
        if (null != mAccount.getBirthday()) {
            binding.edtBirth.setText(mAccount.getBirthday().split(" ")[0]);
        }
        if (null != mAccount.getGender()) {
            if (mAccount.getGender() == 0) {
                binding.rdoFemale.setChecked(true);
            } else {
                binding.rdoMale.setChecked(true);
            }
        }
        if (null != mAccount.getAddress()) {
            binding.edtAddress.setText(mAccount.getAddress());
        }
        if (null != mAccount.getIcppNumber()) {
            binding.edtCmnd.setText(mAccount.getIcppNumber());
        }
        if (null != mAccount.getAbout()) {
            binding.edtAboutMe.setText(mAccount.getAbout());
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            try {
                KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
            } catch (Exception e) {

            }
            mActivity.onBackPressed();
        } else if (view == binding.rlAvatar) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISTION_AVATAR);
            } else {
                PopupMenu dropDownMenu = new PopupMenu(getContext(), binding.rlAvatar);
                dropDownMenu.getMenuInflater().inflate(R.menu.menu_select_photo, dropDownMenu.getMenu());
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_gallery) {
                            getPhotoFromGalley();
                        } else if (menuItem.getItemId() == R.id.action_camera) {
                            getPhotoFromCamera();
                        }
                        return true;
                    }
                });
                dropDownMenu.show();
            }

        } else if (view == binding.edtBirth) {
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                    String day = "" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                    String month = "" + (monthOfYear < 9 ? "0" + (monthOfYear + 1) : monthOfYear + 1);
                    String mBirth = day + "/" + month + "/" + year;
                    binding.edtBirth.setText(mBirth);
                }
            }, year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
            datePickerDialog.setMaxDate(calendar);
        } else if (view == binding.btSave) {
            String name = binding.edtFullName.getText().toString().trim();
            String mobile = binding.edtPhone.getText().toString().trim();
            if (null != mAccount.getMobile() && mAccount.getMobile().length() > 0) {
                mobile = null;
            }

            String mail = binding.edtEmail.getText().toString().trim();
            Integer gender = null;
            if (binding.rdoMale.isChecked()) {
                gender = 1;
            }
            if (binding.rdoFemale.isChecked()) {
                gender = 0;
            }
            String birthday = binding.edtBirth.getText().toString().trim();
            String address = binding.edtAddress.getText().toString().trim();
            String cmnd = binding.edtCmnd.getText().toString().trim();
            String about = binding.edtAboutMe.getText().toString().trim();
            accountViewModel.updateInfo(mAccount.getId(), name, mobile, mail, birthday, gender, address, cmnd, about);
            showDialogLoading();
        } else if (view == binding.btCancel){
            mActivity.onBackPressed();
            try {
                KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof AccountViewModel) {
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        Account account = accountResponse.getData();
                        account.setToken(mAccount.getToken());
                        MyApplication.getInstance().setAccount(account);
                        Log.e("Debuggg"+"UpdateInfoFrm", new Gson().toJson(account));
                        mActivity.updateLogin();
                        updateViews();
                        Toast.makeText(mActivity, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        showMessage(accountResponse.getMessage());
                    }
                }
            }
            if (accountViewModel.getRegister() != null) {

            }
        }
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

    private Uri mUri;

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
                            binding.avatar.setImageBitmap(bitmap);
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
            try {
                Toast.makeText(getActivity(), cropError.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {

            }
        } else {
            try {
                Toast.makeText(getActivity(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }
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
        fileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public String getBitMap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void deletePhoto(Uri photoPath) {
        if (null != photoPath) {
            getActivity().getContentResolver().delete(photoPath, null, null);
            try {
                if (Build.VERSION.SDK_INT < 19) {
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
                } else {
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
                    MediaScannerConnection.scanFile(getActivity(), new String[]{new File(Environment.getExternalStorageDirectory().toString()).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(Bitmap bitmap) {
        accountViewModel.updateAvatar(saveBitmap(bitmap), mAccount, Constants.TypeAvatar.PROFILE);
    }

    private File saveBitmap(Bitmap bm) {
        File sd = Environment.getExternalStorageDirectory();
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

    private void deletePhoto(String path) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{path});
    }
}
