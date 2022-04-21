package com.namviet.vtvtravel.view.fragment.account;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baseapp.menu.SlideMenu;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.AccountPageAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentAccountBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends MainFragment implements Observer {

    private FragmentAccountBinding binding;
    private AccountViewModel loginViewModel;
    private static AccountFragment accountFragment;
    private ArrayList<Fragment> fragmentArrayList;
    private AccountPageAdapter pagerAdapter;
    private Account mAccount;
    private int position = 0;
    private String typeAvatar;

    public int REQUEST_PERMISTION_AVATAR = 20;
    public static int REQUEST_CAMERA = 100;
    public static int REQUEST_SELECT_PICTURE = 0x01;
    public static Uri fileUri;
    public static final String IMAGE_NAME = "VTVTravelImg";
    public static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";

    public static AccountFragment getInstance() {
        if (null == accountFragment) {
            accountFragment = new AccountFragment();
        }
        return accountFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            position = getArguments().getInt(Constants.IntentKey.KEY_POSITION);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
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
        loginViewModel = new AccountViewModel();
        binding.setAccountViewModel(loginViewModel);
        loginViewModel.addObserver(this);

        binding.ivBack.setOnClickListener(this);
        binding.tvShareMoment.setOnClickListener(this);
        binding.rlAvatar.setOnClickListener(this);
        binding.imvBackground.setOnClickListener(this);
        binding.rlCover.setOnClickListener(this);
        updateViews();
        setUiTab();
    }

    private void setUiTab() {
        fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(AccountVideoFragment.newInstance());
        fragmentArrayList.add(AccountScheduleFragment.newInstance());
        fragmentArrayList.add(AccountTourFragment.newInstance());

        pagerAdapter = new AccountPageAdapter(mActivity.getSupportFragmentManager(), mActivity, fragmentArrayList);
        binding.viewPager.setAdapter(pagerAdapter);

        binding.tabAccount.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        binding.tabAccount.setViewPager(binding.viewPager);
        binding.tabAccount.setCurrentTab(position);
        FragmentManager faFragmentManager = mActivity.getSupportFragmentManager();
        if (faFragmentManager.getBackStackEntryCount() > 2) {
            faFragmentManager.popBackStack();
        }
    }


    @Override
    protected void updateViews() {
        super.updateViews();
        mAccount = MyApplication.getInstance().getAccount();
        if (null != mAccount.getFullname()) {
            binding.tvName.setText(mAccount.getFullname());
        } else {
            binding.tvName.setText("");
        }
        if (null != mAccount.getEmail()) {
            binding.tvMail.setText(mAccount.getEmail());
        } else {
            binding.tvMail.setText("");
        }
        if (null != mAccount) {
            setImageUrl(mAccount.getImageProfile(), binding.avatar);
            if (!"".equals(mAccount.getImageBackground())) {
                setImageUrl(mAccount.getImageBackground(), binding.ivCover);
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvShareMoment) {
            mActivity.switchFragment(SlideMenu.MenuType.SHARE_MOMENT_SCREEN);
        } else if (view == binding.rlAvatar) {
            typeAvatar = Constants.TypeAvatar.PROFILE;
            handleUpdateAvatar();
        } else if (view == binding.imvBackground) {
            typeAvatar = Constants.TypeAvatar.BACKGROUND;
            handleUpdateAvatar();
        }

    }

    private void handleUpdateAvatar() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISTION_AVATAR);
        } else {
            PopupMenu dropDownMenu;
            if (Constants.TypeAvatar.PROFILE.equals(typeAvatar)) {
                dropDownMenu = new PopupMenu(getContext(), binding.rlAvatar);
            } else {
                dropDownMenu = new PopupMenu(getContext(), binding.imvBackground);
            }
            dropDownMenu.getMenuInflater().inflate(R.menu.menu_select_photo, dropDownMenu.getMenu());
            dropDownMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.action_gallery) {
                    getPhotoFromGalley();
                } else if (menuItem.getItemId() == R.id.action_camera) {
                    getPhotoFromCamera();
                }
                return true;
            });
            dropDownMenu.show();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AccountViewModel) {
            if (loginViewModel.getAccount() != null) {
                MyApplication.getInstance().setAccount(loginViewModel.getAccount());
                Log.e("Debuggg"+"AccountFragment", new Gson().toJson(loginViewModel.getAccount()));
                // mActivity.onBackPressed();
                mActivity.updateLogin();
            }
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        Account account = accountResponse.getData();
                        account.setToken(mAccount.getToken());
                        MyApplication.getInstance().setAccount(account);
                        Log.e("Debuggg"+"AccountFragment2", new Gson().toJson(account));
                        mActivity.updateLogin();
                        Toast.makeText(mActivity, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        showMessage(accountResponse.getMessage());
                    }
                }
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
                        public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String imageInputPath, @Nullable String imageOutputPath) {
                            if (Constants.TypeAvatar.PROFILE.equals(typeAvatar)) {
                                binding.avatar.setImageBitmap(bitmap);
                            } else {
                                binding.ivCover.setImageBitmap(bitmap);
                            }
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

    private void uploadFile(Bitmap bitmap) {
        loginViewModel.updateAvatar(saveBitmap(bitmap), mAccount, typeAvatar);
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
        fileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

}
