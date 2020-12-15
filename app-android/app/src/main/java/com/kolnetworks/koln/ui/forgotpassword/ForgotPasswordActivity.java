package com.kolnetworks.koln.ui.forgotpassword;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnSendVerify)
    TextView btnSendVerify;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        viewModel.getMldApiError().observe(this, msg -> {
            dismissProgressDialog();
            ToastUtil.showFailedImgInToast(msg);
        });

        viewModel.getMldCanFinish().observe(this, canFinish->{
            if (canFinish){
                setOnDialogClickListener(new OnDialogClickListener() {
                    @Override
                    public void onPositiveClick() {
                        ForgotPasswordActivity.this.finish();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });

                showAlertDialog("發送成功","驗證信箱後我們將為您重設密碼", "確定","");
            }
        });
    }

    @Override
    protected void configViews() {

        addDisposable(RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    finish();
                }));

        addDisposable(RxView.clicks(btnSendVerify)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    if (etEmail.getText().toString().isEmpty()) {
                        ToastUtil.showFailedImgInToast(getString(R.string.forgot_enter_email));
                        return;
                    }
                    showProgressDialog("");
                    viewModel.sendVerifyEmail(etEmail.getText().toString());
                }));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}