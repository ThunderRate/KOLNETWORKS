package com.kolnetworks.koln.ui.benefit;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.main.MainActivity;
import com.kolnetworks.koln.ui.main.MainViewModel;
import com.kolnetworks.koln.ui.paydetail.PayDetailActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class OverAllFragment extends BaseFragment {


    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tvServiceFeeHint)
    TextView tvServiceFeeHint;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.tvUnPayMoney)
    TextView tvUnPayMoney;
    @BindView(R.id.tvPaidMoney)
    TextView tvPaidMoney;
    @BindView(R.id.tvServiceFee)
    TextView tvServiceFee;
    @BindView(R.id.tvCanDraw)
    TextView tvCanDraw;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.bottom)
    CardView bottom;

    private MainViewModel viewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_over_all;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    @Override
    protected void configViews() {
        ((MainActivity) getActivity()).addDisposable(
                RxView.clicks(textView1)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            Intent intent = new Intent();
                            intent.putExtra("benefit",1);
                            intent.setClass(requireActivity(), PayDetailActivity.class);
                            startActivity(intent);
                        }));

        ((MainActivity) getActivity()).addDisposable(
                RxView.clicks(tvUnPayMoney)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            Intent intent = new Intent();
                            intent.putExtra("benefit",1);
                            intent.setClass(requireActivity(), PayDetailActivity.class);
                            startActivity(intent);
                        }));

        ((MainActivity) getActivity()).addDisposable(
                RxView.clicks(textView)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            Intent intent = new Intent();
                            intent.putExtra("benefit",2);
                            intent.setClass(requireActivity(), PayDetailActivity.class);
                            startActivity(intent);
                        }));

        ((MainActivity) getActivity()).addDisposable(
                RxView.clicks(tvPaidMoney)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            Intent intent = new Intent();
                            intent.putExtra("benefit",2);
                            intent.setClass(requireActivity(), PayDetailActivity.class);
                            startActivity(intent);
                        }));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        viewModel.getMldCashFlow().observe(this, bean -> {
            int totalPaidIncome = 0;
            int totalUnpaidIncome = 0;
            int totalCommission = 0;
            int canDraw = 0;

            for (int i = 0; i < bean.getCashflow().size(); i++) {
                if (bean.getCashflow().get(i).getOrder().getStatus() == 1) { //未撥款
                    totalUnpaidIncome += (bean.getCashflow().get(i).getBasic_income() + bean.getCashflow().get(i).getBasic_income_commission());
                }
                if (bean.getCashflow().get(i).getOrder().getStatus() == 2) { //已撥款
                    totalPaidIncome += bean.getCashflow().get(i).getBasic_income();
                    totalCommission += bean.getCashflow().get(i).getBasic_income_commission();
                }
            }
            canDraw = totalPaidIncome - totalCommission;

            tvUnPayMoney.setText(String.valueOf(totalUnpaidIncome));
            tvPaidMoney.setText(String.valueOf(totalPaidIncome));
            tvServiceFee.setText(String.valueOf(totalCommission));
            tvCanDraw.setText(String.valueOf(canDraw));
            tvServiceFeeHint.setText(String.format(context.getString(R.string.benefit_stage_money), String.valueOf(bean.getCommission_rate())));
        });
    }

}