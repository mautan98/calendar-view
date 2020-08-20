package com.namviet.vtvtravel.view.fragment.home;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.baseapp.utils.L;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ShareMomentAdapter;
import com.namviet.vtvtravel.adapter.TagMomentAdapter;
import com.namviet.vtvtravel.adapter.TypeMomentAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentShareMomentBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.CustomGallery;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.ShareMomentResponse;
import com.namviet.vtvtravel.response.TypeMomentResponse;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.ShareMomentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShareMomentFragment extends MainFragment implements Observer {
    private FragmentShareMomentBinding binding;
    private int PICK_IMAGE_MULTIPLE = 200;
    private int PICK_IMAGE = 100;
    private CustomGallery oneImage;
    private ArrayList<CustomGallery> imageList = new ArrayList<>();
    private ShareMomentAdapter shareMomentAdapter;
    private TypeMomentAdapter typeMomentAdapter;
    private TagMomentAdapter tagMomentAdapter;
    private List<Filter> filterList = new ArrayList<>();
    private ArrayList<String> tagsList = new ArrayList<>();
    private ShareMomentViewModel shareMomentViewModel;
    private String linkWeb = WSConfig.HOST + "moments/editor/embed?NVTRAVEL-TOKEN=";
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 50;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private String mDescriptions = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_share_moment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        shareMomentViewModel = new ShareMomentViewModel(getContext());
        shareMomentViewModel.addObserver(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvShareMoment.setOnClickListener(this);
        binding.llAddCover.setOnClickListener(this);
        binding.tvAddPhoto.setOnClickListener(this);
        binding.rvPhotos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPhotos.setNestedScrollingEnabled(false);
        binding.edTags.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String tag = binding.edTags.getText().toString().trim();
                    if (tag.length() > 0) {
                        tagsList.add(tag);
                        tagMomentAdapter.notifyDataSetChanged();
                        binding.edTags.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        shareMomentAdapter = new ShareMomentAdapter(getContext(), imageList);
        binding.rvPhotos.setAdapter(shareMomentAdapter);

        binding.rvTypeMoment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        typeMomentAdapter = new TypeMomentAdapter(getContext(), filterList);
        binding.rvTypeMoment.setAdapter(typeMomentAdapter);

        binding.rvTags.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tagMomentAdapter = new TagMomentAdapter(getContext(), tagsList);
        binding.rvTags.setAdapter(tagMomentAdapter);
        Account account = MyApplication.getInstance().getAccount();
        binding.webDescription.loadUrl(linkWeb + account.getToken());
        binding.webDescription.getSettings().setJavaScriptEnabled(true);
        binding.webDescription.getSettings().setDomStorageEnabled(true);
        binding.webDescription.getSettings().setSupportZoom(false);
        binding.webDescription.getSettings().setAllowFileAccess(true);
        binding.webDescription.getSettings().setAllowFileAccess(true);
        binding.webDescription.getSettings().setAllowContentAccess(true);
        binding.webDescription.setWebChromeClient(new WebChromeClient() {

            protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e) {
                    uploadMessage = null;
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });

        updateViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvAddPhoto) {
            pickMultiPhoto();
        } else if (view == binding.llAddCover) {
            pickPhoto();
        } else if (view == binding.tvShareMoment) {
            final String title = binding.tvTitle.getText().toString().trim();
            final String shortDes = binding.tvShortDes.getText().toString().trim();
            if (null == oneImage) {
                showMessage(getString(R.string.add_cover));
            } else if (title.equals("")) {
                showMessage(getString(R.string.add_title));
            } else if (shortDes.equals("")) {
                showMessage(getString(R.string.add_short_des));
            } else if (!existNameFile(imageList)) {
                showMessage(getString(R.string.not_have_name_file));
            } else {
                showDialogLoading();
                binding.webDescription.evaluateJavascript(
                        "(function(){return window.document.getElementById('inputContent').innerHTML})();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                mDescriptions = removeUTFCharacters(html).toString();
                                mDescriptions = mDescriptions.substring(1);
                                mDescriptions = mDescriptions.substring(0, mDescriptions.length() - 1);
                                mDescriptions = mDescriptions.replace("\\n", "").replace("\\r", "").replace("\\", "");
                                Log.d("LamLV: " , mDescriptions);
                                shareMomentViewModel.shareMoment(oneImage, shareMomentAdapter.getPhotoList(), title, shortDes, mDescriptions, tagsList, typeMomentAdapter.getItemSelect().getValue());
                            }
                        });

            }

        }


    }

    public static StringBuffer removeUTFCharacters(String data) {
        Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher m = p.matcher(data);
        StringBuffer buf = new StringBuffer(data.length());
        while (m.find()) {
            String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
            m.appendReplacement(buf, Matcher.quoteReplacement(ch));
        }
        m.appendTail(buf);
        return buf;
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        shareMomentViewModel.loadTypeMoment();
    }


    private void pickMultiPhoto() {
        Intent i = new Intent(Constants.Action.ACTION_MULTIPLE_PICK);
        startActivityForResult(i, PICK_IMAGE_MULTIPLE);
    }

    private void pickPhoto() {
        Intent i = new Intent(Constants.Action.ACTION_PICK);
        startActivityForResult(i, PICK_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            oneImage = data.getParcelableExtra(Constants.IntentKey.ONE_PATH);
            if (null != oneImage) {
                setImageFile(oneImage.getSdcardPath(), binding.ivCover);
            }
        } else if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK) {
            ArrayList<CustomGallery> selected = data.getParcelableArrayListExtra(Constants.IntentKey.MUTI_PATH);
            imageList.addAll(selected);
            shareMomentAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMessage == null)
                return;
            uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            uploadMessage = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof ShareMomentViewModel) {
            if (o instanceof TypeMomentResponse) {
                TypeMomentResponse response = (TypeMomentResponse) o;
                filterList.addAll(response.getData());
                typeMomentAdapter.notifyDataSetChanged();
            } else if (o instanceof ShareMomentResponse) {
                ShareMomentResponse response = (ShareMomentResponse) o;
                if (response.getCode().equals("SUCCESS")) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    createSuccess(getString(R.string.share_moment_success), 0);
                } else {
                    showMessage(getString(R.string.share_moment_faile));
                }
            }  else if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            }
        }
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private boolean existNameFile(ArrayList<CustomGallery> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAboutPhoto().equals("")) {
                return false;
            }
        }
        return true;
    }
}
