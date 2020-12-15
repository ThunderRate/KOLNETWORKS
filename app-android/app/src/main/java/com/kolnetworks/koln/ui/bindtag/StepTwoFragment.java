package com.kolnetworks.koln.ui.bindtag;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.util.DateUtil;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class StepTwoFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.tvBirth)
    TextView tvBirth;
    @BindView(R.id.btnNext)
    TextView btnNext;
    @BindView(R.id.btnPre)
    TextView btnPre;
    private NavController navController;
    private DatePickerPopWin pickerPopWin;
    private BindTagViewModel viewModel;
    private long birthStamp;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_two;
    }

    @Override
    protected void initData() {
        initViewModel();
        navController = NavHostFragment.findNavController(this);

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
        viewModel = new ViewModelProvider(requireActivity()).get(BindTagViewModel.class);
    }

    @Override
    protected void configViews() {
        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnPre)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            navController.popBackStack();
                        })
        );

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnNext)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {

                            if (etName.getText().toString().isEmpty()) {
                                ToastUtil.showFailedImgInToast("請輸入公司名稱或真實姓名。");
                                return;
                            }

                            if (etPhone.getText().toString().isEmpty() || !etPhone.getText().toString().matches(Constant.TEL_REG_TW)) {
                                ToastUtil.showFailedImgInToast("請確認手機格式。");
                                return;
                            }

                            if (tvBirth.getText().toString().isEmpty()) {
                                ToastUtil.showFailedImgInToast("請輸入生日。");
                                return;
                            }

                            viewModel.setUserInfoDataStepTwo(etName.getText().toString(),
                                    etPhone.getText().toString(),
                                    birthStamp / 1000);

                            navController.navigate(R.id.action_stepTwoFragment2_to_stepThreeFragment);

                        })
        );

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(tvBirth)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            pickerPopWin.showPopWin(requireActivity());
                        })
        );
    }
}