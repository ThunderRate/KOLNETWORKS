package com.kolnetworks.koln.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.ui.bindtag.BindTagActivity;
import com.kolnetworks.koln.ui.forgotpassword.ForgotPasswordActivity;
import com.kolnetworks.koln.ui.forgotpassword.ForgotPasswordViewModel;
import com.kolnetworks.koln.ui.main.MainActivity;
import com.kolnetworks.koln.ui.register.JoinUsActivity;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.mainTitle)
    TextView mainTitle;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnMemberRule)
    TextView btnMemberRule;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    @BindView(R.id.btnJoinUs)
    TextView btnJoinUs;
    @BindView(R.id.tv_copyright)
    TextView tvCopyright;
    @BindView(R.id.btnForgot)
    ImageView btnForgot;

    private LoginViewModel viewModel;
    public static String HAS_IG_URL = "hasigurl";

    private Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBundleExtra("bundle")!=null){
            this.bundle = getIntent().getBundleExtra("bundle");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getFirebaseToken();

        viewModel.getMldIsDataComplete().observe(this, isUserDataComplete -> {
            dismissProgressDialog();
            Intent intent = new Intent();
            if (isUserDataComplete) {
                //intent.setClass(this, BindTagActivity.class);
                intent.setClass(this, MainActivity.class);
            } else {
                intent.putExtra(HAS_IG_URL, !viewModel.getIgUrl().isEmpty());
                intent.setClass(this, BindTagActivity.class);
            }
            if (bundle!=null){
                intent.putExtra("bundle",bundle);
            }
            startActivity(intent);
            finish();
        });

        viewModel.getMldApiError().observe(this, msg -> {
            dismissProgressDialog();
            if (!TextUtils.isEmpty(msg)){
                ToastUtil.showFailedImgInToast(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void configViews() {
        Intent it = new Intent();

//        if (!SPApi.getToken().isEmpty()) {
//            showProgressDialog("");
//            viewModel.getUser();
//        }
//
//        if (!SPApi.getAccount().isEmpty() && !SPApi.getPassword().isEmpty()) {
//            etEmail.setText(SPApi.getAccount());
//            //etPassword.setText(SPApi.getPassword());
//        }


        // 登入
        addDisposable(RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                            showProgressDialog("");
                            viewModel.login(etEmail.getText().toString(), etPassword.getText().toString());
                        }
                ));

        // 加入我們
        addDisposable(RxView.clicks(btnJoinUs)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    it.setClass(this, JoinUsActivity.class);
                    startActivity(it);
                }));

        // 使用者條款
        addDisposable(RxView.clicks(btnMemberRule)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    it.setAction(Intent.ACTION_VIEW);
                    it.setData(Uri.parse("https://www.kolnetworks.com/page/privacy-policy"));
                    startActivity(it);
                }));

        // 忘記密碼
        addDisposable(RxView.clicks(btnForgot)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    it.setClass(this, ForgotPasswordActivity.class);
                    startActivity(it);
                }));
    }
}