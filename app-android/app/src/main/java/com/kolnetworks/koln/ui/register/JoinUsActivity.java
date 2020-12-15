package com.kolnetworks.koln.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.customview.ProgressDialogUtil;
import com.kolnetworks.koln.ui.login.LoginActivity;
import com.kolnetworks.koln.ui.main.MainActivity;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinUsActivity extends BaseActivity {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.mainTitle)
    TextView mainTitle;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;
    @BindView(R.id.btnEmailVerify)
    TextView btnEmailVerify;
    @BindView(R.id.tv_copyright)
    TextView tvCopyright;

    private JoinUsViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_us;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(JoinUsViewModel.class);

        viewModel.getMldApiError().observe(this, errorMsg->{
            dismissProgressDialog();
            ToastUtil.showFailedImgInToast(errorMsg);
        });

        viewModel.getMldUser().observe(this, bean -> {
            setOnDialogClickListener(new OnDialogClickListener() {
                @Override
                public void onPositiveClick() {
                    finish();
                }

                @Override
                public void onNegativeClick() {

                }
            });
            dismissProgressDialog();
            showAlertDialog(getString(R.string.register_success), getString(R.string.register_gt_to_email), getString(R.string.register_confirm), "");
        });

    }

    @Override
    protected void configViews() {

        addDisposable(RxView.clicks(btnEmailVerify)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {

                    if (!etEmail.getText().toString().matches(Constant.EMAIL_REG)) {
                        ToastUtil.showFailedImgInToast("Email格式錯誤！");
                        return;
                    }

                    if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                        ToastUtil.showFailedImgInToast("二次密碼輸入不相同！");
                        return;
                    }

                    if (etPassword.getText().toString().length() <6){
                        ToastUtil.showFailedImgInToast("密碼長度須大於6位數！");
                        return;
                    }

                    showProgressDialog("");
                    viewModel.register(
                            etEmail.getText().toString(),
                            etPassword.getText().toString(),
                            etPasswordConfirm.getText().toString());
                }));

    }

}