package com.kolnetworks.koln.ui.bindtag;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class StepThreeFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rbMan)
    RadioButton rbMan;
    @BindView(R.id.rbWomen)
    RadioButton rbWomen;
    @BindView(R.id.rbLgb)
    RadioButton rbLgb;
    @BindView(R.id.rbNoAnswer)
    RadioButton rbNoAnswer;
    @BindView(R.id.mRadioGroupGender)
    RadioGroup mRadioGroupGender;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.mRadioGroupPlace)
    RadioGroup mRadioGroupPlace;
    @BindView(R.id.btnNext)
    TextView btnNext;
    @BindView(R.id.btnPre)
    TextView btnPre;
    @BindView(R.id.rbTwn)
    RadioButton rbTwn;
    @BindView(R.id.rbJpn)
    RadioButton rbJpn;
    @BindView(R.id.rbUsa)
    RadioButton rbUsa;
    private NavController navController;
    private BindTagViewModel viewModel;
    private int gender = 1; // man:1 woman:2 LGBTQ:3 NO:4
    private int region = 1; // tw:1 jp:2 usa:3

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_three;
    }

    @Override
    protected void initData() {
        initViewModel();
        navController = NavHostFragment.findNavController(this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(BindTagViewModel.class);
//        viewModel.getMldCanFinishStep3().observe(this, canFinish -> {
//            if (canFinish){
//                navController.navigate(R.id.action_stepThreeFragment_to_stepFourFragment);
//            }
//        });
    }

    @Override
    protected void configViews() {

        mRadioGroupGender.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbMan:
                    gender = 1;
                    break;
                case R.id.rbWomen:
                    gender = 2;
                    break;
                case R.id.rbLgb:
                    gender = 3;
                    break;
                case R.id.rbNoAnswer:
                    gender = 4;
                    break;
            }
        });

        mRadioGroupPlace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbTwn:
                        region = 1;
                        break;
                    case R.id.rbJpn:
                        region = 2;
                        break;
                    case R.id.rbUsa:
                        region = 3;
                        break;
                }
            }
        });

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnPre)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            navController.popBackStack(R.id.stepTwoFragment2, false);
                        })
        );

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnNext)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            viewModel.setUserInfoDataStepThree(region, gender);
                            navController.navigate(R.id.action_stepThreeFragment_to_stepFourFragment);
                        })
        );
    }
}