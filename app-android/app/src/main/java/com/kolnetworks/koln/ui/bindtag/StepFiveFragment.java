package com.kolnetworks.koln.ui.bindtag;

import android.content.Intent;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class StepFiveFragment extends BaseFragment {
    @BindView(R.id.btnDone)
    TextView btnDone;
    private NavController navController;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_five;
    }

    @Override
    protected void initData() {
        navController = NavHostFragment.findNavController(this);

    }

    @Override
    protected void configViews() {
        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnDone)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            Intent intent = new Intent();
                            intent.setClass(requireActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        })
        );
    }
}