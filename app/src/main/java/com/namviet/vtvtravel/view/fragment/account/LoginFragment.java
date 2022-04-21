package com.namviet.vtvtravel.view.fragment.account;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.KeyboardUtils;
import com.baseapp.utils.L;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentLoginBinding;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToCallNow;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class LoginFragment extends MainFragment implements Observer {

    //hot fix
    private boolean isFromButtonCallNow;


    private FragmentLoginBinding binding;
    private AccountViewModel accountViewModel;
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";

    private PreferenceUtil mPreferenceUtil;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (null != getArguments()) {
                isFromButtonCallNow = getArguments().getBoolean(Constants.IntentKey.IS_FROM_BUTTON_CALL_NOW);
            }
        } catch (Exception e) {

        }
        callbackManager = CallbackManager.Factory.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.rlToolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        initViews(view);

        mPreferenceUtil = PreferenceUtil.getInstance(getContext());
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
//        KeyboardUtils.showKeyboard(mActivity, mActivity.getCurrentFocus());

        accountViewModel = new AccountViewModel();
        binding.setAccountViewModel(accountViewModel);
        accountViewModel.addObserver(this);

        binding.btLogin.setOnClickListener(this);
        binding.tvForgotPass.setOnClickListener(this);
        binding.toolBar.ivBack.setOnClickListener(this);
        updateViews();
    }


    @Override
    protected void updateViews() {
        super.updateViews();
        setLink(binding.tvRegister);

        binding.ivLoginFb.setEnabled(false);
        binding.ivLoginFb.setOnClickListener(this);
        binding.ivLoginGg.setOnClickListener(this);
        binding.btLoginGg.setOnClickListener(this);
        binding.btLoginFb.setReadPermissions(Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_POSTS));
        binding.btLoginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setFacebookData(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
                L.e("onCancel fb ");
            }

            @Override
            public void onError(FacebookException error) {
                L.e("onCancel fb " + error.toString());
            }


        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void update(Observable observable, Object o) {
        dimissDialogLoading();
        if (observable instanceof AccountViewModel) {
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.IS_LOGIN, true);
                        MyApplication.getInstance().setAccount(accountResponse.getData());
                        Log.e("Debuggg"+"LoginFrm", new Gson().toJson(accountResponse.getData()));
                        mActivity.updateLogin();
                        mActivity.onBackPressed();
                        mActivity.switchFragment(SlideMenu.MenuType.HOME_SCREEN);
                        if(isFromButtonCallNow) {
                            EventBus.getDefault().post(new OnLoginSuccessAndGoToCallNow());
                        }
                        EventBus.getDefault().post(new OnLoginSuccessAndUpdateUserView());
                        String token = FirebaseInstanceId.getInstance().getToken();
                        accountViewModel.notificationReg(DeviceUtils.getDeviceId(getContext()), token, "ANDROID");
                        mPreferenceUtil.setValue(Constants.PrefKey.ACCOUNT_ID, accountResponse.getData().getId().toString());
                    } else {
                        showMessage(accountResponse.getMessage());
                        mPreferenceUtil.setValue(Constants.PrefKey.ACCOUNT_ID, "");
                    }
                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                    mPreferenceUtil.setValue(Constants.PrefKey.ACCOUNT_ID, "");
                }
            }

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.btLogin) {
            String phone = binding.edPhone.getText().toString().trim();
            String pass = binding.edPass.getText().toString().trim();
            if (phone.isEmpty()) {
                showMessage(getString(R.string.phone_empty));
            } else if (pass.isEmpty()) {
                showMessage(getString(R.string.pass_empty));
            } else if (!ValidateUtils.isValidPhoneNumber(phone)) {
                showMessage(getString(R.string.phone_or_pass_invalid));
            } else {
                showDialogLoading();
                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.LOGIN, Constants.TypeLogin.MOBILE);
                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.MOBILE, phone);
                PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.PASSWORD, pass);
//                KeyboardUtils.hideKeyboard1(mActivity);
                accountViewModel.login(StringUtils.isPhoneValidateV2(phone, 84), pass, PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.DEVICE_TOKEN, ""));
            }
        } else if (view.getId() == R.id.ivLoginFb) {
            binding.btLoginFb.performClick();
        } else if (view.getId() == R.id.ivLoginGg) {
            signIn();
        } else if (view.getId() == R.id.tvForgotPass) {
            mActivity.switchFragment(SlideMenu.MenuType.RESET_PASS_SCREEN);
        } else if (view.getId() == R.id.ivBack) {
            mActivity.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, updateInfo UI appropriately
                L.e(TAG + " Google sign in failed", e);
                // [START_EXCLUDE]
//                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        L.e(TAG + " firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, updateInfo UI with the signed-in user's information
                            L.d(TAG + " signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            L.e(TAG + " " + user.getPhotoUrl().toString());
                            PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.LOGIN, Constants.TypeLogin.GOOGLE);
                            PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.GOOGLE_ID, user.getUid());
                            accountViewModel.loginGoogle(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        L.e(TAG + " signOut");
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            signOut();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setLink(TextView tvRegister) {
        Link link = new Link(getString(R.string.register_now_click));
        link.setTextColor(getResources().getColor(R.color.colorPrimary))
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.white))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        mActivity.switchFragment(SlideMenu.MenuType.REGISTER_SCREEN);
                    }
                });


        if (null != tvRegister) {
            LinkBuilder.on(tvRegister)
                    .addLink(link)
                    .build();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.LOGIN, Constants.TypeLogin.FACEBOOK);
                            PreferenceUtil.getInstance(getContext()).setValue(Constants.PrefKey.FACEBOOK_ID, id);
                            accountViewModel.loginFacebook(id, firstName + " " + lastName, email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
        } catch (Exception e) {

        }
    }
}
