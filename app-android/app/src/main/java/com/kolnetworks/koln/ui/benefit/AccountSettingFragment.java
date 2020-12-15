package com.kolnetworks.koln.ui.benefit;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.main.MainActivity;
import com.kolnetworks.koln.ui.main.MainViewModel;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class AccountSettingFragment extends BaseFragment {

    @BindView(R.id.etBankCode)
    EditText etBankCode;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etBankAccount)
    EditText etBankAccount;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.bottom)
    CardView bottom;
    @BindView(R.id.etBranchCode)
    EditText etBranchCode;
    private MainViewModel viewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_account_setting;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    @Override
    protected void configViews() {
        ((MainActivity) getActivity()).addDisposable(RxView.clicks(btnSave)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((MainActivity) requireActivity()).showProgressDialog("");
                    viewModel.updateBankAccount(
                            etBankCode.getText().toString(),
                            etBranchCode.getText().toString(),
                            etName.getText().toString(),
                            etBankAccount.getText().toString());
                }));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        viewModel.getMldUserBank().observe(this, userBean -> {
            etBankCode.setText(userBean.getBank_code());
            etBranchCode.setText(userBean.getBranch_code());
            etName.setText(userBean.getAccount_name());
            etBankAccount.setText(userBean.getAccount_no().toString());
            viewModel.loadUser();
        });


        viewModel.getMldUser().observe(this, userBean -> {
            if (!TextUtils.isEmpty(userBean.getBank_code())) {
                etBankCode.setText(String.valueOf(userBean.getBank_code()));
            }
            if (!TextUtils.isEmpty(userBean.getBranch_code())) {
                etBranchCode.setText(String.valueOf(userBean.getBranch_code()));
            }
            if (!TextUtils.isEmpty(userBean.getAccount_name())) {
                etName.setText(String.valueOf(userBean.getAccount_name()));
            }
            if (!TextUtils.isEmpty(userBean.getAccount_no())) {
                etBankAccount.setText(String.valueOf(userBean.getAccount_no()));
            }
        });

        viewModel.getMldBankUpdateFinish().observe(this, canFinish -> {
            if (canFinish) {
                ((MainActivity) requireActivity()).dismissProgressDialog();
            }
        });
//        viewModel.loadCashFlow();
//
//        viewModel.getMldCashFlow().observe(this, beans -> {
//            Log.d("JJJ", "list size : " + beans.size());
//        });
    }

}