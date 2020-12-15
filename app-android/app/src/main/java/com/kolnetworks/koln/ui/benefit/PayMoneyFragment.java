package com.kolnetworks.koln.ui.benefit;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.main.MainViewModel;

import butterknife.BindView;


public class PayMoneyFragment extends BaseFragment {


    @BindView(R.id.rvBenefit)
    RecyclerView rvBenefit;
    @BindView(R.id.bottom)
    CardView bottom;
    private MainViewModel viewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pay_money;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    @Override
    protected void configViews() {

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
//        viewModel.loadCashFlow();
//
//        viewModel.getMldCashFlow().observe(this, beans -> {
//            Log.d("JJJ", "list size : " + beans.size());
//        });
    }

}