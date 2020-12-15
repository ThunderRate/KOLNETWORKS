package com.kolnetworks.koln.ui.userinfo;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.util.DateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class UserBasicFragment extends BaseFragment {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.birth)
    TextView birth;
    @BindView(R.id.tvBirth)
    TextView tvBirth;
    @BindView(R.id.line)
    TextView line;
    @BindView(R.id.etLine)
    EditText etLine;
    @BindView(R.id.bottom)
    CardView bottom;
    @BindView(R.id.btnLogin)
    TextView btnUpdate;

    private DatePickerPopWin pickerPopWin;
    private long birthStamp;
    private  UserViewModel viewModel;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user_basic;
    }

    @Override
    protected void initData() {
        initViewModel();

        pickerPopWin = new DatePickerPopWin.Builder(context, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                birthStamp = DateUtil.timeToTimestamp(dateDesc + " 00:00:00");
                tvBirth.setText(dateDesc);
            }
        }).textConfirm("CONFIRM") //text of confirm button
                .textCancel("CANCEL") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                .minYear(1900) //min year in loop
                .maxYear(2020) // max year in loop
                .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                .dateChose("2005-04-11") // date chose when init popwindow
                .build();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        viewModel.getMldUser().observe(this, bean -> {
            tvName.setText(bean.getDisplay_name());
            tvEmail.setText(bean.getEmail());
            etPhone.setText(bean.getPhone());
            etAddress.setText(bean.getAddress());
            tvBirth.setText(DateUtil.getDate(bean.getBirthday()));
            etLine.setText(bean.getLine_id());
            birthStamp = Long.parseLong(bean.getBirthday() + "000");
        });

        viewModel.getMldUserBasic().observe(this, bean -> {
            ((UserInfoActivity)requireActivity()).dismissProgressDialog();
            tvName.setText(bean.getDisplay_name());
            tvEmail.setText(bean.getEmail());
            etPhone.setText(bean.getPhone());
            etAddress.setText(bean.getAddress());
            tvBirth.setText(DateUtil.getDate(bean.getBirthday()));
            etLine.setText(bean.getLine_id());
            birthStamp = Long.parseLong(bean.getBirthday() + "000");
        });
    }

    @Override
    protected void configViews() {
        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(btnUpdate)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v->{
                    ((UserInfoActivity)requireActivity()).showProgressDialog("");
                    viewModel.updateUserData(
                            etPhone.getText().toString(),
                            etAddress.getText().toString(),
                            birthStamp / 1000,
                            etLine.getText().toString()
                    );
                })
        );

        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(tvBirth)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v->{
                    pickerPopWin.showPopWin(requireActivity());
                })
        );




    }
}